<%@page import="in.co.sunrays.controller.*"%>
<%@page import="in.co.sunrays.controller.BaseCtl"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<meta charset="ISO-8859-1">
<title>RoleIdView</title>
</head>
<body>
       <form action="<%=ORSView.ROLE_CTL%>" method="post">
        <%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.sunrays.bean.RoleBean" scope="request"></jsp:useBean>
       
       <center>
       
           <h1>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				
				<tr>
					<th><font>Update Role</font></th>
				</tr>
				
				<%
					} else {
				%>
				<tr>
					<th><font>Add Role</font></th>
				</tr>
				<%
					}
				%>
			</h1>
       
            <H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
			</H2>
       
          <input type="hidden" name="id" value="<%=bean.getId()%>"> 
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
		<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
       
       <table>
       
       <tr>
                    <th align="left">Name <span style="color: red">*</span></th>
 <td><input type="text" name="name" placeholder="Enter name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
 <td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
              
       </tr>
       
       <tr>
            <th align="left">Description <span style="color: red">*</span></th>
         <td><input type="text" name="description" placeholder="Enter Description" 
          value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
                
       </tr>
       
       <tr><th></th>
               
                <%if(bean.getId()>0) {%>
               
                    <td colspan="2">
                     &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=RoleCtl.OP_UPDATE%>">                    
                      &nbsp;  &nbsp;
                     <input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL%>"></td>
               
                <%}else{ %>
               
                	 <td colspan="2">
                	  &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=RoleCtl.OP_SAVE%>">                    
                      &nbsp;  &nbsp;
                     <input type="submit" name="operation" value="<%=RoleCtl.OP_RESET%>"></td>
               
                <%} %>
               
                </tr>
       
       </table>
       
       </center>
       
       
       </form>
 
      <%@ include file="Footer.jsp"%>

</body>
</html>