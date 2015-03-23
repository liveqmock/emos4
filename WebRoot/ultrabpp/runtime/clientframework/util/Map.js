function Map()
{
    this.arr = new Array();
}

Map.prototype =
{
    put : function(key, value)
    {
        if(!this.containsKey(key))
        {
            this.arr.push([key, value]);
        }
        else
        {
            for(var i = 0; i < this.arr.length; i++)
            {
                if(this.arr[i][0] == key)
                {
                    this.arr[i][1] = value;
                    return;
                }
            }
        }
    },
    
    get : function(key)
    {
        for(var i = 0; i < this.arr.length; i++)
        {
            if(this.arr[i][0] == key)
            {
                return this.arr[i][1];
            }
        }
        return null;
    },
    
    remove : function(key)
    {
        for(var i = 0; i < this.arr.length; i++)
        {
            if(this.arr[i][0] == key)
            {
                this.arr.splice(i, 1);
                return;
            }
        }
    },
    
    containsKey : function(key)
    {
        for(var i = 0; i < this.arr.length; i++)
        {
            if(this.arr[i][0] == key)
            {
                return true;
            }
        }
        return false;
    },
    
    keySet : function()
    {
        var l = new List();
        for(var i = 0; i < this.arr.length; i++)
        {
            l.add(this.arr[i][0]);
        }
        return l;
    },
    
    values : function()
    {
        var l = new List();
        for(var i = 0; i < this.arr.length; i++)
        {
            l.add(this.arr[i][1]);
        }
        return l;
    },
    
    size : function()
    {
        return this.arr.length;
    },
    
    clear : function()
    {
        this.arr = [];
    },
    
    iterator : function()
    {
        var vs = new Array();
        for(var i = 0; i < this.arr.length; i++)
        {
            vs.push(this.arr[i][1]);
        }
        var it = new Iterator(vs);
        return it;
    }
}