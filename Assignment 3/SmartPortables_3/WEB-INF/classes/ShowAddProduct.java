import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ShowAddProduct extends HttpServlet {
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
				session.setAttribute("login_msg", "Please Login to write a Review.");
				response.sendRedirect("Login");
				return;
			}
			
			

			if (utility.isLoggedin())
				utility.printHtml("HeaderLogout.html");
			else
				utility.printHtml("Header.html");
			pw.println("<section id=\"content\">");
			pw.print("<form name ='AddProductForm' action='AddProduct' method='post'>");
			pw.print("<div class='box'>");
			pw.print("<div class='box-header'>");
			pw.print("<h3 class='box-title'>Add New Product</h3>");
			pw.print("</div>");
			
			pw.print("<table  class='table'>");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Product Name: </b></label></td>");
			pw.print("<td> <input type='text' name='productname' class='uname' required='true'> </td>");
			pw.print("</tr>");
			
			pw.print("<tr><td><label><b> Product Category: </b></label></td>");
			pw.print("<td>");
			pw.print("<select name='producttype' class='uname'>");
			pw.print("<option value='SmartWatches' selected>SmartWatches</option>");
			pw.print("<option value='Speakers'>Speakers</option>");
			pw.print("<option value='Headphones'>Headphones</option>");
			pw.print("<option value='Phones'>Phones</option>");
			pw.print("<option value='Laptop'>Laptop</option>");
			pw.print("</td></tr>");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Product Condition: </b></label></td>");
			pw.print("<td> <input type='text' name='productcondition' class='uname' required='true' placeholder='New or Old'> </td>");
			pw.print("</tr>");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Product Price $: </b></label></td>");
			pw.print("<td> <input type='text' name='productprice' class='uname' required='true'> </td>");
			pw.print("</tr>");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Product Quantity : </b></label></td>");
			pw.print("<td> <input type='text' name='productquantity' class='uname' required='true'> </td>");
			pw.print("</tr>");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Product Manufacturer : </b></label></td>");
			pw.print("<td> <input type='text' name='productmanufacturer' class='uname' required='true'> </td>");
			pw.print("</tr>");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Product Image Path: </b></label></td>");
			pw.print("<td> <input type='text' name='productimage' class='uname' required='true' placeholder='Only Name'> </td>");
			pw.print("</tr>");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Product Onsale: </b></label></td>");
			pw.print("<td> <input type='text' name='productonsale' class='uname' required='true' placeholder='Yes or No'> </td>");
			pw.print("</tr>");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Rebate on Product: </b></label></td>");
			pw.print("<td> <input type='text' name='productrebate' class='uname' required='true' placeholder='Yes or No'> </td>");
			pw.print("</tr>");

			pw.print(
					"<tr><td colspan='2'><input type='submit' class='lbutton' name='AddProduct' value='AddProduct'></td></tr></table>");
			

			pw.print("</div></form>");
			pw.println("</section>");
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