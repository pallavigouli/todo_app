<!DOCTYPE html>
<%@page import="todo_app.dto.Task"%>
<%@page import="todo_app.dao.TodoDao"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
 <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            font-weight: bold;
        }

        form {
            background-color: #fff;
            padding: 35px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            height:600px;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin-top:15px;
        }

        input {
            width: 100%;
            padding: 10px 30px;
            margin-top: 9px;
            box-sizing: border-box;
            border-radius: 10px;
        }

        button {
            background-color: #4caf50;
            color: #fff;
            padding: 14px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-weight: bold;
            font-size: 20px;
        }
        button:hover {
            background-color: #45a049;
        }
        h1{
			color: mediumpurple;
			margin-top: 0px;
		}
		.cancelbtn{
			background-color: red;
		}
		.cancelbtn:hover {
            background-color:orangered;
        }

    </style>
    <title>Task Details Form</title>
</head>
<body>
    <form action="edittask" method="post" enctype="multipart/form-data">
    <%
	int id=Integer.parseInt(request.getParameter("id"));
	TodoDao dao=new TodoDao();
	Task task=dao.findTaskById(id);
	%>
		<h1 align="center">Edit Task</h1>
        <h2>Enter Task Details</h2>
        <input type="hidden" name="id" value="<%=task.getId()%>">
        <label for="name">Task Name:</label>
        <input type="text" id="name" name="name" required value="<%= task.getName() %>">

        <label for="description">Description:</label>
        <input type="text" id="description" name="description" required value="<%= task.getDescription()%>">

        <label for="image">Image:</label>
        <input type="file" id="image" name="image"><img height="80px" width="160px"
					src="data:image/png;base64,<%=task.getEncodeImage()%>" /><br><br>

        <button>Edit Task</button><br><br>
        <button type="reset" class="cancelbtn">Cancel</button>
    </form>

</body>
</html>