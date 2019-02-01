import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewCart extends HttpServlet {
	Utilities utility;
	PrintWriter out;
	List<Cart> arraylist;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();

		String username = request.getParameter("username");
		Double totalPrice = new Double(0.00);
		Utilities utility = new Utilities(request, out);

		HttpSession session = request.getSession();
		arraylist = (ArrayList<Cart>) session.getAttribute("cartitem");
		String check = request.getParameter("input");
		if (arraylist != null)

		{

			if (utility.isLoggedin())
				utility.printHtml("HeaderLogout.html");
			else
				utility.printHtml("Header.html");
			out.println("<section id=\"content\">");
			out.println("<table cellspacing=\"0\" class=\"shopping-cart\">");
			out.println("<thead>");
			out.println("<tr class=\"headings\">");
			out.println("<th class=\"link\">&nbsp;</th>");
			out.println("<th class=\"link\">&nbsp;</th>   ");
			out.println("</tr>");
			out.println("</thead>");
			out.println("<tbody>");
			out.println("<tr>");
			for (Cart cart : arraylist) {
				out.println("<article>");
				out.println("<a  href=\"#\">");
				out.println("<img src=\"Html\\images\\" + cart.getImage()
						+ "\" alt=\"Trolltunga Norway\" width=\"300\" height=\"200\">");
				out.println("</a>");
				out.println("<ul>");
				out.println("<li class=\"desc\">Name : " + cart.getName() + "</li>");
				out.println("<li>Price: $" + cart.getPrice() + "</li>");
				out.println("<li>Quantity=1 </li>");
				out.println("</ul>");
				out.println("</article>");
				//System.out.println("cart.getPrice()" + cart.getPrice());
				if (cart.getPrice() != null)
					totalPrice = totalPrice + Double.parseDouble(cart.getPrice());
			}

			out.println("<form action='Payment' method='post' id='paymentFormId'>");
			out.println("<div class=\"container username\">");
			out.println("<div class=\"username\">");
			out.println("<label><b>Total Price</b> :$   " + totalPrice + "</label>");
			out.println("</div>");
			out.println("<div class=\"username\">");
			out.println("<label><b>Card Holder Name</b></label>");
			out.println("<input type=\"text\" class=\"uname\"  name=\"creditCardName\" required=\"true\">");
			out.println("</div>");
			out.println("<div class=\"username\">");
			out.println("<label><b>Card Number</b></label>");
			out.println(
					"<input type=\"text\" class=\"uname\"  name=\"creditCardNo\" required=\"true\" style=\"margin-left:48px !important\">");
			out.println("</div>");

			out.println("<div class=\"username\">");
			out.println("<label><b>Address</b></label>");
			out.println("<input type=\"text\" class=\"uname\"  name=\"address\" required=\"true\" style=\"margin-left:80px !important\">");
			out.println("</div>");
			
			out.println("<div class=\"username\">");
			out.println("<label><b>Zip Code</b></label>");
			out.println("<input type=\"text\" class=\"uname\"  name=\"zipCode\" required=\"true\" style=\"margin-left:80px !important\"> ");
			out.println("</div>");
			
			out.println(
					"<button type=\"submit\" formId=\"paymentFormId\" class=\"lbutton\" value=\"Submit\">Checkout</a></button>");
			out.println(
					"<button type=\"submit\" formId=\"paymentFormId\" class=\"lbutton\" value=\"Submit\">Remove</a></button>");
			out.println("</div>");
			out.println("</div>");
			out.println("</form>");
			out.println("</tr>");
			out.println("</tbody>");
			out.println("</table>");

			out.println("</section>");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		} else {
			if (utility.isLoggedin())
				utility.printHtml("HeaderLogout.html");
			else
				utility.printHtml("Header.html");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		}

	}

}