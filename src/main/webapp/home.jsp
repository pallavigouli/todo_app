<%@page import="todo_app.dto.Task"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

th {
	background-color: #f2f2f2;
}

td {
	font-weight: bold;
}

.status-in-progress {
	color: orange;
}

.status-not-started {
	color: red;
}

.delete, .edit {
	text-decoration: none;
	padding: 5px 10px;
	margin: 2px;
	cursor: pointer;
	border: 1px solid #ccc;
	border-radius: 4px;
	display: inline-block;
}

.delete {
	background-color: #f44336;
	color: white;
	font-weight: bold;
}

.edit {
	background-color: purple;
	color: white;
	font-weight: bold;
}

.addbtn {
	background-color: green;
	color: white;
	font-weight: bold;
	padding: 10px 20px;
	font-size: 16px;
	border-radius: 10px;
}

.logout {
	background-color: red;
	color: white;
	font-weight: bold;
	padding: 10px 20px;
	font-size: 16px;
	border-radius: 10px;
}

.delete:hover {
	background-color: grey;
}

.edit:hover {
	background-color: lightblue;
	color:black;`
}

.btns {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.compbtn {
	background-color: blue;
	color: white;
	font-weight: bold;
	padding: 10px 16px;
	font-size: 16px;
	border-radius: 10px;
}
</style>
</head>
<body>
	<h1>Home Page</h1>
	<%
	request.getAttribute("tasks");
	%>
	<%
	List<Task> tasks = (List<Task>) request.getAttribute("tasks");
	%>
	<%
	if (!tasks.isEmpty()) {
	%>
	<table>
		<thead>
			<tr>
				<th>Task Image</th>
				<th>Task Name</th>
				<th>Task Description</th>
				<th>Created Time</th>
				<th>Status</th>
				<th>Delete</th>
				<th>Edit</th>
			</tr>
		</thead>
		<%
		for (Task task : tasks) {
		%>
		<tbody>
			<tr>
				<td><img height="80px" width="160px"
					src="data:image/png;base64,<%=task.getEncodeImage()%>" /></td>
				<td><%=task.getName()%></td>
				<td><%=task.getDescription()%></td>
				<td><%=task.getAddedTime()%></td>
				<%
				if (task.isStatus()) { //for status to check based on true false
				%>
				<td>Completed</td>
				<%
				} else {
				%>
				<td><a href="complete-task?id=<%= task.getId() %>"><button class="compbtn">Complete</button></a></td>
				<%
				}
				%>
				<td><a href="delete-task?id=<%= task.getId() %>" class="delete">Delete</a></td>
				<td><a href="edit-task.jsp?id=<%= task.getId() %>" class="edit">Edit</a></td>
			</tr>
		</tbody>
		<%
		}
		%>
	</table>
	<br>
	<br>
	<%
	}
	%>
	<div class="btns">
		<a href="add-task.html">
			<button class="addbtn">Add Task</button>
		</a> <a href="logout"><button class="logout">Logout</button></a>
	</div>
</body>
</html>