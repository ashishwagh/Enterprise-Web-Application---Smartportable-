import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Home extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession();
		Utilities utility = new Utilities(request, pw);

		if (session.getAttribute("user") != null) {
			utility.printHtml("HeaderLogout.html");
			//utility.printHtml("Product.html");
			RequestDispatcher rd=request.getRequestDispatcher("DealMatchesUtilities");
			rd.include(request,response);
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		} else {
			utility.printHtml("Header.html");
			//utility.printHtml("Product.html");
			RequestDispatcher rd=request.getRequestDispatcher("DealMatchesUtilities");
			rd.include(request,response);
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		}
	}

}