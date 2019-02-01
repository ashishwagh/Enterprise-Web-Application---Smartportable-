import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowUpdateProduct extends HttpServlet  {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		addProductForm(request, response);
	}

	protected void addProductForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			if (!utility.isLoggedin()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("login_msg", "Please Login to Update Product.");
				response.sendRedirect("Login");
				return;
			}
			
			

			if (utility.isLoggedin())
				utility.printHtml("HeaderLogout.html");
			else
				utility.printHtml("Header.html");
			pw.println("<section id=\"content\">");
			pw.print("<form name ='UpdateShowProductForm' action='UpdateProduct' method='get'>");
			pw.print("<div class='box'>");
			pw.print("<div class='box-header'>");
			pw.print("<h3 class='box-title'>Update Details</h3>");
			pw.print("</div>");

			if (request.getParameter("Order") == null) {
				pw.print(
						"<table align='center' class='table'><tr><td><label><b>Enter Product Id:</b></label>&nbsp&nbsp<input name='id' type='text'"
								+ "class='uname'></td>");
				pw.print(
						"<td><input type='submit' name='UpdateShowProductForm' value='UpdateProduct' class='lbutton' style='margin-top: 12px;'></td></tr></table>");
			}
			pw.print("</form>");
			pw.print("</section>");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		addProductForm(request, response);

	}
}
