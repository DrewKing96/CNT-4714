<!--
	Name: Drew King
	Course: CNT 4714 -- FALL 2019 -- Project Four
	Assignment Title: A Three-Tier Distributed Web-Based Application
	Date: December 1, 2019
-->


<!DOCTYPE html>
<%-- start scriplet --%>
			<%
				String sqlStatement = (String) session.getAttribute("sqlStatement");
				
				String message = (String) session.getAttribute("message");
				if(message == null){
				 message = " ";
				}

				if(sqlStatement == null){
					sqlStatement = " ";
				}
			%>
<!--A Remote Database Management System-->
<html>

	<head>
		<meta charset="utf-8"/>
		<title>A Three-Tier Distributed Web-Based Application</title>
		<style type="text/css">
			<!--
				body{background-color: blue;}
				textarea{background-color: black;border:2px solid grey;color: lime;text-decoration:none;}
				input{background-color:black;color: yellow;}
				h1{text-align: center; color: white;}
				hr{color:white;}
				#userPrompts{text-align: center;color:white;}
				#footer{text-align: center;color:white;}
				::placeholder{color:lime;opacity: 1;}
			-->
		</style>
	</head>


	<body>
		<h1>Welcome to the Fall 2019 Project 4 Enterprise System A Remote Database Management System
		</h1>
		<hr>
		<div id="userPrompts">
			<p>
				You are connected to the Project 4 Enterprise System Database.
				Please enter any valid SQL query or update statement.
				If no query/update command is initially provided the Execute button will display all supplier information in the database.
				All execution results will appear below.
			</p>
			<form action='/Project4/buisnesslogic' method="post">
				<textarea name = sqlStatement id="sqlStatement" rows="10" cols="80"></textarea>
				<br/>
				<br/>
				<input type="submit" value="Execute Command" id="execute">
				<input method="get" type="reset" value="Clear Form" id="clear">
			</form>
		</div>
		<hr>
		<div id="footer">
			<h4>Database Results:</h4>
			

			<div style="width:50%; margin: 0 auto;">
				<%-- JSP expression to access servlet varibale: message --%>
				<%=message%>
			</div>
		</div>
	</body>
</html>