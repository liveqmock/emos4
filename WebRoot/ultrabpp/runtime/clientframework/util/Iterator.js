function Iterator(iteratorArray)
{
    this.itArr = iteratorArray;
    this.index = -1;
}

Iterator.prototype =
{
    hasNext : function()
    {
        if(this.index + 1 >= this.itArr.length)
        {
            return false;
        }
        else
        {
            return true;
        }
    },
    
    next : function()
    {
        this.index++;
        return this.itArr[this.index];
    }
}