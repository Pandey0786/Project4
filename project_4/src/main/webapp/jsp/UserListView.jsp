<%@page import="in.co.sunrays.model.UserModel"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>
<%@page import="in.co.sunrays.controller.UserListCtl"%>
<%@page import="in.co.sunrays.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>User List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>
</head>
<body>
	<jsp:useBean id="bean" class="in.co.sunrays.bean.UserBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.USER_LIST_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<%
			List slist = (List) request.getAttribute("name");
		%>

		<center>
			<div align="center">
				<h1>User List</h1>
				<h1>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h1>
				<h1>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h1>
			</div>
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<UserBean> it = list.iterator();

				if (list.size() != 0) {
			%>



			<table width="100%">
				<tr>
					<td align="center"><label>FirstName :</label> <%=HTMLUtility.getList("firstName", String.valueOf(bean.getId()), slist)%>
						&emsp; <label>LoginId:</label> <input type="text" name="login"
						placeholder="Enter Login_ID "
						value="<%=ServletUtility.getParameter("login", request)%>">
						&emsp; <input type="submit" name="operation"
						value="<%=UserListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=UserListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr>
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>
						<th>SNo.</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>Role</th>
					<th>Gender</th>
					<th>DOB</th>
					<th>Edit</th>
				</tr>




				<%
					while (it.hasNext()) {
							UserBean usb = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=usb.getId()%>" <%if (usb.getRoleId() == 1) {%>
						disabled="disabled" <%}%>></td>
						<td><%=index++%></td>
					<td><%=usb.getFirstName()%></td>
					<td><%=usb.getLastName()%></td>
					<td><%=usb.getLogin()%></td>
					<%
						if (usb.getRoleId() == 1) {
					%><td>Admin</td>
					<%
						} else if (usb.getRoleId() == 2) {
					%><td>Student</td>
					<%
						} else if (usb.getRoleId() == 3) {
					%><td>College</td>
					<%
						} else if (usb.getRoleId() == 4) {
					%><td>Kiosk</td>
					<%
						} else {
					%><td>Faculty</td>
					<%
						}
					%>


					<td><%=usb.getGender()%></td>
					<td><%=usb.getDob()%></td>
					<%
						if (usb.getRoleId() == 1) {
					%>
					<td><a href="UserCtl?id=<%=usb.getId()%>"
						onclick="return false;">Edit</a></td>
					<%
						} else {
					%><td><a href="UserCtl?id=<%=usb.getId()%>">Edit</a></td>
					<%
						}
					%>


				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
		 				value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>


					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>

					<%
						UserModel model = new UserModel();
					%>
					<%
						if (list.size() < pageSize || model.nextPK() - 1 == bean.getId()) {
					%><td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=UserListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"></td>
					<%
						}
					%>
				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=UserListCtl.OP_BACK%>"></td>
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</center>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>

	<br>
	<br>
	<%@include file="Footer.jsp"%>
</body>
</html>