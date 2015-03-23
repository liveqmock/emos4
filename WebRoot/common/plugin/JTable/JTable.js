/* 
 * 作者：李昊原（BigMouse）
 * 版本：v1.0.3
 * 日期：2010-07-07
 * 说明：
 * 该工具为了实现客户端table动态添加和删除数据，并返回最终数据集，以及本次添加、修改和删除的数据
 */

/*
 * 修改记录
 * 2010-06-04
 *  1. 增加了tshow参数，可以只对数据进行操作，而不作为显示存在了
 *  2. 对样式进行了改造，当传''时，现实为默认样式，当传null时，则不显示样式
 * 2010-06-11
 *  1. 增加了生成xml时row的opt属性，新增为add，修改为mod，未操作为空
 *  2. 修改了modifyrow的参数，现在可以同步更新数据了
 * 2010-07-7
 *  1. 增加了对Firefox、Safari以及Google Chrome的支持
 */
 
/*
* 类结构为：（没有列出使用时不需要调用的内部使用方法）
* JsCell(cview, cheadcode, chead, cheadstyles, cdataindex, cdatastyles, cparas) 构造函数，创建一个表头的列
* JsTable(tname, tshow, ttableclass, theadtrclass, theadtdclass, tdatatrclass, tdatatdclass, tdatacrosstrclass, tdatacrosstdclass, tcolumns, tdata) 构造函数，用于初始化table以及数据
*  |-draw(pNode) 画表格，或重画表格
*  |-addrow(rowdata) 添加行，如果为二维数组，则添加多行数据，如果为一位数组则添加一行数据
*  |-modifyrow(rowdata) 修改行，这里只保存修改数据的主键
*  |-deleterow(key) 删除行
*  |-getdata() 获取现有数据的数组
*  |-getaddeddata() 获取添加的数据的主键数组
*  |-getmodifydata() 获取修改的数据的主键数组
*  |-getdeleddata() 获取删除的数据的主键数组
*  |-gettablexml() 获取现有数据的xml
*/

/*
* 构造函数，创建一个表头的列
* cview        是否可见
* cheadcode    表头的英文标识
* chead        表头显示内容（支持html）
* cheadstyles  表头的样式，为样式数组
* cdataindex   绑定数据的列的索引
* cdatastyles  数据的样式
* cparas       格式化的列内容，支持html以及js，其中{@COL0}代表替换为数据行的第一列数据，{@COLN}为第N条数据
*/
function JsCell(cview, cheadcode, chead, cheadstyles, cdataindex, cdatastyles, cparas)
{
    this.view = cview;
    this.head = chead;
    this.headcode = cheadcode;
    this.headstyles = cheadstyles;
    this.dataindex = cdataindex;
    this.datastyles = cdatastyles;
    this.paras = cparas;
}
 
/*
 * 构造函数，用于初始化table以及数据
 * tname                table的ID
 * ttableclass          table的样式
 * theadtrclass         表头的tr样式
 * theadtdclass         表头的td样式
 * tdatatrclass         表格数据的tr样式
 * tdatatdclass         表格数据的td样式
 * tdatacrosstrclass    表格数据交叉行的tr样式
 * tdatacrosstdclass    表格数据交叉行的td样式
 * tcolumns             表头，使用JsCell对象的数组
 * tdata                数据，使用二维数组，第一维代表行，第二维代表列，每行的第一列为主键，不允许重复
 */
function JsTable(tname, tshow, ttableclass, theadtrclass, theadtdclass, tdatatrclass, tdatatdclass, tdatacrosstrclass, tdatacrosstdclass, tcolumns, tdata)
{
    this.name = tname;
    this.columns = tcolumns;
    this.data = tdata;
    this.show = tshow;
    this.container = null;

    this.addeddata = new Array();
    this.modifydata = new Array();
    this.deleddata = new Array();

    this.tableclass = ttableclass;
    this.headtrclass = theadtrclass;
    this.headtdclass = theadtdclass;
    this.datatrclass = tdatatrclass;
    this.datatdclass = tdatatdclass;
    this.datacrosstrclass = tdatacrosstrclass;
    this.datacrosstdclass = tdatacrosstdclass;

    this.divbox = null;
    this.tbl = null;
    this.headtr = null;
}

JsTable.prototype =
{
    /*
    * 内部使用方法，画table
    */
    drawtable: function()
    {
        this.tbl = document.createElement('table');
        this.tbl.id = this.name;
        if (this.tableclass != '') this.tbl.className = this.tableclass;
        else if (this.tableclass != null) this.tbl.className = 'JT_Table';
        this.tbl.cellPadding = 1;
        this.tbl.cellSpacing = 1;
    },

    /*
    * 内部使用方法，画表头
    */
    drawhead: function()
    {
        this.headtr = this.tbl.insertRow(-1);
        if (this.headtrclass != '') this.headtr.className = this.headtrclass;
        else if (this.headtr != null) this.headtr.className = 'JT_HeadTr';
        for (var i = 0; i < this.columns.length; i++)
        {
            if (this.columns[i].view)
            {
                //var headtd = this.headtr.insertCell(-1);
                var headtd = document.createElement('th');
                if (this.headtdclass != '') headtd.className = this.headtdclass;
                else if (this.headtd != null) headtd.className = 'JT_HeadTd';
                headtd.innerHTML = this.columns[i].head;
                var stylesarr = this.columns[i].headstyles;
                for (var s = 0; s < stylesarr.length; s++)
                {
                    headtd.style[stylesarr[s][0]] = stylesarr[s][1];
                }
                this.headtr.appendChild(headtd);
            }
        }
    },

    /*
    * 内部使用方法，画数据
    */
    drawdata: function()
    {
        for (var i = 0; i < this.data.length; i++)
        {
            var datarow = this.data[i];
            var datatr = this.tbl.insertRow(-1);
            var iscross = false;
            if ((i + 3) % 2 != 0)
            {
                if (this.datatrclass != '') datatr.className = this.datatrclass;
                else if (this.datatr != null) datatr.className = 'JT_DataTr';
            }
            else
            {
                if (this.datacrosstrclass != '') datatr.className = this.datacrosstrclass;
                else if (this.datatr != null) datatr.className = 'JT_DataTr_Cross';
                iscross = true;
            }

            for (var j = 0; j < this.columns.length; j++)
            {
                if (this.columns[j].view)
                {
                    var paraarr = this.columns[j].paras;
                    var datacol = datatr.insertCell(-1);
                    if (!iscross)
                    {
                        if (this.datatdclass != '') datacol.className = this.datatdclass;
                        else if (this.datacol != null) datacol.className = 'JT_DataTd';
                    }
                    else
                    {
                        if (this.datacrosstrclass != '') datacol.className = this.datacrosstrclass;
                        else if (this.datacol != null) datacol.className = 'JT_DataTd';
                    }
                    var stylesarr = this.columns[j].datastyles;
                    for (var s = 0; s < stylesarr.length; s++)
                    {
                        datacol.style[stylesarr[s][0]] = stylesarr[s][1];
                    }
                    var content = '';

                    if (paraarr != '')
                    {
                        content = paraarr;
                        while (content.indexOf('{@COL') > -1)
                        {
                            var para1 = content.substring(content.indexOf('{@COL') + 5);
                            var para = para1.slice(0, para1.indexOf('}'));
                            var intpara = parseInt(para);
                            var celldata = datarow[intpara];
                            while (content.indexOf('{@COL' + para + '}') > -1)
                            {
                                content = content.replace('{@COL' + para + '}', celldata);
                            }
                        }
                    }
                    else if (this.columns[j].dataindex > -1)
                    {
                        if (datarow[this.columns[j].dataindex])
                        {
                            content = datarow[this.columns[j].dataindex];
                        }
                        else
                        {
                            content = '';
                        }
                    }
                    datacol.innerHTML = content;
                }
            }
        }
    },

    /*
    * 画表格，或重画表格
    * pNode：表格的父对象，表格将画在这个对象里，如果为空，则画在body中
    */
    draw: function(pNode)
    {
        if (this.show)
        {
            if (pNode)
            {
                this.container = pNode;
            }
            else
            {
                pNode = this.container;
            }

            this.drawtable();

            this.drawhead();

            this.drawdata();
            if (!pNode)
            {
                pNode = document.body;
            }
            if (document.getElementById(this.name))
            {
                document.getElementById(this.name).outerHTML = '';
            }
            pNode.appendChild(this.tbl);
        }
    },

    /*
    * 添加行，如果为二维数组，则添加多行数据，如果为一位数组则添加一行数据
    * rowdata：数据，可以为二维数组，也可以为一位数组
    */
    addrow: function(rowdata)
    {
        if (rowdata[0][0] == undefined || typeof (rowdata[0]) == 'string' || typeof (rowdata[0]) == 'number')
        {
            this.addonerow(rowdata);
        }
        else
        {
            for (var i = 0; i < rowdata.length; i++)
            {
                this.addonerow(rowdata[i]);
            }
        }

        this.draw();
    },

    /*
    * 内部使用方法，添加一行数据
    */
    addonerow: function(rowdata)
    {
        if (rowdata[0] == '')
        {
            alert('主键不能为空！');
            return false;
        }

        for (var i = 0; i < this.data.length; i++)
        {
            if (this.data[i][0] == rowdata[0])
            {
                alert('主键有冲突！请检查数据的第一列数据！');
                return false;
            }
        }

        var isdeled = false;
        for (var i = 0; i < this.deleddata.length; i++)
        {
            if (rowdata[0] == this.deleddata[i])
            {
                this.deleddata.splice(i, 1);
                isdeled = true;
                break;
            }
        }
        if (!isdeled)
        {
            this.addeddata.push(rowdata[0]);
        }

        this.data[this.data.length] = rowdata;
    },

    /*
    * 修改行，这里只保存修改数据的主键
    * key：修改数据的主键
    */
    modifyrow: function(rowdata)
    {
        var ismodified = false;
        for (var i = 0; i < this.addeddata.length; i++)
        {
            if (rowdata[0] == this.addeddata[i])
            {
                ismodified = true;
                break;
            }
        }
        for (var i = 0; i < this.modifydata.length; i++)
        {
            if (rowdata[0] == this.modifydata[i])
            {
                ismodified = true;
                break;
            }
        }
        if (!ismodified)
        {
            this.modifydata.push(rowdata[0]);
        }

        for (var i = 0; i < this.data.length; i++)
        {
            if (this.data[i][0] == rowdata[0])
            {
                this.data[i] = rowdata;
                break;
            }
        }
    },

    /*
    * 删除行
    * key：删除数据的主键
    */
    deleterow: function(key)
    {
        if (key == '')
        {
            alert('主键不能为空！');
            return false;
        }

        for (var i = 0; i < this.data.length; i++)
        {
            if (this.data[i][0] == key)
            {
                this.data.splice(i, 1);
                break;
            }
        }

        var isadded = false;
        for (var i = 0; i < this.addeddata.length; i++)
        {
            if (key == this.addeddata[i])
            {
                this.addeddata.splice(i, 1);
                isadded = true;
                break;
            }
        }
        if (!isadded)
        {
            this.deleddata.push(key);
        }

        for (var i = 0; i < this.modifydata.length; i++)
        {
            if (key == this.modifydata[i])
            {
                this.modifydata.splice(i, 1);
                break;
            }
        }

        this.draw();
    },

    /*
    * 获取现有数据的数组
    */
    getdata: function()
    {
        return this.data;
    },

    /*
    * 获取添加的数据的主键数组
    */
    getaddeddata: function()
    {
        return this.addeddata;
    },

    /*
    * 获取修改的数据的主键数组
    */
    getmodifydata: function()
    {
        return this.modifydata;
    },

    /*
    * 获取删除的数据的主键数组
    */
    getdeleddata: function()
    {
        return this.deleddata;
    },

    /*
    * 获取现有数据的xml
    */
    gettablexml: function()
    {
        var doc = this.createDocObj();
        var node_table = doc.createElement('table');
        var attradd = doc.createAttribute('add');
        attradd.value = this.addeddata.toString();
        node_table.setAttributeNode(attradd);
        var attrmod = doc.createAttribute('mod');
        attrmod.value = this.modifydata.toString();
        node_table.setAttributeNode(attrmod);
        var attrdel = doc.createAttribute('del');
        attrdel.value = this.deleddata.toString();
        node_table.setAttributeNode(attrdel);

        for (var i = 0; i < this.data.length; i++)
        {
            var rowdata = this.data[i];
            var node_row = doc.createElement('row');

            var addString = ',' + this.addeddata.toString() + ',';
            var modifyString = ',' + this.modifydata.toString() + ',';
            var opt = null;
            if (addString.indexOf(',' + rowdata[0] + ',') > -1)
            {
                opt = doc.createAttribute('opt');
                opt.value = 'add';
            }
            else if (modifyString.indexOf(',' + rowdata[0] + ',') > -1)
            {
                opt = doc.createAttribute('opt');
                opt.value = 'mod';
            }
            else
            {
                opt = doc.createAttribute('opt');
                opt.value = '';
            }

            node_row.setAttributeNode(opt);

            var emptytdcount = 0;
            for (var j = 0; j < rowdata.length; j++)
            {
                var node_cell = null;
                for (var k = 0; k < this.columns.length; k++)
                {
                    if (this.columns[k].dataindex == j)
                    {
                        node_cell = doc.createElement(this.columns[k].headcode);
                        break;
                    }
                }
                if (!node_cell)
                {
                    node_cell = doc.createElement('emptytd' + emptytdcount);
                    emptytdcount++;
                }
                if (node_cell.textContent)
                {
                    node_cell.textContent = rowdata[j];
                }
                else
                {
                    node_cell.text = rowdata[j];
                }
                node_row.appendChild(node_cell);
            }
            node_table.appendChild(node_row);
        }
        doc.appendChild(node_table);
        if (doc.xml)
        {
            return doc.xml;
        }
        else
        {
            var oSerializer = new XMLSerializer();
            return oSerializer.serializeToString(doc);
        }
    },

    /*
    * 内部使用方法，获取xml对象
    */
    createDocObj: function()
    {
        var xmlobjs = ['Msxml2.DOMDocument.5.0', 'Msxml2.DOMDocument.4.0', 'Msxml2.DOMDocument.3.0', 'Msxml2.DOMDocument', 'Microsoft.XmlDom'];
        for (var i = 0; i < xmlobjs.length; i++)
        {
            try
            {
                var domDoc = new ActiveXObject(xmlobjs[i]);
                return domDoc;
            }
            catch (e) { }
        }
        if (document.implementation.createDocument)
        {
            domDoc = document.implementation.createDocument('', '', null)
            return domDoc;
        }
        return null;
    }
}
/*
if (typeof (HTMLElement) != "undefined" && !window.opera)
{
    HTMLElement.prototype.__defineGetter__("outerHTML", function()
    {
        var a = this.attributes, str = "<" + this.tagName, i = 0; for (; i < a.length; i++)
            if (a[i].specified)
            str += " " + a[i].name + '="' + a[i].value + '"';
        if (!this.canHaveChildren)
            return str + " />";
        return str + ">" + this.innerHTML + "</" + this.tagName + ">";
    });
    HTMLElement.prototype.__defineSetter__("outerHTML", function(s)
    {
        var r = this.ownerDocument.createRange();
        r.setStartBefore(this);
        var df = r.createContextualFragment(s);
        this.parentNode.replaceChild(df, this);
        return s;
    });
    HTMLElement.prototype.__defineGetter__("canHaveChildren", function()
    {
        return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase());
    });
} 
*/