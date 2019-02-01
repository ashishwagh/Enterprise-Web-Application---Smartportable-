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


public class WriteReview extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		review(request, response);
	}

	protected void review(HttpServletRequest request, HttpServletResponse response)
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
			String id = request.getParameter("id");
			String productname = request.getParameter("name");
			String producttype = request.getParameter("catogory");
			String productprice = request.getParameter("price");
			String productmaker = request.getParameter("manufacturer");
			String image = request.getParameter("image");
			

			// on filling the form and clicking submit button user will be directed to
			// submit review page
			if (utility.isLoggedin())
				utility.printHtml("HeaderLogout.html");
			else
				utility.printHtml("Header.html");
			pw.println("<section id=\"content\">");
			pw.print("<form name ='WriteReview' action='SubmitReview' method='post'>");
			pw.print("<div class='box'>");
			pw.print("<div class='box-header'>");
			pw.print("<h3 class='box-title'>Review</h3>");
			pw.print("</div>");
			
			
			pw.print("<table  class='table'>");
			pw.print("<tr><td><label><b> Product Name: </b></label></td><td>");
			pw.print(productname);
			pw.print("<input type='hidden' name='productname' value='" + productname + "'>");
			pw.print("</td></tr>");
			pw.print("<tr><td><label><b> Product Type: </b></label></td><td>");
			pw.print(producttype);
			pw.print("<input type='hidden' name='producttype' value='" + producttype + "'>");
			pw.print("</td></tr>");
			pw.print("<tr><td><label><b> Product Price: </b></label></td><td>");
			pw.print(productprice);
			pw.print("<input type='hidden' name='productprice' value='" + productprice + "'>");
			pw.print("</td></tr>");
			pw.print("<tr><td><label><b> Product Maker: </b></label></td><td>");
			pw.print(productmaker);
			pw.print("<input type='hidden' name='productmaker' value='" + productmaker + "'>");
			pw.print("</td></tr>");

			pw.print("<tr><td><label><b> Review Rating: </b></label></td>");
			pw.print("<td>");
			pw.print("<select name='reviewrating' class='uname'>");
			pw.print("<option value='1' selected>1</option>");
			pw.print("<option value='2'>2</option>");
			pw.print("<option value='3'>3</option>");
			pw.print("<option value='4'>4</option>");
			pw.print("<option value='5'>5</option>");
			pw.print("</td></tr>");

			pw.print("<tr>");
			pw.print("<td><label><b> Retailer Zip Code: </b></label></td>");
			pw.print("<td> <input type='text' name='zipcode' class='uname' required='true'> </td>");
			pw.print("</tr>");

			pw.print("<tr>");
			pw.print("<td><label><b> Retailer City: </b></label></td>");
			pw.print("<td> <input type='text' name='retailercity' class='uname' required='true'> </td>");
			pw.print("</tr>");

			Date date = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(date); 
			date = c.getTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
			
			pw.print("<tr>");
			pw.print("<td><label><b> Review Date: </b></label></td>");
			pw.print("<td> <input type='date' name='reviewdate' min='01-01-1899' max='"+simpleDateFormat.format(date) +" ' class='uname' required='true'> </td>");
			pw.print("</tr>");

			pw.print("<tr>");
			pw.print("<td><label><b> Review Text: </b></label></td>");
			pw.print("<td><textarea name='reviewtext' rows='4' cols='50' class='uname' required='true'> </textarea></td></tr></table>");
			pw.print(
					"<tr><td colspan='2'><input type='submit' class='lbutton' name='SubmitReview' value='SubmitReview'></td></tr></table>");
			

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
		review(request, response);

	}
}
