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
import javax.servlet.http.HttpSession;

public class Registration extends HttpServlet {
	private String error_msg;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		Utilities utility = new Utilities(request, out);
		displayRegistration(request, response, out, true);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String repassword = request.getParameter("repassword");
		
		if(!utility.isLoggedin())
			usertype = request.getParameter("usertype");

		if(!password.equals(repassword))
		{
			error_msg = "Passwords doesn't match!";
		}
		else
		{
			HashMap<String, Users> hm=new HashMap<String, Users>();
			String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
 			 FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\SmartPortables\\UserDetails.txt"));
			 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
			 hm= (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			}

			if(hm.containsKey(username))
				error_msg = "Username already exist as " + usertype;
			else
			{
				Users user = new Users(username,password,usertype,firstname,lastname);
				hm.put(username, user);
			    FileOutputStream fileOutputStream = new FileOutputStream(TOMCAT_HOME+"\\webapps\\SmartPortables\\UserDetails.txt");
        		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
           	 	objectOutputStream.writeObject(hm);
				objectOutputStream.flush();
				objectOutputStream.close();       
				fileOutputStream.close();
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Your "+usertype+" account has been created. Please login");
				if(!utility.isLoggedin()){
					response.sendRedirect("Login"); return;
				} else {
					response.sendRedirect("Home"); return;
				}
			}
		}

		if(utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", error_msg);
			response.sendRedirect("Home"); return;
		}
		displayRegistration(request, response, pw, true);
		
	}
	protected void displayRegistration(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {
		
		Utilities utility = new Utilities(request, pw);
		if(utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");
		utility.printHtml("Registration.html");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");
		
	}
	
}