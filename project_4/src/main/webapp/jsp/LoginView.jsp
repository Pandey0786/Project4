<%@page import="in.co.sunrays.controller.LoginCtl"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
</head>
<body>
       <form action="<%=ORSView.LOGIN_CTL%>" method="post">
       
       <%@ include file="Header.jsp"%>
       
      <jsp:useBean id="bean" class="in.co.sunrays.bean.UserBean" scope="request"></jsp:useBean>
      
      <input type="hidden" name="URI" value="<%=session.getAttribute("uri")%>">
      
      <center>
                  <h1>Login</h1>
            <H1>
              <font color = "red"> <%= ServletUtility.getErrorMessage(request) %></font>
              <font color = "green"> <%= ServletUtility.getSuccessMessage(request) %></font>
            </H1>
      
         <% 
           String msg = (String) request.getAttribute("message");
            if (msg != null) {
         %>
         <h1 align="center">
			<font style="color: red"><%=msg%></font>
		</h1>
			<%
				}
			%>
        
          <input type="hidden" name="id" value="<%=bean.getId() %>">
          <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
          <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
		<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">   
		
		<table>
		
		<tr>
		
		<th align="left">LoginId <span style="color: red">*</span></th>
   <td><input type="text" name="login" size=30 placeholder="Enter LoginID" 
          value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
 <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>          
		
		</tr>
		
		<tr>
		
			<th align="left">Password<span style="color: red">*</span></th>
			<td><input type="password" name="password" size=30 placeholder="Enter Password" 
		value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				
		</tr>
		
		<tr>
			
			<th></th>
			<td colspan="2"><input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN%>"> &nbsp; 
			<input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>"> &nbsp;</td>
			
		</tr>
		
		<tr>
				<th></th>
				<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>""><b>Forget my password</b></a>&nbsp;</td>
		</tr>
				
		</table>
		  
      </center>
       
       </form>
       
     <%@ include file="Footer.jsp"%>
</body>
</html>