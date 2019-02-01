import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ViewOrder")

public class ViewOrder extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		if (!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}
		HttpSession session = request.getSession();
		String usertype = (String) session.getAttribute("usertype");
		String username = utility.username();
		if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");
		pw.println("<section id=\"content\">");
		pw.print("<form name ='ViewOrder' action='ViewOrder' method='get'>");
		pw.print("<div class='box'>");
		pw.print("<div class='box-header'>");
		pw.print("<h3 class='box-title'>Order Details</h3>");
		pw.print("</div>");

		if (request.getParameter("Order") == null) {
			pw.print(
					"<table align='center' class='table'><tr><td><label><b>Enter OrderNo</b></label>&nbsp&nbsp<input name='orderId' type='text'"
							+ "class='uname'></td>");
			pw.print(
					"<td><input type='submit' name='Order' value='ViewOrder' class='lbutton' style='margin-top: 12px;'></td></tr></table>");
		}

		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");

		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(TOMCAT_HOME + "\\webapps\\SmartPortables\\PaymentDetails.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			orderPayments = (HashMap) objectInputStream.readObject();
		} catch (Exception e) {
		}

		if (request.getParameter("Order") != null && request.getParameter("Order").equals("ViewOrder")) {
			if (request.getParameter("orderId") != null && request.getParameter("orderId") != "") {
				int orderId = Integer.parseInt(request.getParameter("orderId"));
				pw.print("<input type='hidden' name='orderId' value='" + orderId + "'>");
				try {
					FileInputStream fileInputStream = new FileInputStream(
							new File(TOMCAT_HOME + "\\webapps\\SmartPortables\\PaymentDetails.txt"));
					ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
					orderPayments = (HashMap) objectInputStream.readObject();
				} catch (Exception e) {

				}
				int size = 0;
				if(!usertype.equals("Salesman") && !usertype.equals("Manager")) {
						if (orderPayments.get(orderId) != null) {
							for (OrderPayment od : orderPayments.get(orderId))
								if (od.getUserName().equals(username))
									size = orderPayments.get(orderId).size();
						}
						if (size > 0) {
							pw.print("<table  class='table'>");
							pw.print("<th></th>");
							pw.print("<th><label><b>OrderId</b></label></th>");
							pw.print("<th><label><b>UserName</b></label></th>");
							pw.print("<th><label><b>Product Ordered</b></label></th>");
							pw.print("<th><label><b>Product Price</b></label></th><th></th><th></th>");
							for (OrderPayment oi : orderPayments.get(orderId)) {
								pw.print("<tr>");
								pw.print("<td><input class='radio' type='radio' name='orderName' value='" + oi.getOrderName()
										+ "'></td>");
								pw.print("<td><label><b>" + oi.getOrderId() + "</b></label></td>" + "<td><label><b>"
										+ oi.getUserName() + "</b></label></td>" + "<td><label><b>" + oi.getOrderName()
										+ "</b></label></td>" + "<td><label><b>Price: " + oi.getOrderPrice()
										+ "</b></label></td>");
								pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='lbutton'></td>");
								pw.print("</tr>");
		
							}
							pw.print("</table>");
						} else {
							pw.print("<h2 style='color:red'>You have not placed any order with this order id.</h2>");
						}
				}else {
					if (orderPayments.get(orderId) != null) {
							size = size + orderPayments.get(orderId).size();
					}
					if (size > 0) {
						pw.print("<table  class='table'>");
						pw.print("<th></th>");
						pw.print("<th><label><b>OrderId</b></label></th>");
						pw.print("<th><label><b>UserName</b></label></th>");
						pw.print("<th><label><b>Product Ordered</b></label></th>");
						pw.print("<th><label><b>Product Price</b></label></th><th></th><th></th>");
						for (OrderPayment oi : orderPayments.get(orderId)) {
							pw.print("<tr>");
							pw.print("<td><input class='radio' type='radio' name='orderName' value='" + oi.getOrderName()
									+ "'></td>");
							pw.print("<td><label><b>" + oi.getOrderId() + "</b></label></td>" + "<td><label><b>"
									+ oi.getUserName() + "</b></label></td>" + "<td><label><b>" + oi.getOrderName()
									+ "</b></label></td>" + "<td><label><b>Price: " + oi.getOrderPrice()
									+ "</b></label></td>");
							pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='lbutton'></td>");
							pw.print("</tr>");
	
						}
						pw.print("</table>");
					} else {
						pw.print("<h2 style='color:red'>No orders for this order id.</h2>");
					}
				}
			} else

			{
				pw.print("<h2 style='color:red'>Please enter the valid order number.</h2>");
			}
		}
		if (request.getParameter("Order") != null && request.getParameter("Order").equals("CancelOrder")) {
			if (request.getParameter("orderName") != null) {
				String orderName = request.getParameter("orderName");
				int orderId = 0;
				orderId = Integer.parseInt(request.getParameter("orderId"));
				ArrayList<OrderPayment> ListOrderPayment = new ArrayList<OrderPayment>();
				try {

					FileInputStream fileInputStream = new FileInputStream(
							new File(TOMCAT_HOME + "\\webapps\\SmartPortables\\PaymentDetails.txt"));
					ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
					orderPayments = (HashMap) objectInputStream.readObject();
				} catch (Exception e) {

				}
				for (OrderPayment oi : orderPayments.get(orderId)) {
					if(!usertype.equals("Manager") && !usertype.equals("Salesman")) {
						if (oi.getOrderName().equals(orderName) && oi.getUserName().equals(username)) {
							ListOrderPayment.add(oi);
							pw.print("<h2 style='color:green'>Your Order is Cancelled.</h2>");
						}
					}else {
						ListOrderPayment.add(oi);
					}
				}
				if(usertype.equals("Manager")|| usertype.equals("Salesman"))
					pw.print("<h2 style='color:green'>Your Order is Cancelled.</h2>");
				orderPayments.get(orderId).removeAll(ListOrderPayment);
				if (orderPayments.get(orderId).size() == 0) {
					orderPayments.remove(orderId);
				}
				try {
					FileOutputStream fileOutputStream = new FileOutputStream(
							new File(TOMCAT_HOME + "\\webapps\\SmartPortables\\PaymentDetails.txt"));
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
					objectOutputStream.writeObject(orderPayments);
					objectOutputStream.flush();
					objectOutputStream.close();
					fileOutputStream.close();
				} catch (Exception e) {

				}
			} else {
				pw.print("<h2 style='color:red'>Please select any product</h2>");
			}
		}
		pw.print("</form>");
		pw.print("</section>");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");
	}

}
