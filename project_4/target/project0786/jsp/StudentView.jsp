<%@page import="in.co.sunrays.util.HTMLUtility"%>
<%@page import="javax.swing.text.html.HTML"%>
<%@page import="in.co.sunrays.bean.CollegeBean"%>
<%@page import="java.util.List"%>

<%@page import="in.co.sunrays.controller.StudentCtl"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.controller.ORSView"%>
<%@page import="in.co.sunrays.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<meta charset="ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<title>Student Registration page</title>
</head>
<body>

<center>

<jsp:useBean id="bean" class="in.co.sunrays.bean.StudentBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.STUDENT_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<%
			List clist = (List) request.getAttribute("collegeList");
		%>
		<h1>
			<%
				if (bean != null && bean.getId() > 0) {
			%>
			<tr>
				<th><font>Update Student</font></th>
				<%
					} else {
				%>
				<th><font>Add Student</font></th>
			</tr>
			<%
				}
			%>
		</h1>

		<div>
			<h1>
				<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h1>
			<h1>
				<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h1>
		</div>
		<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
			type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedby"
			value="<%=bean.getModifiedBy()%>"> <input type="hidden"
			name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
		<input type="hidden" name="modifieddatetime"
			value="<%=bean.getModifiedDatetime()%>">

		<table>


			<tr>
				<th align="left">CollegeName <span style="color: red">*</span></th>
				<td><%=HTMLUtility.getList("collegename", String.valueOf(bean.getCollegeId()), clist)%>
				<td style="position: fixed"><font color="red">
				<%=ServletUtility.getErrorMessage("collegename", request)%></font>
				</td>
			</tr>
			<tr>
				<th style="padding: 3px"></th>
			</tr>
			<tr>
				<th align="left">FirstName <span style="color: red">*</span></th>
				<td><input type="text" name="firstname"
					placeholder="Enter First Name" size="25"
					value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
				<td style="position: fixed"><font color="red">
				<%=ServletUtility.getErrorMessage("firstname", request)%></font>
				</td>
			</tr>
			<tr>
				<th style="padding: 3px"></th>
			</tr>
			<tr>
				<th align="left">LastName <span style="color: red">*</span></th>
				<td><input type="text" name="lastname"
					placeholder="Enter Last Name" size="25"
					value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
				<td style="position: fixed"><font color="red">
				<%=ServletUtility.getErrorMessage("lastname", request)%></font>
				</td>
			</tr>
			<tr>
				<th style="padding: 3px"></th>
			</tr>
			<tr>
				<th align="left">Date Of Birth <span style="color: red">*</span></th>
				<td><input type="text" name="dob" id="datepicker"
					readonly="readonly" placeholder=" Date of Birth" size="25"
					value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
				<td style="position: fixed"><font color="red"> 
				<%=ServletUtility.getErrorMessage("dob", request)%></font></td>
			</tr>
			<tr>
				<th style="padding: 3px"></th>
			</tr>
			<tr>
				<th align="left">MobileNo <span style="color: red">*</span></th>
				<td><input type="text" name="mobile" maxlength="10"
					placeholder="Enter Mobile No" size="25"
					value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
				<td style="position: fixed"><font color="red">
				<%=ServletUtility.getErrorMessage("mobile", request)%></font>
				</td>
			</tr>
			<tr>
				<th style="padding: 3px"></th>
			</tr>
			<tr>
				<th align="left">Email-Id <span style="color: red">*</span></th>
				<td><input type="text" name="email"
					placeholder="Enter Email_Id" size="25"
					value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
				<td style="position: fixed"><font color="red">
				<%=ServletUtility.getErrorMessage("email", request)%></font>
				</td>
			</tr>
			<tr>
				<th style="padding: 3px"></th>
			</tr>


			<tr>
	<th></th>
		<%
		if(bean.getId() > 0){ %>
		<td>
		<input type="submit" name="operation" value="<%=StudentCtl.OP_UPDATE%>">
		<input type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>"></td>
		<%}else{ %>
		<td>
		<input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE %>">
		<input type="submit" name="operation" value="<%=StudentCtl.OP_RESET%>"></td>
	
		<%} %>
	</tr>
		</table>
	</form>

</center>
              <%@include file="Footer.jsp"%>
</body> 
</html>