var ListCombox = Ext.extend(Ext.form.ComboBox, {
       style:'',
       AjaxUrl : null,//获取数据的url
       IdField:null,//隐藏控件id
       NameField:null,//输入控件id
       NameFieldName:null,//输入控件name
       showFormat:null,
       queryParam:'query',//后台传的参数名,默认为query
       initComponent : function() {
          Ext.apply(this, {
            style:this.style,
            id:this.NameField,
            name:this.NameFieldName,
            showFormat:this.showFormat,
            anchor:'100%',
            forceSelection : true,
            selectOnFocus : true,
            mode : 'remote',
            editable : true,
            multiSelect :'true',
            queryParam:this.queryParam,
            loadingText:'',
            hideTrigger:true,//隐藏下拉箭头
            store : new Ext.data.JsonStore({ 
	               url:this.AjaxUrl,root:'',
                   fields: ['key','value']}), 
           tpl: '<tpl for="."><div ext:qtip="" class="x-combo-list-item">'+this.showFormat+'</div></tpl>', 
           displayField : 'value',
           queryDelay : 10,
           minChars:1,
           typeAhead: false,
           listeners:{
           'select':function(combox,record,index){
               if(document.getElementById(this.IdField))
                document.getElementById(this.IdField).value=record.data.key; }
           }
          });
    ListCombox.superclass.initComponent.call(this);
   }
});   

var ListComboxEx = Ext.extend(Ext.form.ComboBox, {
    style:'',
    width:471,
    AjaxUrl : null,//获取数据的url
    IdField:null,//隐藏控件id
    NameField:null,//输入控件id
    NameFieldName:null,//输入控件name
    queryParam:'query',//后台传的参数名,默认为query
    initComponent : function() {
       Ext.apply(this, {
         style:this.style,
         id:this.NameField,
         name:this.NameFieldName,
         anchor:'100%',
         forceSelection : true,
         selectOnFocus : true,
         mode : 'remote',
         editable : true,
         multiSelect :'true',
         queryParam:this.queryParam,
         loadingText:'',
         hideTrigger:true,//隐藏下拉箭头
         store : new Ext.data.JsonStore({ 
	               url:this.AjaxUrl,root:'',
                fields: this.fields}), 
        displayField : 'value',
        queryDelay : 10,
        minChars:1,
        typeAhead: false,
        listeners:{
        'select':function(combox,record,index){
            if(document.getElementById(this.IdField))
             document.getElementById(this.IdField).value=record.data.key; }
        }
       });
 ListCombox.superclass.initComponent.call(this);
}
});   