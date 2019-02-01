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
		String address = request.getParameter("address");
		String zipCode = request.getParameter("zipCode");
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		String usertype = session.getAttribute("usertype").toString();
		List<Cart> list = (ArrayList) session.getAttribute("cartitem");
		List<Cart> tempList = new ArrayList<Cart>(list);
		String message=MySQLDataStoreUtilities.getConnection();
		if(message.equals("successfull")) {
			if (!creditCardName.isEmpty() && !creditCardNo.isEmpty()) {
				Date date = new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(date); 
				c.add(Calendar.DATE, 14);
				date = c.getTime();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				int orderId=0;
				int tempOrderId=0;
				orderId = utility.getOrderPaymentSize(username,usertype) + 1;
				tempOrderId=orderId;
				int IsDelivered=0;
				for (Cart cart : list) {
					utility.storePayment(orderId,username,usertype, cart.getName(), Double.parseDouble(cart.getPrice()), address,zipCode,
							creditCardNo,simpleDateFormat.format(date),IsDelivered,simpleDateFormat.format(new Date()));
					orderId++;
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
				
				
				pw.print("<h4 style='color:green'>Your Order is placed successfully."
						+ "Your order will be delivered on "+ simpleDateFormat.format(date));
				
				for (Cart cart : tempList) {
					pw.print("<h4 style='color:green'>Your Order No for "+cart.getName()+" is "+tempOrderId+".</h4>");
					tempOrderId++;
				}
				
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
		}else {
			if (utility.isLoggedin())
				utility.printHtml("HeaderLogout.html");
			else
				utility.printHtml("Header.html");

			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");

			pw.print("<h4 style='color:red'>MySql server is not up and running</h4>");
			pw.print("</h2></div></div></div>");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		}
	}

}