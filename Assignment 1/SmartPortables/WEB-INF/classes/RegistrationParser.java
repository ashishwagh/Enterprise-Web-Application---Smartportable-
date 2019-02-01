import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationParser extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		HashMap<String, Users> hm = new HashMap<String, Users>();

		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables\\UserDetails.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			hm = (HashMap) objectInputStream.readObject();
		} catch (Exception ex) {
			ex.getMessage();
		}
		if (hm.containsKey(username) && hm.containsKey(usertype)) {
			request.setAttribute("Er", "Username Already exists");
			request.getRequestDispatcher("/Registration").forward(request, response);

		} else
		{
			Users user = new Users(username, password, usertype, firstname, lastname);
			hm.put(username, user);

			FileOutputStream fileOutputStream = new FileOutputStream(
					"C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables\\UserDetails.txt");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(hm);
			objectOutputStream.flush();
			objectOutputStream.close();
			fileOutputStream.close();

			request.getRequestDispatcher("/Login").forward(request, response);
		}

	}

}