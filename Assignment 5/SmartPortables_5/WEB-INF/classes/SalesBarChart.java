import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class SalesBarChart extends HttpServlet {
	 
	
	MySQLDataStoreUtilities ms = new MySQLDataStoreUtilities();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		Utilities utility = new Utilities(request, out);

		if (session.getAttribute("user") != null) {
			utility.printHtml("HeaderLogout.html");
			out.println("<section id=\"content\">");
			out.println("<h2 align=\"center\" margin-top=\"60px\">Sales BarChart</h2>");
			out.println("<div id=\"piechart_sales\" ></div>");
			out.println("</section>");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		} else {
			utility.printHtml("Header.html");
			utility.printHtml("Product.html");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		}

	}
}