import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginParser extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		
		System.out.println("usertype"+usertype);
		
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("usertype", usertype);

		HashMap<String, Users> hm = new HashMap<String, Users>();
		Utilities utility = new Utilities(request, pw);
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables\\UserDetails.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			hm = (HashMap) objectInputStream.readObject();
		} catch (Exception ex) {
			ex.getMessage();
		}
		if (hm.containsKey(username)) {
			Users user = (Users) hm.get(username);
			if (!user.getpassword().equals(password)) {
				System.out.println("user.getpassword()" + user.getpassword());
				System.out.println("password" + password);
				utility.printHtml("Header.html");
				pw.println("<section id=\"content\">");
				pw.println("<h2 style=\"color:red\"> Login Failed. Please enter correct password.</h2>");
				pw.println(
						"<h2 style=\"color:blue\"> Click Here for Login : <a href=\"Login\" color=\"red\">Login</a> </h2>");
				pw.println("</section>");
				utility.printHtml("LeftNav.html");
				utility.printHtml("Footer.html");
			} else if (!user.getUsertype().equals(usertype)) {
				System.out.println("user.getusertype()" + user.getUsertype());
				System.out.println("usertype" + usertype);
				utility.printHtml("Header.html");
				pw.println("<section id=\"content\">");
				pw.println("<h2 style=\"color:red\"> Login Failed. Please select correct usertype.</h2>");
				pw.println(
						"<h2 style=\"color:blue\"> Click Here for Login : <a href=\"Login\" color=\"red\">Login</a> </h2>");
				pw.println("</section>");

				utility.printHtml("LeftNav.html");
				utility.printHtml("Footer.html");

			} else if(user.getUsertype().equals("Manager")) {
				System.out.println("INside request.getRequestDispatcher(\"/ManagerLogin\")");
				session.setAttribute("user", user);
				request.getRequestDispatcher("/ManagerLogin").forward(request, response);
				
			}else if(user.getUsertype().equals("Salesman")){
				System.out.println("INside equest.getRequestDispatcher(\"/ViewOrder\").forward(request, response);");
				session.setAttribute("user", user);
				request.getRequestDispatcher("/ViewOrder").forward(request, response);
			}else {
				System.out.println("request.getRequestDispatcher(\"/Home\").forward(request, response);");
				session.setAttribute("user", user);
				request.getRequestDispatcher("/Home").forward(request, response);
			}
		}

	}

}