package todo_app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import todo_app.dao.TodoDao;
import todo_app.dto.Task;
import todo_app.dto.User;

public class TodoService {
	TodoDao dao = new TodoDao();

	public void signup(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		User user = new User();
		user.setEmail(req.getParameter("email"));
		user.setName(req.getParameter("name"));
		user.setGender(req.getParameter("gender"));
		user.setPassword(req.getParameter("password"));
		user.setPhone(Long.parseLong(req.getParameter("phone")));
		user.setDob(req.getParameter("dob"));

		// to check duplicates
		List<User> users1 = dao.findByEmail(user.getEmail());
		List<User> users2 = dao.findByMobile(user.getPhone());
		if (users1.isEmpty() && users2.isEmpty()) {
			dao.saveUser(user);
			resp.getWriter().print("<h1>Account Created successfully</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} else {
			if (users1.isEmpty()) {
				resp.getWriter().print("<h1 style='color:red'>Mobile number should be unique!</h1>");
			} else if (users2.isEmpty()) {
				resp.getWriter().print("<h1 style='color:red'>Email should be unique!</h1>");
			} else {
				resp.getWriter().print("<h1 style='color:red'>Email and Mobile should be unique!</h1>");
			}
			req.getRequestDispatcher("Signup.html").include(req, resp);
		}
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String emph = req.getParameter("email");
		String password = req.getParameter("password");
		List<User> list = null;
		try {
			long mobile = Long.parseLong(emph);
			list = dao.findByMobile(mobile);
			if (list.isEmpty()) {
				resp.getWriter().print("<h1 style='color:red'>Incorrect Mobile number!!!</h1>");
				req.getRequestDispatcher("Login.html").include(req, resp);
			}
		} catch (NumberFormatException e) {
			String email = emph;
			list = dao.findByEmail(email);
			if (list.isEmpty()) {
				resp.getWriter().print("<h1 style='color:red'>Incorrect Email!!!</h1>");
			}
			if (!list.isEmpty()) {
				User user = list.get(0); // to get userid which user is login
				if (list.get(0).getPassword().equals(password)) {
					req.getSession().setAttribute("user", list.get(0));
					resp.getWriter().print("<h1 style='color:green' align='center'>Login Successfull</h1>");

					List<Task> tasks = dao.fetchTasksByUserId(user.getId()); // to get userid which user is login to
																				// display tasks carrying tasks added by
																				// user
					req.setAttribute("tasks", tasks);

					req.getRequestDispatcher("home.jsp").include(req, resp);
				} else {
					resp.getWriter().print("<h1 style='color:red'>Incorrect Password!!!</h1>");
					req.getRequestDispatcher("Login.html").include(req, resp);
				}
			} else {
				req.getRequestDispatcher("Login.html").include(req, resp);
			}
		}
	}

	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("user");
		resp.getWriter().print("<h1 align='center' style='color:red'>Logout Success!</h1>");
		req.getRequestDispatcher("Login.html").include(req, resp);

	}

	public void addTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		Part image = req.getPart("image");

		Task task = new Task();
		task.setName(name);
		task.setDescription(description);
		task.setStatus(false);
		task.setAddedTime(LocalDateTime.now()); // to set current time

		// convert from part to binary array for image
		byte[] pic = new byte[image.getInputStream().available()];
		image.getInputStream().read(pic);
		task.setImage(pic);

		User user = (User) req.getSession().getAttribute("user"); // to add session
		task.setUser(user);
		dao.saveTask(task);

		resp.getWriter().print("<h1 align='center' style='color:green'>Task Added</h1>");

		List<Task> tasks = dao.fetchTasksByUserId(user.getId()); // to get userid which user is login to display tasks
																	// carrying tasks added by user
		req.setAttribute("tasks", tasks);

		req.getRequestDispatcher("home.jsp").include(req, resp);

	}

	public void completeTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int id=Integer.parseInt(req.getParameter("id"));
		Task task =dao.findTaskById(id);
		task.setStatus(true);
		dao.updateTask(task);
	
		resp.getWriter().print("<h1 align='center' style='color:green'>Status Changed successfully</h1>");
		User user = (User) req.getSession().getAttribute("user"); // to add session

		List<Task> tasks = dao.fetchTasksByUserId(user.getId()); // to get userid which user is login to display tasks
																	// carrying tasks added by user
		req.setAttribute("tasks", tasks);

		req.getRequestDispatcher("home.jsp").include(req, resp);

	}

	public void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		Task task =dao.findTaskById(id);
	    dao.deletingTask(task);
	    
	    resp.getWriter().print("<h1 align='center' style='color:red'>Task Deleted Successfully</h1>");
		User user = (User) req.getSession().getAttribute("user"); // to add session

		List<Task> tasks = dao.fetchTasksByUserId(user.getId()); // to get userid which user is login to display tasks
																	// carrying tasks added by user
		req.setAttribute("tasks", tasks);

		req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}

	public void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		Part image = req.getPart("image");
		int id=Integer.parseInt(req.getParameter("id")); 
		
		Task task = new Task();
		task.setId(id);
		task.setName(name);
		task.setDescription(description);
		task.setStatus(false);
		task.setAddedTime(LocalDateTime.now()); // to set current time

		// convert from part to binary array for image
		byte[] pic = new byte[image.getInputStream().available()];
		image.getInputStream().read(pic);
		if(pic.length==0) {
			task.setImage(dao.findTaskById(id).getImage());
		}
		else
		task.setImage(pic);

		User user = (User) req.getSession().getAttribute("user"); // to add session
		task.setUser(user);
		//to save these details in task table
		dao.updateTask(task);

		resp.getWriter().print("<h1 align='center' style='color:green'>Task Updated</h1>");

		List<Task> tasks = dao.fetchTasksByUserId(user.getId()); // to get userid which user is login to display tasks
																	// carrying tasks added by user
		req.setAttribute("tasks", tasks);

		req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}

}
