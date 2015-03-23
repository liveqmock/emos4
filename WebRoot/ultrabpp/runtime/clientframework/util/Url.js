function Url(urlstr)
{
    this.paraMap = new Map();
    
    if(urlstr == "")
    {
    	urlstr = location.search;
    }
    
    if(urlstr.indexOf('?') > -1)
    {
        urlstr = urlstr.substr(1);
    }
    if(urlstr.indexOf('&') > -1)
    {
        var pvarr = urlstr.split('&');
        for(var i = 0; i < pvarr.length; i++)
        {
            var pv = pvarr[i].split('=');
            this.paraMap.put(pv[0], pv[1]);
        }
    }
    else
    {
        var pv = urlstr.split('=');
        this.paraMap.put(pv[0], pv[1]);
    }
}

Url.prototype =
{
    getvalue : function(para)
    {
        return this.paraMap.get(para);
    }
}