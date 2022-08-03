<%@page import="in.co.sunrays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="<%=ORSView.WELCOME_CTL%>">
        <%@ include file="Header.jsp"%>
               <%@ include file="Footer.jsp" %> 
                    <h1 align="Center">
                        <font size="10px" color="red">Welcome to ORS </font>
                    </h1>
                    <h1><%=ServletUtility.getSuccessMessage(request) %></h1>
        
                    <%
                  UserBean usb = (UserBean) session.getAttribute("user");
                       if (usb != null) {
                           if (usb.getRoleId() == 2) {
                    %>
        
                    <h2 align="Center">
                        <a href="<%=ORSView.GET_MARKSHEET_CTL%>"> Click here to see your Marksheet </a>
                    </h2>
                     
                     <%
                          }
                       }
                     %>
                </form>
</body>
</html>