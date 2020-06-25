<%@ page language="java" contentType="text/html; charset = ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Comparator Pro</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="common/javascript/dataComparison.js"></script>
	<link rel="stylesheet" type="text/css" href="common/css/dataComparison.css">
</head>

<body>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid headerHomeCustom">
			<div class="navbar-header">
				<h1 class="h1HomeCustom">Comparator Pro</h1>
			</div>
		</div>
	</nav>

	<div class="container text-center containerCustom">
		<div class="row">
		
			<div class="col-sm-4 columnHomeCustom">
				<div id="imageCarousel" class="carousel slide textCenterCustom" data-ride="carousel">
					<div class="carousel-inner" role="listbox">
						<div class="item active"> 
							<img class="d-block w-100 img-rounded" src="common/images/DB.jpg" alt="First slide">
						</div>
						<div class="item">
							<img class="d-block w-100 img-rounded" src="common/images/File.jpg"	alt="Second slide">
						</div>
						<div class="item">
							<img class="d-block w-100 img-rounded" src="common/images/API.jpg" alt="Third slide">
						</div>
						<div class="item">
							<img class="d-block w-100 img-rounded" src="common/images/DWH.jpeg" alt="Third slide">
						</div>
					</div>
					<a class="carousel-control-prev carouselLinkCustom" href="#imageCarousel" role="button" data-slide="prev"> 
						<span class="carousel-control-prev-icon" aria-hidden="true"></span> 
						<span class="sr-only">Previous</span>
					</a> 
					<a class="carousel-control-next carouselLinkCustom" href="#imageCarousel" role="button" data-slide="next"> 
						<span class="carousel-control-next-icon" aria-hidden="true"></span> 
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>
			
			<form action="compareData" id="dataCompareForm" name="dataCompareForm" method="post">
			
				<div class="col-sm-4 columnHomeCustom">
					<h2 class="fontNormal textCenterCustom">Source System</h2>
					<div class="form-group formGroupCustom">
						<label class="fontCursive" for="sourceSelection">Select Source Using *</label> 
						<select class="form-control formInputCustom" name="sourceSelection" id="sourceSelection" onchange="showSourceSubSection()">
							<option class="fontCursive" value="" selected disabled>Select One</option>
							<option class="fontCursive" value="file">Files</option>
							<option class="fontCursive" value="database">Databases</option>
							<option class="fontCursive" value="salesforce" disabled>Sales Force</option>
						</select>
						<div class="errorFont" id="sourceSelectionError">          					
        				</div>
					</div>
	
					<div class="form-group formGroupCustom" id="sourceFileDiv" style="display: none">
						<label class="fontCursive" for="sourceFile">Source File *</label> 
						<input class="fontCursive" type="file" id="sourceFile" name="sourceFile" value=""/>
						<div class="errorFont" id="sourceFileError">          					
        				</div>
					</div>
	
					<div class="form-group" id="sourceQueryDiv" style="display: none">
						<label class="fontCursive" for="sourceQuery">Source Query *</label>
						<textarea class="form-control formInputCustom" rows="5" id="sourceQuery" name="sourceQuery" value=""></textarea>
						<div class="errorFont" id="sourceQueryError">          					
        				</div>
					</div>
	
					<div class="form-group" id="sourceAPIDiv" style="display: none">
						<label class="fontCursive" for="sourceAPI">Source API *</label> 
						<input type="text" class="form-control formInputCustom" id="sourceAPI" name="sourceAPI" value=""/>
						<div class="errorFont" id="sourceAPIError">          					
        				</div>
					</div>
				</div>
			
				<div class="col-sm-4 columnHomeCustom">
					<h2 class="fontNormal textCenterCustom">Target System</h2>
					<div class="form-group formGroupCustom">
						<label class="fontCursive" for="targetSelection">Select Target Using *</label> 
						<select class="form-control formInputCustom" name="targetSelection" id="targetSelection" onchange="showTargetSubSection()" >
							<option class="fontCursive" value="" selected disabled>Select One</option>
							<option class="fontCursive" value="file">Files</option>
							<option class="fontCursive" value="database">Databases</option>
							<option class="fontCursive" value="salesforce" disabled>Sales Force</option>
						</select>
						<div class="errorFont" id="targetSelectionError">          					
        				</div>
					</div>
						
					<div class="form-group formGroupCustom" id="targetFileDiv" style="display: none">
						<label class="fontCursive" for="sourceFile">Target File *</label> 
						<input class="fontCursive" type="file" id="targetFile" name="targetFile" value="">
						<div class="errorFont" id="targetFileError">          					
        				</div>
					</div>
	
					<div class="form-group" id="targetQueryDiv" style="display: none">
						<label class="fontCursive" for="targetQuery">Target Query *</label>
						<textarea class="form-control formInputCustom" rows="5" id="targetQuery" name="targetQuery" value=""></textarea>
						<div class="errorFont" id="targetQueryError">          					
        				</div>
					</div>
	
					<div class="form-group" id="targetAPIDiv" style="display: none">
						<label class="fontCursive" for="targetAPI">Target API *</label> 
						<input type="text" class="form-control formInputCustom" id="targetAPI" name="targetAPI" value="">
						<div class="errorFont" id="targetAPIError">          					
        				</div>
					</div>
				</div>		
				
			</form>
		</div>
		
		<button type="button" onclick="validateFormAndSubmit()" class="btn btn-primary fontNormal compareButton">Compare Data</button>
	</div>
	
	<footer class="container-fluid footerCustom">
		<p class="fontNormal">Copyright &copy; 2020, UTIS Automation All Rights Reserved.</p>
  	</footer>

</body>
</html>