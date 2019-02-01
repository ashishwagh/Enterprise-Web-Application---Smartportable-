
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
						result = result + "<li><a href=\"./ShowAddProduct\">Add Product</a></li>";
						result = result + "<li><a href=\"Inventory\">Inventory</a></li>";
						result = result + "<li><a href=\"Sales\">Sales Report</a></li>";
						result = result + "<li><a href=\"./DataVisualization\">Trending</a></li>";
						result = result + "<li><a href=\"./DataAnalytics\">Data Analytics</a></li>";
						result = result + "<li class=\"register\"><a href=\"./ViewOrder\">ViewOrder</a></li>";
						result = result + "<li class=\"login\"><a href=\"./LogoutServlet\">Hi "+username+", Logout</a></li>";
					}else if(usertype.equals("Customer")) {
						
						result = result + "<li class=\"\"><a href=\"Myhandler?category=SmartWatches\">Wearable Technology</a></li>";
						result = result + "<li><a href=\"Myhandler?category=Speakers\">Voice Assistant/Smart Speaker</a></li>";
						result = result + "<li><a href=\"Myhandler?category=Headphones\">Headphones</a></li>";
						result = result + "<li><a href=\"Myhandler?category=Phones\">Phones</a></li>";
						result = result + "<li class=\"end\"><a href=\"Myhandler?category=Laptop\">Laptops</a></li>";
						
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
		}else if(htmlFile == "LeftNav.html"){
			if (session.getAttribute("username") != null) {
				String usertype =(String) session.getAttribute("usertype");
				if(usertype!=null) {
					if(usertype.equals("Manager")) {
						result = result + "<li>";
						result = result + "<h4>Inventory</h4>";
						result = result + "<ul>";
						result = result + "<li id=\"first\"><a href=\"Inventory\">Product Inventory</a></li>";
						result = result + "<li><a href=\"InventoryBarChart\">Inventory Chart</a></li>";
						result = result + "<li><a href=\"InventoryOnSale\">Product On Sale</a></li>";
						result = result + "<li><a href=\"InventoryRebate\">Product With Rebate</a></li>";
						result = result + "</ul>";
						result = result + "</li>";
						
						result = result + "<li>";
						result = result + "<h4>Sales Report</h4>";
						result = result + "<ul>";
						result = result + "<li id=\"first\"><a href=\"Sales\">Product Sale</a></li>";
						result = result + "<li><a href=\"SalesBarChart\">Sales Chart</a></li>";
						result = result + "<li><a href=\"SalesDailyReport\">Total Daily Sale</a></li>";
						result = result + "</ul>";
						result = result + "</li>";
					}else{
					
						
						result = result + "<li>";
						result = result + "<h4>Wearable Technology</h4>";
						result = result + "<ul>";
						result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=SmartWatches&maker=LG\">Fitness Watches LG</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=SmartWatches&maker=Samsung\">Samsung Smart Watches</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=SmartWatches&maker=Apple\">Apple Smart/Fitness Watches</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=SmartWatches&maker=VR\">Virtual Reality</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=SmartWatches&maker=PETTRACKER\">Pet Tracker</a></li>";
						result = result + "</ul>";
						result = result + "</li>";
												
						result = result + "<li>";
						result = result + "<h4>Voice Assistant/Smart Speaker</h4>";
						result = result + "<ul>";
						result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=Speakers&maker=JBL\">JBL Speaker</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=Speakers&maker=Sony\">Sony Speaker</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=Speakers&maker=Beats\">Beats Speaker</a></li>";
						result = result + "</ul>";
						result = result + "</li>";
										
						result = result + "<li>";
						result = result + "<h4>HEADPHONES</h4>";
						result = result + "<ul>";
						result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=Headphones&maker=Sennheiser\">Sennheiser</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=Headphones&maker=SkullCandy\">SkullCandy</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=Headphones&maker=Sony\">Sony</a></li>";
						result = result + "</ul>";
						result = result + "</li>";
						
										
						result = result + "<li>";
						result = result + "<h4>PHONES</h4>";
						result = result + "<ul>";
						result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=Phones&maker=Apple\">Apple</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=Phones&maker=OnePlus\">One Plus</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=Phones&maker=Samsung\">Samsung</a></li>";
						result = result + "</ul>";
						result = result + "</li>";
						
						
						result = result + "<li>";
						result = result + "<h4>LAPTOPS</h4>";
						result = result + "<ul>";
						result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=Laptop&maker=Dell\">Dell</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=Laptop&maker=HP\">HP</a></li>";
						result = result + "<li><a href=\"ProductListHandler?category=Laptop&maker=Apple\">Apple</a></li>";
						result = result + "</ul>";
						result = result + "</li>";
					}
				}
				result = result + "<li>";
				result = result + "<h4>Search</h4>";
				result = result + "<ul>";
				result = result + "<li class=\"text\">";
				result = result + "<form method=\"get\" class=\"searchform\" action=\"#\">";
				result = result + "<p><input type=\"text\" size=\"25\" value=\"\" name=\"s\" class=\"s\" /></p>";
				result = result + "</form></li>";
				result = result + "</ul>";
				result = result + "</li>";
				
				
				result = result + "</ul></li></ul></aside>";
				result = result + "<div class=\"clear\"></div></div>";
				pw.print(result);
			}else {
			result = result + "<li>";
			result = result + "<h4>Wearable Technology</h4>";
			result = result + "<ul>";
			result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=SmartWatches&maker=LG\">Fitness Watches LG</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=SmartWatches&maker=Samsung\">Samsung Smart Watches</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=SmartWatches&maker=Apple\">Apple Smart/Fitness Watches</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=SmartWatches&maker=VR\">Virtual Reality</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=SmartWatches&maker=PETTRACKER\">Pet Tracker</a></li>";
			result = result + "</ul>";
			result = result + "</li>";
									
			result = result + "<li>";
			result = result + "<h4>Voice Assistant/Smart Speaker</h4>";
			result = result + "<ul>";
			result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=Speakers&maker=JBL\">JBL Speaker</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=Speakers&maker=Sony\">Sony Speaker</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=Speakers&maker=Beats\">Beats Speaker</a></li>";
			result = result + "</ul>";
			result = result + "</li>";
							
			result = result + "<li>";
			result = result + "<h4>HEADPHONES</h4>";
			result = result + "<ul>";
			result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=Headphones&maker=Sennheiser\">Sennheiser</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=Headphones&maker=SkullCandy\">SkullCandy</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=Headphones&maker=Sony\">Sony</a></li>";
			result = result + "</ul>";
			result = result + "</li>";
			
							
			result = result + "<li>";
			result = result + "<h4>PHONES</h4>";
			result = result + "<ul>";
			result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=Phones&maker=Apple\">Apple</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=Phones&maker=OnePlus\">One Plus</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=Phones&maker=Samsung\">Samsung</a></li>";
			result = result + "</ul>";
			result = result + "</li>";
			
			
			result = result + "<li>";
			result = result + "<h4>LAPTOPS</h4>";
			result = result + "<ul>";
			result = result + "<li id=\"first\"><a href=\"ProductListHandler?category=Laptop&maker=Dell\">Dell</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=Laptop&maker=HP\">HP</a></li>";
			result = result + "<li><a href=\"ProductListHandler?category=Laptop&maker=Apple\">Apple</a></li>";
			result = result + "</ul>";
			result = result + "</li>";
			
			result = result + "<li>";
			result = result + "<h4>Search</h4>";
			result = result + "<ul>";
			result = result + "<li class=\"text\">";
			result = result + "<form method=\"get\" class=\"searchform\" action=\"#\">";
			result = result + "<p><input type=\"text\" size=\"25\" value=\"\" name=\"s\" class=\"s\" /></p>";
			result = result + "</form></li>";
			result = result + "</ul>";
			result = result + "</li>";
			
			
			result = result + "</ul></li></ul></aside>";
			result = result + "<div class=\"clear\"></div></div>";
			pw.print(result);
		}
	}else
		pw.print(result);
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
			String creditCardNo,String DeliveryDate,int IsDelivered,String orderDate) {
		System.out.println("Inside storePayment");
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
		try {
			MySQLDataStoreUtilities.insertOrder(orderId, username,usertype, orderName, orderPrice, address, zipCode, 
					creditCardNo, DeliveryDate,IsDelivered,orderDate);	
			
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
	  
	  public void addProduct(String productname,String producttype,String  productcondition,String  productmaker,
			  String productimage,String productquantity,String productprice,String productonsale,String productrebate){
				MySQLDataStoreUtilities.insertNewProduct(productname,producttype,productcondition,productmaker,
            		productimage,productquantity,productprice,productonsale,productrebate);

			}
}