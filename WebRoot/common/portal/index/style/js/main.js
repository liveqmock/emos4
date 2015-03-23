function changeRow_color(obj) {
	var Ptr = document.getElementById(obj).getElementsByTagName("tr");
	for (var i = 1; i < Ptr.length + 1; i++)
	 {
		if (i % 2 > 0)
		 {
			Ptr[i - 1].className = "t1";
		}
		 else
		 {
			Ptr[i - 1].className = "t2";
		}
	}
	for (var i = 0; i < Ptr.length; i++) {
		Ptr[i].onmouseover = function() {
			this.tmpClass = this.className;
			this.className = "t3";
		};
		Ptr[i].onmouseout = function() {
			this.className = this.tmpClass;
		};
	}
}
function changediv(id_num, id, n)
 {
	for (var i = 1; i <= n; i++)
	 {
		var divName = document.getElementById("div1_" + id_num + i);
		var divObj = document.getElementById("div" + id_num + i);
		if (i == id)
		 {
			divName.className = 'menu_onfocus';
			divObj.style.display = '';
		}
		 else
		 {
			divName.className = 'menu_onblur';
			divObj.style.display = 'none';
		}
	}
}
function changefaqDiv(id_num, id, n)
 {
	for (var i = 1; i <= n; i++)
	 {
		var divName = document.getElementById("faq_" + id_num + i);
		var divObj = document.getElementById("faq" + id_num + i);
		if (i == id)
		 {
			divName.className = 'open';
			divObj.style.display = '';
		}
		 else
		 {
			divName.className = 'close';
			divObj.style.display = 'none';
		}
	}
}function changetabDiv(id_num, id, n)
 {
	for (var i = 1; i <= n; i++)
	 {
		var divName = document.getElementById("tab_" + id_num + i);
		var divObj = document.getElementById("tab" + id_num + i);
		if (i == id)
		 {
			divName.className = 'tab_show';
			divObj.style.display = '';
		}
		 else
		 {
			divName.className = 'tab_off';
			divObj.style.display = 'none';
		}
	}
}function changepageDiv(id_num, id, n)
 {
	for (var i = 1; i <= n; i++)
	 {
		var divName = document.getElementById("page_" + id_num + i);
		var divObj = document.getElementById("page" + id_num + i);
		if (i == id)
		 {
			divName.className = 'page_on';
			divObj.style.display = '';
		}
		 else
		 {
			divName.className = 'page_off';
			divObj.style.display = 'none';
		}
	}
}function changegongdanDiv(id_num, id, n)
 {
	for (var i = 1; i <= n; i++)
	 {
		var divName = document.getElementById("gongdan_" + id_num + i);
		var divObj = document.getElementById("gongdan" + id_num + i);
		if (i == id)
		 {
			divName.className = 'gongdan_on';
			divObj.style.display = '';
		}
		 else
		 {
			divName.className = 'gongdan_off';
			divObj.style.display = 'none';
		}
	}
}
function checkAll() {
	var chk = document.forms["form1"].elements["chkAll"];
	var inputObj = document.forms["form1"].getElementsByTagName("input");
	for (i = 0; i < inputObj.length; i++) {
		var temp = inputObj[i];
		if (temp.type == "checkbox") {
			temp.checked = chk.checked;
		}
	}
}