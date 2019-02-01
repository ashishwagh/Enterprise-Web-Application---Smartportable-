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
				hm=MySQLDataStoreUtilities.selectUser();
	
		} catch (Exception ex) {
			ex.getMessage();
		}
		System.out.println("username+usertype "+username+usertype);
		if (hm.containsKey(username+usertype)) {
			Users user = (Users) hm.get(username+usertype);
			
			if(user.getpassword().equals(password) && user.getUsertype().equals(usertype)) {
				switch(usertype) {
				case "Customer":
					System.out.println("request.getRequestDispatcher(\"/Home\").forward(request, response);");
					session.setAttribute("user", user);
					request.getRequestDispatcher("/Home").forward(request, response);
					break;
				case "Manager":
					System.out.println("INside request.getRequestDispatcher(\"/ManagerLogin\")");
					session.setAttribute("user", user);
					request.getRequestDispatcher("/ManagerLogin").forward(request, response);
					break;
				case "Salesman":
					System.out.println("INside equest.getRequestDispatcher(\"/ViewOrder\").forward(request, response);");
					session.setAttribute("user", user);
					request.getRequestDispatcher("/ViewOrder").forward(request, response);
					break;
				default:
					System.out.println("No case found.");
				}
			}else if (!user.getpassword().equals(password)) {
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
			} 
		}else {
			utility.printHtml("Header.html");
			pw.println("<section id=\"content\">");
			pw.println("<h2 style=\"color:red\"> User does not exist or You have selected wrong Usertype.</h2>");
			pw.println(
					"<h2 style=\"color:blue\"> Please do Registartion<a href=\"Registration\" style=\"color:green\"> here.</a> </h2>");
			pw.println("</section>");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		}

	}

}