import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Payment")
public class Payment extends HttpServlet {
	Utilities utility;
	PrintWriter pw;
	List<Cart> arraylist;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("HeaderLogout.html");
		pw.println("<section id=\"content\">");
		pw.println("<h2 style=\"color:green\"> Your Order is Placed successfully. </h2>");
		pw.println("<h2 style=\"color:red\"> Your order num # is A44.  </h2>");
		pw.println("</section>");

		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		if (!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to Pay");
			response.sendRedirect("Login");
			return;
		}

		String creditCardName = request.getParameter("creditCardName");
		String creditCardNo = request.getParameter("creditCardNo");
		HttpSession session = request.getSession();
		List<Cart> list = (ArrayList) session.getAttribute("cartitem");
		if (!creditCardName.isEmpty() && !creditCardNo.isEmpty()) {
			int orderId = utility.getOrderPaymentSize() + 1;
			for (Cart cart : list) {
				utility.storePayment(orderId, cart.getName(), Double.parseDouble(cart.getPrice()), creditCardName,
						creditCardNo);
			}

			list.clear();
			session.setAttribute("cartitem", list);
			if (utility.isLoggedin())
				utility.printHtml("HeaderLogout.html");
			else
				utility.printHtml("Header.html");

			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order Status</a>");
			pw.print("</h2><div class='entry'>");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(date); 
			c.add(Calendar.DATE, 14);
			date = c.getTime();
			pw.print("<h4 style='color:green'>Your Order is placed successfully.Your Order No is "+ (orderId)+"."
					+ "Your order will be delivered on "+ simpleDateFormat.format(date));
			pw.print("</h4></div></div></div>");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		} else {
			if (utility.isLoggedin())
				utility.printHtml("HeaderLogout.html");
			else
				utility.printHtml("Header.html");

			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");

			pw.print("<h4 style='color:red'>Please enter valid creditcard name and creditcard number</h4>");
			pw.print("</h2></div></div></div>");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		}
	}

}