function List()
{
    this.arr = new Array();
}

List.prototype = 
{
    add : function(obj)
    {
        this.arr.push(obj);
    },
    
    addat : function(index, obj)
    {
        this.arr.splice(index, 0, obj);
    },
    
    get : function(index)
    {
        return this.arr[index];
    },
    
    set : function(index, obj)
    {
        this.arr.splice(index, 1, obj);
    },
    
    size : function()
    {
        return this.arr.length;
    },
    
    remove : function(index)
    {
        this.arr.splice(index, 1);
    },
    
    clear : function()
    {
        this.arr = [];
    },
    
    iterator : function()
    {
        var it = new Iterator(this.arr);
        return it;
    }
}