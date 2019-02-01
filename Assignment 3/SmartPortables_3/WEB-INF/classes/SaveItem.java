import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveItem extends HttpServlet {
	public PrintWriter out;
	Utilities utility;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();
		Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables_3\\WEB-INF\\classes\\ProductCatalog.xml");
		HashMap<String, Product> hm2 = handler.getProducts();
		
		String id = request.getParameter("id");
		String category = request.getParameter("category");
		String prodName = request.getParameter("prodName");
		String prodPrice = request.getParameter("prodPrice");
		
		utility = new Utilities(request, out);
		if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");

		out.print("<div id='content'><div class='post'><h2 class='title meta'>");
		out.print("</h2><div class='entry'>");
		out.print("<h4 style='color:green'>Your prodct is updated successfully.");
		out.print("</h4></div></div></div>");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");
	}

}
