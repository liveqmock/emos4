var activeQuickBar = false;
var activeQuickBarName = '';
var focusQuickBar = false;

function showQuickBar(barName)
{
	if(document.getElementById(barName) && activeQuickBar)
	{
		if(!focusQuickBar || activeQuickBarName != barName)
		{
			hideQuickBar();
			activeQuickBarName = barName;
			document.getElementById(activeQuickBarName).style.display = '';
			if(activePageMenu != 'td' + activeQuickBarName.substring(9))
			{
				document.getElementById('td' + activeQuickBarName.substring(9)).className = 'menu_onquick';
			}
			focusQuickBar = true;
		}
	}
}

function hideQuickBar()
{
	if(activeQuickBarName != '')
	{
		document.getElementById(activeQuickBarName).style.display = 'none';
		if(activePageMenu == 'td' + activeQuickBarName.substring(9))
		{
			document.getElementById('td' + activeQuickBarName.substring(9)).className = 'menu_onfocus';
		}
		else
		{
			document.getElementById('td' + activeQuickBarName.substring(9)).className = 'menu_onblur';
		}
		activeQuickBarName = '';
	}
	focusQuickBar = false;
}

function leaveQuickBar()
{
	focusQuickBar = false;
	hideQuickBar();
}

function openQuickBarBtnLink(url, type)
{
	window.open(url, type);
}