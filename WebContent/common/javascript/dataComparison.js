function showSourceSubSection() {
	var selection = document.getElementById("sourceSelection").value;
	if(selection == 'file') {
		document.getElementById("sourceFileDiv").style.display = "block";
		document.getElementById("sourceQueryDiv").style.display = "none";
		document.getElementById("sourceAPIDiv").style.display = "none";
		document.getElementById("sourceSelectionError").innerHTML = "";
	} else if(selection == 'database') {
		document.getElementById("sourceQueryDiv").style.display = "block";
		document.getElementById("sourceFileDiv").style.display = "none";
		document.getElementById("sourceAPIDiv").style.display = "none";
		document.getElementById("sourceSelectionError").innerHTML = "";
	} else if(selection == 'salesforce') {
		document.getElementById("sourceAPIDiv").style.display = "block";
		document.getElementById("sourceQueryDiv").style.display = "none";
		document.getElementById("sourceFileDiv").style.display = "none";
		document.getElementById("sourceSelectionError").innerHTML = "";
	} else {
		document.getElementById("sourceAPIDiv").style.display = "none";
		document.getElementById("sourceQueryDiv").style.display = "none";
		document.getElementById("sourceFileDiv").style.display = "none";
	}
}

function showTargetSubSection() {
	var selection = document.getElementById("targetSelection").value;
	if(selection == 'file') {
		document.getElementById("targetFileDiv").style.display = "block";
		document.getElementById("targetQueryDiv").style.display = "none";
		document.getElementById("targetAPIDiv").style.display = "none";
		document.getElementById("targetSelectionError").innerHTML = "";
	} else if(selection == 'database') {
		document.getElementById("targetQueryDiv").style.display = "block";
		document.getElementById("targetFileDiv").style.display = "none";
		document.getElementById("targetAPIDiv").style.display = "none";
		document.getElementById("targetSelectionError").innerHTML = "";
	} else if(selection == 'salesforce') {
		document.getElementById("targetAPIDiv").style.display = "block";
		document.getElementById("targetQueryDiv").style.display = "none";
		document.getElementById("targetFileDiv").style.display = "none";
		document.getElementById("targetSelectionError").innerHTML = "";
	} else {
		document.getElementById("targetAPIDiv").style.display = "none";
		document.getElementById("targetQueryDiv").style.display = "none";
		document.getElementById("targetFileDiv").style.display = "none";
	}
}

function showNavContent(navigation) {	
	if(navigation == 'summary') {
		document.getElementById("summaryNav").style.display = "block";
		document.getElementById("mismatchNav").style.display = "none";
		document.getElementById("graphNav").style.display = "none";
	} else if(navigation == 'dataMismatch') {
		document.getElementById("mismatchNav").style.display = "block";
		document.getElementById("summaryNav").style.display = "none";
		document.getElementById("graphNav").style.display = "none";
		document.getElementById("summaryLink").classList.remove("active");
	} else if(navigation == 'graphs') {
		document.getElementById("graphNav").style.display = "block";
		document.getElementById("summaryNav").style.display = "none";
		document.getElementById("mismatchNav").style.display = "none";
		document.getElementById("summaryLink").classList.remove("active");
	} 
}

function validateFormAndSubmit() {
	var isErrorFound = false;
	
	var sourceSelection = document.getElementById("sourceSelection").value;
	
	if(sourceSelection == 'file' && document.getElementById("sourceFile").value == "") {
		document.getElementById("sourceFileError").innerHTML = "Please Select a Source File";
		isErrorFound = true;
	} else if(sourceSelection == 'database' && document.getElementById("sourceQuery").value == "") {
		document.getElementById("sourceQueryError").innerHTML = "Please Enter a Source Query";
		isErrorFound = true;
	} else if(sourceSelection == 'salesforce' && document.getElementById("sourceAPI").value == "") {
		document.getElementById("sourceAPIError").innerHTML = "Please Enter a Source API";
		isErrorFound = true;
	} else if (sourceSelection == "") {
		document.getElementById("sourceSelectionError").innerHTML = "Please Select a Source Method";
		isErrorFound = true;
	}
	
	var targetSelection = document.getElementById("targetSelection").value;
	
	if(targetSelection == 'file' && document.getElementById("targetFile").value == "") {
		document.getElementById("targetFileError").innerHTML = "Please Select a Target File";
		isErrorFound = true;
	} else if(targetSelection == 'database'  && document.getElementById("targetQuery").value == "") {
		document.getElementById("targetQueryError").innerHTML = "Please Enter a Target Query";
		isErrorFound = true;
	} else if(targetSelection == 'salesforce' && document.getElementById("targetAPI").value == "") {
		document.getElementById("targetAPIError").innerHTML = "Please Enter a Target API";
		isErrorFound = true;
	} else if (targetSelection == "") {
		document.getElementById("targetSelectionError").innerHTML = "Please Select a Target Method";
		isErrorFound = true;
	}
	
	if(isErrorFound) {
		return false;
	} else {
		document.getElementById("dataCompareForm").submit();
	}
}

function sendReportToMail() {
	if(document.getElementById("recepientEmail").value == "") {
		document.getElementById("recepientEmailError").innerHTML = "Please Enter a valid Email";
		return false;
	} else {
		document.getElementById("sendMailForm").submit();
	}
}

function setFooterPosition() {
	//document.getElementById("footerDiv").style.position = "absolute";
}


