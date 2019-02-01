import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class InventoryBarChart extends HttpServlet {
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
			out.println("<div id=\"piechart\" style=\"width: 6500px; height: 1700px;\" ></div>");
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