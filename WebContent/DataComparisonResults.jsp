<%@ page language="java" contentType="text/html; charset = ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Comparator Pro Results</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="common/javascript/dataComparison.js"></script>
	<link rel="stylesheet" type="text/css" href="common/css/dataComparison.css">
	
	<script>
    	$(document).ready(function(){
        	$("#mailSentModal").modal('show');
   	 	});
    	
    	$(document).scroll(function() {
    	    setFooterPosition();
    	});
	</script>
</head>

<body>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid headerResultsCustom">
			<div class="navbar-header">
				<h1 class="h1HomeCustom">Comparator Pro Results</h1>
			</div>
		</div>
	</nav>

	<c:set var="outputFile" value="" />
	<c:set var = "isMailSent" value='<%=application.getAttribute("isMailSent")%>' />
	<c:set var = "RowMissingMap" value='<%=application.getAttribute("rowMissingMap")%>' />
	<c:set var = "DataMismatchMap" value='<%=application.getAttribute("dataMismatchMap")%>' />
	
	<div class="container text-center containerCustom">
		
		<div class="row">
			<div class="col-sm-4 columnResultsCustom">
				<p class="fontNormal paraCustom">
					Click &nbsp; <a href="common/Data/ExcelOutput.xlsx">
						<img src="common/images/ExcelIcon.png" class="excelIcon"></a> &nbsp; to view in Excel
				</p>
			</div>
			<div class="col-sm-4 columnResultsCustom">				
				<form action="sendMail" id="sendMailForm" name="sendMailForm" method="post" class="form-inline">
					<div class="form-group">
					    <label class="fontNormal recepientEmailLabel" for="recepientEmail">Send Report to Mail</label> 
						<input class="form-control fontNormal recepientEmailInput" type="text" id="recepientEmail" name="recepientEmail" placeholder="Email" value="">
						<div class="input-group-btn inputGroupCustom">
							<button class="btn btn-default" type="button" onclick="sendReportToMail()">
								<i class="glyphicon glyphicon-envelope"></i>
							</button>
						</div>
						<div class="errorFont emailError" id="recepientEmailError">          					
        				</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="row rowCustom">		
			<nav class="navbar navbar-default navbarCustom">
				    <ul class="nav navbar-nav navUlCustom">
				      <li id="summaryLink" class="active navLiCustom navbarFont"><a href="#" onclick="showNavContent('summary')">Summary</a></li>
				      <li id="dataMismatchLink" class="navLiCustom navbarFont"><a href="#" onclick="showNavContent('dataMismatch')">Data Mismatch</a></li>
				      <li id="graphlink" class="navLiCustom navbarFont"><a href="#" onclick="showNavContent('graphs')">Graphs</a></li>
				    </ul>
			</nav>
		</div>
		
		<div class="row rowCustom" id="summaryNav">		
		  <div class="container-fluid">
			<p class="fontNormal paraCustom">
				Comparison of <%=application.getAttribute("sourceFileName")%> and <%=application.getAttribute("targetFileName")%> is successful
			</p>
			<c:forEach var="rowMissing" items="${RowMissingMap}">
				<div class="col-sm-4 columnTableCustom">			
				   <table class="table table-striped">
				    	<thead>
	      					<tr> <th class="fontNormal thCustom"> ${rowMissing.key} </th> </tr>
	    				</thead>
	    				<tbody>
					      	<c:forEach var="missingRow" items="${rowMissing.value}">
					           <tr>				        	 
					        	 <td class="fontNormal"><c:out value="${missingRow}"/></td>
					      	   </tr>
						  	</c:forEach>
				    	</tbody>
				    </table>
				 </div>		
			</c:forEach>
			</div>
		</div>
		
		<div class="row rowCustom" id="mismatchNav" style="display: none">		
		  <div class="container-fluid">
			 <c:set var="isHeader" value="true"/>
			 <c:set var="primaryKey" value=""/>
			 <table class="table table-striped">
			 	<c:forEach var="dataMismatch" items="${DataMismatchMap}">
			 	  <c:choose>
					<c:when test="${isHeader eq true}">
						<thead>
							<tr>
								<th class="fontNormal thCustom">${dataMismatch.value.primaryKey}</th>
								<th class="fontNormal thCustom">${dataMismatch.value.sourceColumnName}</th>
								<th class="fontNormal thCustom">${dataMismatch.value.sourceColumnValue}</th>
								<th class="fontNormal thCustom">${dataMismatch.value.targetColumnName}</th>
								<th class="fontNormal thCustom">${dataMismatch.value.targetColumnValue}</th>
							</tr>
						</thead>
						<c:set var="isHeader" value="false"/>
					</c:when>
					<c:otherwise>
						<tbody>
							 <tr>
							    <c:choose>
							    	<c:when test="${primaryKey ne dataMismatch.value.primaryKey}">
										<td class="fontNormal">${dataMismatch.value.primaryKey}</td>
										<c:set var="primaryKey" value="${dataMismatch.value.primaryKey}"/>
									</c:when>
									<c:otherwise>
										<td class="fontNormal"></td>
									</c:otherwise>
								</c:choose>
								<td class="fontNormal">${dataMismatch.value.sourceColumnName}</td>
								<td class="fontNormal">${dataMismatch.value.sourceColumnValue}</td>
								<td class="fontNormal">${dataMismatch.value.targetColumnName}</td>
								<td class="fontNormal">${dataMismatch.value.targetColumnValue}</td>
							</tr>
						</tbody>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</table>
			
			</div>
		</div>
		
		<div class="row rowCustom" id="graphNav" style="display: none">		
		</div>
		
	</div>
	
	<c:if test="${isMailSent eq true}">
		<div class="modal" id="mailSentModal">
		    <div class="modal-dialog">
		      <div class="modal-content">
		      
		        <!-- Modal body -->
		        <div class="modal-body">
		          Report Sent to Mail Successfully !
		        </div>
		        
		        <!-- Modal footer -->
		        <div class="modal-footer">
		          <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button>
		        </div>
		        
		      </div>
		    </div>
	  	</div>
  	</c:if>
  	
  	<c:remove var="isMailSent"/>
	
	<footer class="container-fluid footerCustom" id="footerDiv">
		<p class="fontNormal">Copyright &copy; 2020, UTIS Automation All
			Rights Reserved.</p>
	</footer>
</body>
</html>