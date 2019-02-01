
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

		if (htmlFile == "HeaderLogout.html") {

			if (session.getAttribute("username") != null) {
				String  username = (String) session.getAttribute("username");
				String usertype =(String) session.getAttribute("usertype");
				if(usertype!=null) {
					if(usertype.equals("Manager")) {
						result = result + "<li><a href=\"./DataVisualization\">Trending</a></li>";
						result = result + "<li><a href=\"./DataAnalytics\">Data Analytics</a></li>";
						result = result + "<li class=\"register\"><a href=\"./ViewOrder\">ViewOrder</a></li>";
						result = result + "<li class=\"login\"><a href=\"./LogoutServlet\">Hi "+username+", Logout</a></li>";
					}else if(usertype.equals("Customer")) {
						result = result + "<li><a href=\"./Trending\">Trending</a></li>";
						result =result + "<li class=\"cart\"><a href=\"ViewCart\">Cart("+CartCount()+")</a></li>";
						result = result + "<li class=\"register\"><a href=\"./ViewOrder\">ViewOrder</a></li>";
						result = result + "<li class=\"login\"><a href=\"./LogoutServlet\">Hi "+username+", Logout</a></li>";
					}else if(usertype.equals("Salesman")) {
						result = result + "<li class=\"register\"><a href=\"./ViewOrder\">ViewOrder</a></li>";
						result = result + "<li class=\"login\"><a href=\"./LogoutServlet\">Hi "+username+", Logout</a></li>";
					}
				}
			}
			result = result + "</ul>"
					+ "</nav><img src=\"Html/images/image.jpg\" alt=\"Buildings\" />";
			pw.print(result);
		}else {
			pw.print(result);
		}
		
		
	}
			
	public int CartCount(){
		if(isLoggedin() && session.getAttribute("cartitem")!=null) {
			HttpSession session = req.getSession();
			List<Cart> list = (ArrayList) session.getAttribute("cartitem");
			return list.size();
		}
		return 0;
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
			//pw.println(text);
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

	public int getOrderPaymentSize(String username,String usertype) {
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
		try {
			orderPayments = MySQLDataStoreUtilities.selectOrder(username, usertype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = 0;
		for (Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()) {
			size = size + 1;
		}
		return size;
	}

	public void storePayment(int orderId,String username,String usertype, String orderName, double orderPrice, String address,String zipCode,
			String creditCardNo,String DeliveryDate,int IsDelivered) {
		System.out.println("Inside storePayment");
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
		try {
			MySQLDataStoreUtilities.insertOrder(orderId, username,usertype, orderName, orderPrice, address, zipCode, creditCardNo, DeliveryDate,IsDelivered);	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	  public String storeReview(String productname,String producttype,String productmaker,String reviewrating,String reviewdate,String  reviewtext,String reatilerpin,String price,String city){
			String message=MongoDBDataStoreUtilities.insertReview(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city);
				if(!message.equals("Successfull"))
				{ return "UnSuccessfull";
				}
				else
				{
				HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
				try
				{
					reviews=MongoDBDataStoreUtilities.selectReview();
				}
				catch(Exception e)
				{
					
				}
				if(reviews==null)
				{
					reviews = new HashMap<String, ArrayList<Review>>();
				}
					
				if(!reviews.containsKey(productname)){	
					ArrayList<Review> arr = new ArrayList<Review>();
					reviews.put(productname, arr);
				}
				ArrayList<Review> listReview = reviews.get(productname);		
				Review review = new Review(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city);
				listReview.add(review);	
					
				
				return "Successfull";	
				}
			}
}