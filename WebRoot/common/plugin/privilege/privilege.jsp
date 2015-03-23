<script type="text/javascript">

function Privilege() {
	this.initialize();
}
Privilege.prototype.initialize = function() {
	this.updatePrivilegeItems();	
};
Privilege.prototype.updatePrivilegeItems = function() {
	var anchors = document.getElementsByTagName('privilege');
	for (var i = 0; i < anchors.length; i++) {
		var anchor = anchors[i];
		var opCode = anchor.getAttribute('opCode');
		var forAttribute = String(anchor.getAttribute('for'));
		var forId = document.getElementById(forAttribute);
	    if(forId)
			forId.style.display = '';
	}
};

if (window.addEventListener) {
	window.addEventListener("load",initPrivilege,false);
} else if (window.attachEvent) {
	window.attachEvent("onload",initPrivilege);
} else {
	window.onload = function() {initPrivilege();}
}
function initPrivilege() { myPrivilege = new Privilege(); }

</script>
