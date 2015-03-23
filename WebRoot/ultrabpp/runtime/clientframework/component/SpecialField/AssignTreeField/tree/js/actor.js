/**
 *处理人对象及属性
 *liuchuanzu
 *2010-06-17
 */
function JsActor(){
	this.sign = '';		//标识，save为保存，del为删除，defaul为默认
	this.id = '';		//id
	this.type = '';		//组或人，组为G，人为U；
	this.name = '';		//名称
	this.dealtype = '';	//处理类型(固定流程：NEXT,自由流程：)
	this.model = '';	 	//组处理模式(1:共享,2:独占,3:管理者)
	this.limittimes = '';	//受理时限
	this.payouttime = '';	//派发时限
	this.dealtimes = '';	//处理时限
	this.nextstepid = '';	//下一环节环节号
	this.childschema = '';//子流程定义名称
	this.payoutdesc = '';	//派发说明
}