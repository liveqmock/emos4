function BaseField()
{
	this.fieldID;
	this.text;
}
//用于继承的方法块，用法：class2.protytype = (new class1()).extend({$some prototype$});
BaseField.extend = function(destination, source){for(property in source){destination[property]=source[property];};return destination;}
BaseField.prototype.extend = function(object){return BaseField.extend.apply(this, [this, object]);}

