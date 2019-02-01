
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Utilities extends HttpServlet {

	HttpServletRequest req;
	PrintWriter pw;
	HttpSession session;
	String url;
	Users user;

	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.fetchURL();
		this.session = req.getSession(true);
	}

	public void printHtml(String htmlFile) {
		String result = HtmlToString(htmlFile);
		StringBuffer sb = new StringBuffer();
		sb.append("");

		if (result == "Header.Html") {

			if (session.getAttribute("username") != null) {
				Users user = (Users) session.getAttribute("username");
				sb.append(
						"<li class=\"register\"> <a href=\"./Registration\" >register</a></li><li class=\"login\"> <a href=\"LogoutServlet\" >logout</a></li><li class=\"login\">"
								+ user.getfirstname() + "</li>");
			} else {
				sb.append(
						"<li class=\"register\"> <a href=\"./Registration\" >register</a></li><li class=\"login\"> <a href=\"./Login\" >login</a></li>");
			}

		}
	}

	public String fetchURL() {
		String scheme = req.getScheme();
		String server = req.getServerName();
		int port = req.getServerPort();
		String path = req.getContextPath();
		StringBuffer sb = new StringBuffer();
		sb.append(scheme).append("://").append(server);
		if ((port != 80)) {
			sb.append(":").append(port);
		}
		sb.append(path).append("/Html/");
		return sb.toString();
	}

	public String HtmlToString(String htmlFile) {
		String text = null;
		String newPage = url + htmlFile;
		try {
			URL url = new URL(newPage);
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();
			InputStreamReader inr = new InputStreamReader(in);
			int readChars;
			char[] arr = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((readChars = inr.read(arr)) > 0) {
				sb.append(arr, 0, readChars);
			}

			text = sb.toString();
			pw.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	public boolean isLoggedin() {
		if (session.getAttribute("username") == null)
			return false;
		return true;
	}

	public String username() {
		if (session.getAttribute("username") != null)
			return session.getAttribute("username").toString();
		return null;
	}

	public ArrayList<OrderItem> getCustomerOrders() {
		ArrayList<OrderItem> order = new ArrayList<OrderItem>();
		if (OrdersHashMap.orders.containsKey(username()))
			order = OrdersHashMap.orders.get(username());
		return order;
	}

	public int getOrderPaymentSize() {
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(TOMCAT_HOME + "\\webapps\\SmartPortables\\PaymentDetails.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			orderPayments = (HashMap) objectInputStream.readObject();
		} catch (Exception e) {
		}
		int size = 0;
		for (Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()) {
			size = size + 1;
		}
		return size;
	}

	public void storePayment(int orderId, String orderName, double orderPrice, String userAddress,
			String creditCardNo) {
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(TOMCAT_HOME + "\\webapps\\SmartPortables\\PaymentDetails.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			orderPayments = (HashMap) objectInputStream.readObject();
		} catch (Exception e) {

		}
		if (orderPayments == null) {
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}

		if (!orderPayments.containsKey(orderId)) {
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
		OrderPayment orderpayment = new OrderPayment(orderId, username(), orderName, orderPrice, userAddress,
				creditCardNo);
		listOrderPayment.add(orderpayment);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(
					new File(TOMCAT_HOME + "\\webapps\\SmartPortables\\PaymentDetails.txt"));
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(orderPayments);
			objectOutputStream.flush();
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception e) {
			System.out.println("inside exception file not written properly");
		}
	}
}