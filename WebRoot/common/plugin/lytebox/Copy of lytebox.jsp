<script src="${ctx}/plugin/lytebox/popup/common.js" type="text/javascript"></script>
<script src="${ctx}/plugin/lytebox/popup/subModal.js" type="text/javascript"></script>

<script type="text/javascript">
function inputlyte(id){
var url;
if(!id){
url = document.getElementById('lytebox').href;
}else{
url = document.getElementById(id).href;
}
urllyte(url);
}

function urllyte(url){
showPopWin(url, 300, 150, null);
}
</script>
