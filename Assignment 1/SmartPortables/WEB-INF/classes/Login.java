import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Utilities utility = new Utilities(request, pw);
		System.out.println("Inside Login doGEt");
		utility.printHtml("Header.html");
		if(session.getAttribute("login_msg")!=null) {
			pw.println("<section id=\"content\">");
			pw.println("<h3 style=\"color:red\">"+session.getAttribute("login_msg").toString()+ "</h3>");
			pw.println("</section>");
			session.removeAttribute("login_msg");
		}
		utility.printHtml("Login.html");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");
	}

}