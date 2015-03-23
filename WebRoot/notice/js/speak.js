// Create the Sapi SpVoice object 
var VoiceObj;
try{
	VoiceObj = new ActiveXObject("Sapi.SpVoice");
}catch(exception){
}
		// ChangeVoice() function:
		// This function sets the newly selected voice choice from the Voice
		// Select box on the Voice object. 
function ChangeVoice() {
	var i = parseInt(idsVoices.value);
	VoiceObj.Voice = VoiceObj.GetVoices().Item(i);
} 
		
		// ChangeAudioOutput() function:
		// This function sets the newly selected audio output choice from the
		// Audio Output Select box on the Voice object. 
function ChangeAudioOutput() {
	var i = parseInt(idsAudioOutputs.value);
	VoiceObj.AudioOutput = VoiceObj.GetAudioOutputs().Item(i);
} 
		
		// IncRate() function:
		// This function increases the speaking rate by 1 up to a maximum
		// of 10. 
function IncRate() {
	if (VoiceObj.Rate < 10) {
		VoiceObj.Rate = VoiceObj.Rate + 1;
	}
} 
		
		// DecRate() function:
		// This function decreases the speaking rate by -1 down to a minimum
		// of -10. 
function DecRate() {
	if (VoiceObj.Rate > -10) {
		VoiceObj.Rate = VoiceObj.Rate - 1;
	}
} 
		// IncVol() function:
		// This function increases the speaking volume by 10 up to a maximum
		// of 100. 
function IncVol() {
	if (VoiceObj.Volume < 100) {
		VoiceObj.Volume = VoiceObj.Volume + 10;
	}
} 
		
		// DecVol() function:
		// This function decreases the speaking volume by -10 down to a minimum
		// of 0. 
function DecVol() {
	if (VoiceObj.Volume > 9) {
		VoiceObj.Volume = VoiceObj.Volume - 10;
	}
} 
