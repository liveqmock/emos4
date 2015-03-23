
var fileInput;
var submitButton;
var progressBarBoxContent;
var progressBar;
function refreshProgress() {
	UploadMonitor.getUploadInfo(updateProgress);
}

function updateProgress(uploadInfo) {
 if (uploadInfo.inProgress) {
  var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);
  if(parseInt(progressPercent) < 0) return true;
  progressBar.style.display = 'block';
  fileInput.style.display = 'none';
  fileInput.disabled = true;
  submitButton.disabled = true;

  var fileIndex = uploadInfo.fileIndex;
  progressBarBoxContent.innerHTML = progressPercent + '%';
  progressBarBoxContent.style.width = parseInt(progressPercent * 3.5) + 'px';

  window.setTimeout('refreshProgress()', 100);
 } else {
  submitButton.disabled = false;
  fileInput.disabled = false;
  progressBar.style.display = 'none';
 }
 return true;
}

function startProgress(fileInputName, submitButtonName) {
	fileInput = document.getElementById(fileInputName);
	submitButton = document.getElementById(submitButtonName);
	progressBar = document.getElementById("progressBar");
	progressBarBoxContent = document.getElementById("progressBarBoxContent");
	if (fileInput != null && submitButton != null && progressBar != null && progressBarBoxContent != null) {
		progressBarBoxContent.innerHTML = "0%";
		window.setTimeout("refreshProgress()", 150);
		return true;
	} else {
		alert("Ajax Upload ERROR:some element of Ajax Upload is null!!");
		return false;
	}
}


