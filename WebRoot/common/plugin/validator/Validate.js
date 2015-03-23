/* 
 * 作者：李昊原（BigMouse）
 * 版本：v1.0.0
 * 日期：2010-07-13
 * 说明：
 * 该工具用于进行客户端验证和Ajax服务端验证
 * 
 * 验证方式：client和remote
 * 目前支持的验证类型：Require,Email,Phone,Mobile,Url,Ip,IdCard,Currency,Number,Zip,QQ,Integer,SN,Double,Float,English,Chinese,Username,Filter,Limit,Date,Repeat,Range,Compare,Custom,Group
 * 其中需要参数的有Filter,Limit,Date,Repeat,Range,Compare,Custom,Group
 * 验证方式与参数中间用等号“=”隔开，参数之间用英文逗号“,”隔开
 * Filter=提供过滤条件的字段ID
 * Limit=提供最小极限条件的字段ID,提供最大极限条件的字段ID
 * Date=提供时间格式的字段ID，格式有ymd（年月日）和dmy（日月年）
 * Repeat=重复的字段ID
 * Range=比较的最小值,比较的最大值
 * Compare=提供比较类型的字段ID,作比较的字段ID，比较类型有NotEqual（!=）、GreaterThan（>）、GreaterThanEqual（>=）、LessThan（<）、LessThanEqual（<=），其他文字被认为判断相等（=）
 * Custom=自定义验证表达式的字段ID，注意，Custom中的正则表达式需要将“\”写成“\\”，如下面例子中的“\d”写成了“\\d”
 *
 * Validate.setValidate('要进行验证的字段ID', '字段名称', 是否必填, '验证类型', '验证方式', '异步验证的url', '验证失败的提示信息', ...触发验证的事件（可为多个）);
 * 举例：
 * 客户端验证：
 * Require：Validate.setValidate('testField1', '字段名称', true, 'Require', 'client', null, '不能为空', EventType.blur, EventType.submit);
 * Email,Phone,Mobile,Url,Ip,IdCard,Currency,Number,Zip,QQ,Integer,SN,Double,Float,English,Chinese,Username与Require的处理方式一样
 * Filter：Validate.setValidate('testField1', '字段名称', true, 'Filter=敏感词语1|敏感词语2|敏感词语3', 'client', null, '不能为空', EventType.blur, EventType.submit);
 * Limit：Validate.setValidate('testField1', '字段名称', true, 'Limit=1,9', 'client', null, '不能为空', EventType.blur, EventType.submit);
 * Date：Validate.setValidate('testField1', '字段名称', true, 'Date=ymd', 'client', null, '不能为空', EventType.blur, EventType.submit);
 * Repeat：Validate.setValidate('testField1', '字段名称', true, 'Repeat=testField12', 'client', null, '不能为空', EventType.blur, EventType.submit);
 * Range：Validate.setValidate('testField1', '字段名称', true, 'Repeat=1,100', 'client', null, '不能为空', EventType.blur, EventType.submit);
 * Compare：Validate.setValidate('testField1', '字段名称', true, 'Compare=NotEqual,Demo', 'client', null, '不能为空', EventType.blur, EventType.submit);
 * Custom：Validate.setValidate('testField1', '字段名称', true, 'Custom=^\\d+$', 'client', null, '不能为空', EventType.blur, EventType.submit);
 * 
 * 服务端验证：Validate.setValidate('testField2', true, 'Require', 'remote', 'valiserver.htm', '不能为空', EventType.blur, EventType.submit);
 *
 * form提交时调用Validate.submitValidate(form的对象)就OK了，无需自己写form.submit()，验证通过后会自动提交
 * 页面操作时的验证使用标签即可。
 * 需要在第45行-48行设置默认验证的页面以及验证状态的显示图标，需要些全路径
 * 使用时一定要引入util.js以及AjaxBus.js
 */

/*
 * 修改记录
 */


function ValidateModel(fieldID, title, req, type, valiType, actonurl, msg, handle)
{
    this.title = title;
    this.fieldID = fieldID;
    var t = '';
    var i = '';
    if (type.indexOf('=') > 0)
    {
        var valiarr = type.split('=');
        t = valiarr[0];
        i = type.substring(type.indexOf('=') + 1);
    }
    else
    {
        t = type;
    }
    this.type = t;
    this.infor = i;
    this.req = req;
    this.valiType = valiType;
    this.successBox = null;
    this.failBox = null;
    this.loadingBox = null;
    this.url = actonurl;
    this.handle = handle;
    this.errorMsg = msg;
}

function Validate() { }

Validate.showType = 2;
Validate.customComplete = false;
Validate.defaultValidateUrl = 'valiserver.xml';
Validate.successImg = '/eoms/common/plugin/validator/yes.png';
Validate.failImg = '/eoms/common/plugin/validator/warning.png';
Validate.loadingImg = '/eoms/common/plugin/validator/loading.gif';
Validate.validates = new List();
Validate.leftVali = 0;
Validate.hasFailed = false;
Validate.submitFormObj = null;
Validate.errorMsg = '';
Validate.errorBox = null;
Validate.errorBoxWidth = 250;
Validate.errorBoxHeight = 100;
Validate.status = 'simple';

Validate.setValidate = function(fieldID, title, req, type, valiType, action, errorMsg, handle)
{
    var handles = new Array();

    for (var i = 3; i < arguments.length; i++)
    {
        handles.push(arguments[i]);
    }

    if (!Validate.validates)
    {
        Validate.validates = new List();
    }
    var valiModel = new ValidateModel(fieldID, title, req, type, valiType, action, errorMsg, handles);
    Validate.validates.add(valiModel);

    var valiFunction = function()
    {
        var handler = new ValidateHandler(valiModel);
        handler.validate();
        Validate.status = 'simple';
        Validate.hasFailed = false;
        Validate.completeValidate();
    }

    for (var i = 0; i < handles.length; i++)
    {
        if (handles[i] == EventType.blur)
        {
            EventHandler.attachEvent(Util.$(fieldID), EventType.blur, valiFunction);
        }
    }
}

Validate.submitValidate = function(formID)
{
    Validate.submitFormObj = Util.$(formID);
    Validate.hasFailed = false;
    Validate.status = 'submit';
    Validate.leftVali = Validate.validates.size();
    for (var it = Validate.validates.iterator(); it.hasNext(); )
    {
        var vali = it.next();
        if (vali.handle.toString().indexOf(EventType.submit) > -1)
        {
            var handler = new ValidateHandler(vali);
            handler.onComplete = Validate.finishValidate;
            handler.validate();
        }
    }
}

Validate.finishValidate = function(result)
{
    if (!result)
    {
        Validate.hasFailed = true;
    }

    Validate.leftVali -= 1;

    if (Validate.leftVali < 1)
    {
        Validate.completeValidate();
    }
}

Validate.completeValidate = function()
{
    if (!Validate.hasFailed)
    {
        if (Validate.status == 'submit')
        {
            if (Validate.submitFormObj)
            {
                this.submitFormObj.submit();
            }
        }
    }
    else
    {
    	if(Validate.customComplete)
    	{
    		Validate.customCompleteValidateFail();
    	}
    	else
    	{
    		if(Validate.showType == 1)
    		{
		        if(Validate.errorBox)
		        {
		        	Validate.errorBox.outerHTML = '';
		        }
		        Validate.errorBox = document.createElement('div');
		        Validate.errorBox.style.position = 'absolute';
		        Validate.errorBox.style.top = parseInt(document.body.clientHeight) - 145;
		        Validate.errorBox.style.left = 5;
		        Validate.errorBox.style.width = Validate.errorBoxWidth;
		        Validate.errorBox.style.height = Validate.errorBoxHeight;
		        Validate.errorBox.style.padding = '10px';
		        Validate.errorBox.style.fontSize = '12px';
		        Validate.errorBox.style.overflow = 'auto';
		        Validate.errorBox.style.fontFamily = '宋体';
		        Validate.errorBox.style.backgroundColor = '#FFCCCC';
		        Validate.errorBox.style.border = '#FFAAAA solid 1px';
		        Validate.errorBox.style.index = '99999';
		        Validate.errorBox.innerText = Validate.errorMsg;
		        document.body.appendChild(Validate.errorBox);
	        }
        }
    }
}

Validate.customCompleteValidateFail = function() { }

function ValidateHandler(valiModel)
{
    this.validateModel = valiModel;
    this.remoteHandler = new AjaxBus();
    this.responseText = null;
    this.responseXml = null;
    this.status = null;
    this.statusText = null;
}

ValidateHandler.prototype =
{
    validate: function()
    {
        if (this.validateModel.req == false && Util.$(this.validateModel.fieldID).value == '')
        {
        	this.clearStatus(this.validateModel);
            this.validateSuccess(this.validateModel);
            this.onComplete(true);
            return;
        }
        var thiz = this;
        var vali = null;
        if (this.validateModel.valiType == 'client')
        {
            vali = new ClientValidate(this.validateModel);
        }
        else
        {
            vali = new RemoteValidate(this.validateModel);
            vali.onLoading = function() { thiz.onLoading(thiz.validateModel) };
        }

        vali.onComplete = function(result)
        {
            if (result)
            {
                thiz.validateSuccess(thiz.validateModel);
            }
            else
            {
                thiz.validateFail(thiz.validateModel);
            }
            thiz.onComplete(result);
        };
        vali.validate();
    },

    validateSuccess: function(field)
    {
        this.clearStatus(field);
        if (Validate.showType == 2)
        {
            var parent = Util.$(field.fieldID).parentNode;
            var successObj = field.successBox;
            if (!successObj)
            {
                successObj = document.createElement('font');
                successObj.style.marginLeft = '3px';
                successObj.style.marginRight = '3px';
                var imgobj = document.createElement('img');
                imgobj.src = Validate.successImg;
                imgobj.style.marginBottom = '-3px';
                successObj.appendChild(imgobj);
                field.successBox = successObj;

                var parent = Util.$(field.fieldID).parentNode;
                if (parent.lastChild == Util.$(field.fieldID))
                {
                    parent.appendChild(field.successBox);
                }
                else
                {
                    parent.insertBefore(field.successBox, Util.$(field.fieldID).nextSibling);
                }
            }
            field.successBox.style.display = '';
        }
    },

    validateFail: function(field)
    {
        this.clearStatus(field);

        Util.$(field.fieldID).style.backgroundColor = '#FFAAAA';

		this.customValidateFail(field);
		
        if (Validate.showType == 1)
        {
            Validate.errorMsg += field.title + '：' + field.errorMsg + '\n';
        }

        if (Validate.showType == 2)
        {
            var parent = Util.$(field.fieldID).parentNode;
            var errorObj = field.failBox;
            if (!errorObj)
            {
                errorObj = document.createElement('font');
                errorObj.style.marginLeft = '3px';
                errorObj.style.marginRight = '3px';
                var imgobj = document.createElement('img');
                imgobj.src = Validate.failImg;
                imgobj.style.marginBottom = '-3px';
                errorObj.appendChild(imgobj);
                errtextobj = document.createElement('font');
                errtextobj.style.color = 'red';
                errtextobj.style.fontSize = '12px';
                errtextobj.style.fontWeight = 'bold';
                Util.ie ? errtextobj.innerText = field.errorMsg : errtextobj.textContent = field.errorMsg;
                errorObj.appendChild(errtextobj);
                field.failBox = errorObj;

                if (parent.lastChild == Util.$(field.fieldID))
                {
                    parent.appendChild(errorObj);
                }
                else
                {
                    parent.insertBefore(errorObj, Util.$(field.fieldID).nextSibling);
                }
            }
            errorObj.style.display = '';
        }
        Validate.finishValidate(false);
    },
    
    customValidateFail: function(field){ },

    clearStatus: function(field)
    {
    	
		Util.$(field.fieldID).style.backgroundColor = '';
        if (field.successBox)
        {
            field.successBox.style.display = 'none';
        }
        if (field.failBox)
        {
            field.failBox.style.display = 'none';
        }
        if (field.loadingBox)
        {
            field.loadingBox.style.display = 'none';
        }
        Util.$(field.fieldID).style.backgroundColor = '';
        Validate.errorMsg = '';
        if(Validate.errorBox)
        {
        	Validate.errorBox.outerHTML = '';
        }
    },

    onComplete: function(result) { },
    onLoading: function(field)
    {
        this.clearStatus(field);
        if(Validate.showType == 2)
        {
	        if (!field.loadingBox)
	        {
	            loadingObj = document.createElement('font');
	            loadingObj.style.marginLeft = '3px';
	            loadingObj.style.marginRight = '3px';
	            var imgobj = document.createElement('img');
	            imgobj.src = Validate.loadingImg;
	            imgobj.style.marginBottom = '-3px';
	            loadingObj.appendChild(imgobj);
	            field.loadingBox = loadingObj;
	            var parent = Util.$(field.fieldID).parentNode;
	            if (parent.lastChild == Util.$(field.fieldID))
	            {
	                parent.appendChild(field.loadingBox);
	            }
	            else
	            {
	                parent.insertBefore(field.loadingBox, Util.$(field.fieldID).nextSibling);
	            }
	        }
	        field.loadingBox.style.display = '';
        }
    }
}

ValidateRegExp =
{
    Require: /.+/,
    Email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    Phone: /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
    Mobile: /^((\(\d{2,3}\))|(\d{3}\-))?(13\d{9}|15\d{9})$/,
    Url: /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
    Ip: /^([3-9]\d{0,1}|[1]\d{0,2}|[2]\d{0,1}|[2][0-4]\d{1}|[2][5][0-5]|[0])\.([3-9]\d{0,1}|[1]\d{0,2}|[2]\d{0,1}|[2][0-4]\d{1}|[2][5][0-5]|[0])\.([3-9]\d{0,1}|[1]\d{0,2}|[2]\d{0,1}|[2][0-4]\d{1}|[2][5][0-5]|[0])\.([3-9]\d{0,1}|[1]\d{0,2}|[2]\d{0,1}|[2][0-4]\d{1}|[2][5][0-5]|[0])$/,
    IdCard: "this.IsIdCard(value)",
    Currency: /^\d+(\.\d+)?$/,
    Number: /^\d+$/,
    Zip: /^[1-9]\d{5}$/,
    QQ: /^[1-9]\d{4,8}$/,
    Integer: /^[-\+]?\d+$/,
    SN: /^[0-9A-Z]+$/,
    Double: /^[-\+]?\d+(\.\d+)?$/,
    Float: /^[-\+]?\d+(\.\d+)?$/,
    English: /^[A-Za-z]+$/,
    Chinese: /^[\u0391-\uFFE5]+$/,
    Username: /^[a-z]\w{3,}$/i,
    UnSafe: /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
    IsSafe: function(str) { return !this.UnSafe.test(str); },
    SafeString: "this.IsSafe(value)",
    Filter: "this.DoFilter(value, args[0])",
    Limit: "this.limit(value.length, args[0], args[1])",
    LimitB: "this.limit(this.LenB(value), args[0], args[1])",
    Date: "this.IsDate(value, args[0])",
    Repeat: "value == Util.$(args[0]).value",
    Range: "args[0] < (value|0) && (value|0) < args[1]",
    Compare: "this.compare(value,args[0], args[1])",
    Custom: "this.Exec(value, args[0])",
    Group: "this.MustChecked(args[0], args[1], args[2])"
}

function ValidateType(type)
{
    this.type = type;
    this.args = new Array();
    for (var i = 1; i < arguments.length; i++)
    {
        this.args.push(arguments[i]);
    }
}

function ClientValidate(valiModel)
{
    this.validateModel = valiModel;
}

ClientValidate.prototype =
{
    validate: function()
    {
        var res = false;
        var valiObj = Util.$(this.validateModel.fieldID);
        var value = valiObj.value;
        switch (this.validateModel.type)
        {
            case "IdCard":
            case "Date":
            case "Repeat":
            case "Range":
            case "Compare":
            case "Custom":
            case "Group":
            case "Limit":
            case "LimitB":
            case "SafeString":
            case "Filter":
                var args = null;
                if (this.validateModel.type == 'Filter' || this.validateModel.type == 'Date' || this.validateModel.type == 'Repeat' || this.validateModel.type == 'Custom')
                {
                    if (this.validateModel.type == 'Custom')
                    {
                        //args = [this.validateModel.infor.replace('\\', '\\\\')];
                        args = [this.validateModel.infor];
                    }
                    else
                    {
                        args = [this.validateModel.infor];
                    }
                }
                else if (this.validateModel.type == 'Limit' || this.validateModel.type == 'LimitB' || this.validateModel.type == 'Range' || this.validateModel.type == 'Compare' || this.validateModel.type == 'Group')
                {
                    if (this.validateModel.infor.indexOf(',') < 0)
                    {
                        args = [this.validateModel.infor];
                    }
                    else
                    {
                        args = this.validateModel.infor.split(',');
                    }
                }
                if (eval(ValidateRegExp[this.validateModel.type]))
                {
                    res = true;
                }
                break;
            default:
                if (ValidateRegExp[this.validateModel.type].test(value))
                {
                    res = true;
                }
                break;
        }
        this.onComplete(res);
    },
    limit: function(len, min, max)
    {
        min = min || 0;
        max = max || Number.MAX_VALUE;
        return min <= len && len <= max;
    },
    LenB: function(str)
    {
        return str.replace(/[^\x00-\xff]/g, "***").length;
    },
    Exec: function(op, reg)
    {
        return new RegExp(reg, "g").test(op);
    },
    compare: function(op1, operator, op2)
    {
        switch (operator)
        {
            case "NotEqual":
                return (op1 != op2);
            case "GreaterThan":
                return (op1 > op2);
            case "GreaterThanEqual":
                return (op1 >= op2);
            case "LessThan":
                return (op1 < op2);
            case "LessThanEqual":
                return (op1 <= op2);
            default:
                return (op1 == op2);
        }
    },
    MustChecked: function(name, min, max)
    {
        var groups = document.getElementsByName(name);
        var hasChecked = 0;
        min = min || 1;
        max = max || groups.length;
        for (var i = groups.length - 1; i >= 0; i--)
            if (groups[i].checked) hasChecked++;
        return min <= hasChecked && hasChecked <= max;
    },
    DoFilter: function(input, filter)
    {
        return new RegExp("^.+\.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(input);
    },
    IsIdCard: function(number)
    {
        var date, Ai;
        var verify = "10x98765432";
        var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
        var area = ['', '', '', '', '', '', '', '', '', '', '', '\u5317\u4eac', '\u5929\u6d25', '\u6cb3\u5317', '\u5c71\u897f', '\u5185\u8499\u53e4', '', '', '', '', '', '\u8fbd\u5b81', '\u5409\u6797', '\u9ed1\u9f99\u6c5f', '', '', '', '', '', '', '', '\u4e0a\u6d77', '\u6c5f\u82cf', '\u6d59\u6c5f', '\u5b89\u5fae', '\u798f\u5efa', '\u6c5f\u897f', '\u5c71\u4e1c', '', '', '', '\u6cb3\u5357', '\u6e56\u5317', '\u6e56\u5357', '\u5e7f\u4e1c', '\u5e7f\u897f', '\u6d77\u5357', '', '', '', '\u91cd\u5e86', '\u56db\u5ddd', '\u8d35\u5dde', '\u4e91\u5357', '\u897f\u85cf', '', '', '', '', '', '', '\u9655\u897f', '\u7518\u8083', '\u9752\u6d77', '\u5b81\u590f', '\u65b0\u7586', '', '', '', '', '', '\u53f0\u6e7e', '', '', '', '', '', '', '', '', '', '\u9999\u6e2f', '\u6fb3\u95e8', '', '', '', '', '', '', '', '', '\u56fd\u5916'];
        var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/i);
        if (re == null) return false;
        if (re[1] >= area.length || area[re[1]] == "") return false;
        if (re[2].length == 12)
        {
            Ai = number.substr(0, 17);
            date = [re[9], re[10], re[11]].join("-");
        }
        else
        {
            Ai = number.substr(0, 6) + "19" + number.substr(6);
            date = ["19" + re[4], re[5], re[6]].join("-");
        }
        if (!this.IsDate(date, "ymd")) return false;
        var sum = 0;
        for (var i = 0; i <= 16; i++)
        {
            sum += Ai.charAt(i) * Wi[i];
        }
        Ai += verify.charAt(sum % 11);
        return (number.length == 15 || number.length == 18 && number == Ai);
    },
    IsDate: function(op, formatString)
    {
        formatString = formatString || "ymd";
        var m, year, month, day;
        switch (formatString)
        {
            case "ymd":
                m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
                if (m == null) return false;
                day = m[6];
                month = m[5] * 1;
                year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
                break;
            case "dmy":
                m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
                if (m == null) return false;
                day = m[1];
                month = m[3] * 1;
                year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
                break;
            default:
                break;
        }
        if (!parseInt(month)) return false;
        month = month == 0 ? 12 : month;
        var date = new Date(year, month - 1, day);
        return (typeof (date) == "object" && year == date.getFullYear() && month == (date.getMonth() + 1) && day == date.getDate());
        function GetFullYear(y) { return ((y < 30 ? "20" : "19") + y) | 0; }
    },
    onComplete: function(result) { }
}

function RemoteValidate(valiModel)
{
    this.validateModel = valiModel;
    this.ajax = new AjaxBus();
}

RemoteValidate.prototype =
{
    validate: function()
    {
        var thiz = this;
        this.ajax.onComplete = function(resText, resXml)
        {
            thiz.responseText = resText;
            thiz.responseXml = resXml;
            var res = false;
            if (resText == 'true' || resXml != null && resXml.documentElement != null && (Util.ie ? resXml.documentElement.text == 'true' : resXml.documentElement.textContent == 'true'))
            {
                res = true;
            }
            thiz.onComplete(res);
        };
        this.ajax.onLoading = this.onLoading;
        this.ajax.onError = function(stat, statText, resText) { thiz.status = stat; thiz.statusText = statText; thiz.responseText = resText; thiz.onError(); thiz.onComplete(false); };

        var argString = '?id=' + this.validateModel.fieldID + '&v=' + Util.$(this.validateModel.fieldID).value + '&t=' + this.validateModel.type;
        if (this.validateModel.url != null || this.validateModel.url == '')
        {
            this.ajax.callBackPost(this.validateModel.url + argString);
        }
        else
        {
            this.ajax.callBackPost(Validate.defaultValidateUrl + argString);
        }
    },
    onComplete: function(result) { },
    onLoading: function() { },
    onError: function() { }
}