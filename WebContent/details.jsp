<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Quote Page</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
  <%@include file="css/styles.css" %>
  
</style>
<%
String id = request.getParameter("userId");
String driverName = "com.mysql.jdbc.Driver";
String connectionUrl = "jdbc:mysql://localhost:3306/";
String dbName = "homeinsurance";
String userId = "root";
String password = "root";

try {
Class.forName(driverName);
} catch (ClassNotFoundException e) {
e.printStackTrace();
}

Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
%>
</head>
<body>
<body>
   <div id="wrapper">
<div align="center">
<h2 class= "brown-text"><img class="border-resize" src="http://2.bp.blogspot.com/-ShuRVrIaSvA/USMNj7THdsI/AAAAAAAAIV4/X8WVshITaHY/s1600/red1.jpg"> Homeowners Insurance</h2>
  <div class="topnav">
  <a class="active" href="#home">Home |</a>
  <a href="#news">Get Quote |</a>
  <a href="#contact">Retrive Quote |</a>
  <a href="#about">My Polices |</a>
    <a href="#Logout">Logout</a>
</div>
  
  </div>
  </div> <hr />
  <div align="center">
    <h2 class="brown-text"> Quote Details </h2>
    
  </div>

  <div align="center">
  <form method="post" action="">
  <table>
  <tr>
  <th>Quote id</th>
  <th> Residence type</th>
  <th> Address</th>
  <th> City</th>
  <th> State</th>
  <th> Zip</th>
  <th> Residence Use</th>
  <th> Status </th>
  <th> Rest of Information</th>
  </tr>
  <%
try{ 
connection = DriverManager.getConnection(connectionUrl+dbName, userId, password);
statement=connection.createStatement();
String sql ="SELECT * FROM get_quote WHERE user_id=?";

resultSet = statement.executeQuery(sql);
while(resultSet.next()){
%>
  <tr>
 <td><%=resultSet.getString("quote_id") %></td>
<td><%=resultSet.getString("resdience_type") %></td>
<td><%=resultSet.getString("address") %></td>
<td><%=resultSet.getString("city") %></td>
<td><%=resultSet.getString("state") %></td>
<td><%=resultSet.getString("zip") %></td>
<td><%=resultSet.getString("resdience_use") %></td>
<td>New</td>
<td><a href=#>Click</a></td>
</tr>

<% 
}

} catch (Exception e) {
e.printStackTrace();
}
%>
  
  
  </table>
  </form>
  </div>

</body>



 <hr />
<div align="center">
<h2 class="brown-text"> Copyright © 2016 Homeownersinsurance.com All Rights Reserved </h2>
  </div>
</html>