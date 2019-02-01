import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MySQLDataStoreUtilities extends HttpServlet {

	static Connection conn = null;

	public static String getConnection()
	{

		System.out.println("Inside getConnection");
		try
		{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartpotables","root","ashish");
		return "successfull";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "unsuccessfull";
		}
	}
	
	public static void insertUser(String username,String password,String usertype,String firstname,String lastname)
	{
		try
		{	

			System.out.println("Inside insertUser");
			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO UserInfo(UserName,Password,Usertype,FirstName,LastName) "
			+ "VALUES (?,?,?,?,?);";	
					
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,username);
			pst.setString(2,password);
			pst.setString(3,usertype);
			pst.setString(4,firstname);
			pst.setString(5,lastname);
			pst.execute();
			conn.close();
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		
	}
	
	public static HashMap<String,Users> selectUser()
	{	
		System.out.println("Inside selectUser");
		HashMap<String,Users> hm=new HashMap<String,Users>();
		try 
		{
			getConnection();
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from  UserInfo;";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next())
			{	Users user = new Users(rs.getString("UserName"),rs.getString("Password"),
					rs.getString("Usertype"),rs.getString("FirstName"),rs.getString("LastName"));
					hm.put(rs.getString("username")+rs.getString("Usertype"), user);
					System.out.println("rs.getString(\"username\")+rs.getString(\"Usertype\") "+rs.getString("username")+rs.getString("Usertype"));
			}
			
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}
	
	public static void insertOrder(int orderId,String userName,String usertype,String orderName,double orderPrice,String Address,
			String ZipCode,String CardNumber,String DeliveryDate,int IsDelivered,String orderDate)
	{
		try
		{
			System.out.println("Inside insertOrder");
			getConnection();
			String insertIntoCustomerOrderQuery = "INSERT INTO CutomerOrders(UserName,Usertype,OrderName,OrderPrice,Address,ZipCode,"
					+ "CardNumber,DeliveryDate,IsDelivered,OrderDate,OrderId) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);";	
				
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
			//set the parameter for each column and execute the prepared statement
			
			pst.setString(1,userName);
			pst.setString(2,usertype);
			pst.setString(3,orderName);
			pst.setDouble(4,orderPrice);
			pst.setString(5,Address);
			pst.setString(6,ZipCode);
			pst.setString(7,CardNumber);
			pst.setString(8,DeliveryDate);
			pst.setInt(9,IsDelivered);
			pst.setString(10,orderDate);
			pst.setInt(11,orderId);
			pst.execute();
			conn.close();
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}		
	}
	
	public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder(String username,String usertype)
	{	

		HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
			
		try
		{					
			getConnection();
			String selectOrderQuery = new String();
			if(usertype !=null && usertype.equals("Customer")) {
				
				selectOrderQuery ="select * from CutomerOrders where UserName=\""+username+"\" and Usertype=\""+usertype+"\""
					+ " order by OrderId;";
			}else {
				selectOrderQuery ="select * from CutomerOrders where Usertype=\"Customer\""
						+ " order by OrderId;";
			}			
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				if(!orderPayments.containsKey(rs.getInt("OrderId")))
				{	
					ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
					OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("UserName"),rs.getString("OrderName"),
							Double.parseDouble(rs.getString("OrderPrice")),rs.getString("CardNumber"),rs.getString("Address"),rs.getString("ZipCode"),
							rs.getInt("IsDelivered"));
					arr.add(order);
					orderPayments.put(rs.getInt("OrderId"), arr);
				}
						
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orderPayments;
	}
	
	public static int cancelOrder(int orderId,String userName,String usertype)
	{
		int rs= 0;
		try
		{	
		getConnection();
        //select the table 
		String selectOrderQuery = new String();
		selectOrderQuery ="Delete from CutomerOrders where OrderId = "+orderId+ " and UserName=\""+userName+"\" and Usertype=\""+usertype+"\";";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		rs = pst.executeUpdate();
		conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static int UpdateOrder(int orderId,String DeliveryDate)
	{
		int rs=0;
		try
		{	
		getConnection();
		String selectOrderQuery = new String();
		selectOrderQuery ="Update CutomerOrders set IsDelivered=1,DeliveryDate=\""+DeliveryDate+"\" where OrderId = "+orderId+ ";";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		rs = pst.executeUpdate();
		conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static ArrayList <Mostsoldzip> mostsoldZip(){
		  ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
		  try{
			  
		  getConnection();
		  String selectOrderQuery = new String();
			selectOrderQuery ="select count(*) as Count,ZipCode from cutomerorders group by ZipCode " + 
					"order by Count desc limit 5;";
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
	        
			String zipcode =(rs.getString("ZipCode"));
	        String count = (rs.getString("Count"));	
	        Mostsoldzip mostsldzip = new Mostsoldzip(zipcode,count);
			mostsoldzip.add(mostsldzip);
		
		  }
		}catch (Exception e){ System.out.println(e.getMessage());}
	      return mostsoldzip;
	  }
	
	public static ArrayList <Mostsold> mostsoldProducts(){
		  ArrayList <Mostsold> mostsold = new ArrayList <Mostsold> ();
		  try{
			  
		  
	      getConnection();
		  String selectOrderQuery = new String();
			selectOrderQuery ="select distinct count(*) as Count,OrderName from cutomerorders " + 
					"group by OrderName order by Count desc limit 5; ";
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();
		  
			while(rs.next()) {
		  
	      
	       
			String prodcutname =(rs.getString("OrderName"));
			String count = (rs.getString("Count"));	
	        Mostsold mostsld = new Mostsold(prodcutname,count);
			mostsold.add(mostsld);
		
		  }
		  
		 
		  
		}catch (Exception e){ System.out.println(e.getMessage());}
	      return mostsold;
	  }
	public static void insertProduct() {
		System.out.println("inside insertProduct "+Saxpaser.hm.size());
		
		try{
			
			int quantity = 0;
			getConnection();
			truncateTable();
			String insertProductQurey = "INSERT INTO Product(ProductName,Category,ProductCondition,Manufacturer,"
			 		+ "ProductImage,Quantity,Price,OnSale,Rebate,Id) VALUES(?,?,?,?,?,?,?,?,?,?);"; 
			for(Map.Entry<String,Product> entry : Saxpaser.hm.entrySet())
			{   
				String selectOrderQuery = new String();
				Product product = entry.getValue();
				quantity=product.getQuantity();
				selectOrderQuery ="select Count(*) as p from CutomerOrders where OrderName=\""+product.getName()+"\""
						+ " group by OrderName order by p desc;";
				PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
				ResultSet rs = pst.executeQuery();
				 if(rs.next())
				 {
					 quantity = quantity - rs.getInt("p");
				 }
				
				PreparedStatement pst1 = conn.prepareStatement(insertProductQurey);
				 pst1.setString(1, product.getName());
				 pst1.setString(2, product.getCategory());
				 pst1.setString(3, product.getCondition());
				 pst1.setString(4, product.getManufacturer());
				 pst1.setString(5,product.getImage() );
				 pst1.setInt(6,quantity);
				 pst1.setDouble(7, new Double(product.getPrice()));
				 pst1.setString(8,product.getOnsale());
				 pst1.setString(9,product.getRebate());
				 pst1.setInt(10,product.getId());
				
				pst1.executeUpdate();
			}
				
			}catch(Exception e)
			{
		  		e.printStackTrace();
			}

		}

	public static  void truncateTable() {
		    try {
				getConnection();
				PreparedStatement  ps = conn.prepareStatement("truncate table Product;");
		         ps.execute();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
	
	public static void insertNewProduct(String productname,String producttype,String  productcondition,String  productmaker,
			  String productimage,String productquantity,String productprice,String productonsale,String productrebate) {
		try {
	    	getConnection();
	    	int maxId=0;
	    	String selectMaxIdQuery = new String();
	    	
			selectMaxIdQuery ="select max(Id) as MaxId from Product;" ;
			
			PreparedStatement pst1 = conn.prepareStatement(selectMaxIdQuery);
			ResultSet rs = pst1.executeQuery();
			 if(rs.next())
			 {
				 maxId = rs.getInt("MaxId")+1;
			 }
	    	PreparedStatement pst = conn.prepareStatement("INSERT INTO Product(ProductName,Category,ProductCondition,Manufacturer,"
			 		+ "ProductImage,Quantity,Price,OnSale,Rebate,Id) VALUES(?,?,?,?,?,?,?,?,?,?);"); 
	  
			 pst.setString(1, productname);
			 pst.setString(2, producttype);
			 pst.setString(3, productcondition);
			 pst.setString(4, productmaker);
			 pst.setString(5,productimage );
			 pst.setInt(6,Integer.parseInt(productquantity));
			 pst.setDouble(7, new Double(productprice));
			 pst.setString(8,productonsale);
			 pst.setString(9,productrebate);
			 pst.setInt(10,maxId);

			 pst.execute();
	        conn.close();
	        Saxpaser.hm.put(maxId+"", new Product(productname,producttype,productcondition,productmaker,productimage,Integer.parseInt(productquantity)
	        		,Integer.parseInt(productprice),productonsale,productrebate,maxId));

	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	}
	
	public static HashMap<String,Product> getProducts()
	{	
		System.out.println("Inside getProducts");
		HashMap<String,Product> hm=new HashMap<String,Product>();
		try 
		{
			int a=1;
			getConnection();
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from  Product order by Id;";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next())
			{	Product product = new Product(rs.getString("ProductName"),rs.getString("Category"),
					rs.getString("ProductCondition"),rs.getString("Manufacturer"),rs.getString("ProductImage"),rs.getInt("Quantity"),
					(int) Math.round(rs.getDouble("Price")),rs.getString("OnSale"),rs.getString("Rebate"),rs.getInt("Id"));
					hm.put(rs.getInt("Id")+ "",product);
			}
			
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hm;			
	}
	
	public ArrayList<Product> checkInventoryonsale() {
		 ArrayList<Product> products = new ArrayList<Product>();
		    try {
				getConnection();
				PreparedStatement  ps = conn.prepareStatement("select ProductName,Price,Quantity,OnSale,Category from product where OnSale = \"yes\"; "); 
		        ResultSet rs= ps.executeQuery();
		         while(rs.next()){
						Product product = new Product();
							product.setName(rs.getString("ProductName"));
							product.setPrice(rs.getInt("Price"));
							product.setQuantity(rs.getInt("Quantity"));
							product.setCategory(rs.getString("Category"));
							product.setOnsale(rs.getString("OnSale"));
							products.add(product);
						}
				

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			return products; 

		}
	
	public ArrayList<Product> checkInventory() {
		 ArrayList<Product> products = new ArrayList<Product>();
		    try {
				getConnection();

				PreparedStatement ps = conn.prepareStatement("select ProductName,Price,Quantity from product; "); 
		        ResultSet rs= ps.executeQuery();
		         while(rs.next()){
						Product product = new Product();
							product.setName(rs.getString("ProductName"));
							product.setPrice(rs.getInt("Price"));
							product.setQuantity(rs.getInt("Quantity"));
							products.add(product);
						}

		    } catch (Exception e) {
		        e.printStackTrace();
		    } 
		    return products;	
		}
	
	public ArrayList<Product> checkInventoryrebate() {
		 ArrayList<Product> products = new ArrayList<Product>();
		    try {
				getConnection();
				PreparedStatement ps = conn.prepareStatement("select ProductName,Price,Quantity,Rebate,Category from product  where Rebate = \"yes\"; "); 
		       
		        ResultSet rs= ps.executeQuery();
		         while(rs.next()){
						Product product = new Product();
							product.setName(rs.getString("ProductName"));
							product.setPrice(rs.getInt("Price"));
							product.setQuantity(rs.getInt("Quantity"));
							product.setCategory(rs.getString("Category"));
							product.setRebate(rs.getString("Rebate"));
							products.add(product);
						}

		    } catch (Exception e) {
		        e.printStackTrace();
		    } 
		    return products;
		}
	
	public ArrayList<Product> checkSales() {
		 ArrayList<Product> products = new ArrayList<Product>();
		    try {
				getConnection();
				PreparedStatement ps = conn.prepareStatement("select *,count(*) as Count from cutomerorders " + 
						"group by OrderName order by Count desc;"); 
		        ResultSet rs= ps.executeQuery();
		         while(rs.next()){
						Product product = new Product();
							product.setName(rs.getString("OrderName"));
							product.setPrice(rs.getInt("OrderPrice"));
							product.setQuantity(rs.getInt("Count"));
							products.add(product);
						}
					return products;	

		    } catch (Exception e) {
		        e.printStackTrace();
				return null;
		    } 

		}
	
	public ArrayList<Order> checkSalesdate() {
		 ArrayList<Order> orders = new ArrayList<Order>();
		    try {
				getConnection();
				PreparedStatement ps = conn.prepareStatement("select *,sum(OrderPrice) as TotalSale from cutomerorders group by OrderDate order by TotalSale desc; "); 
		       
		        ResultSet rs= ps.executeQuery();
		         while(rs.next()){
						Order  order = new Order();
							order.setDate(rs.getString("OrderDate"));
							order.setprice(rs.getInt("TotalSale"));
							orders.add(order);
						}

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return orders;	
		}

	public static void updateProduct(String productname, String producttype, String productcondition,
			String productmaker, String productimage, String productquantity, String productprice, String productonsale,
			String productrebate,String id) {
			int rs=0;
			try
			{	
			getConnection();
			String selectOrderQuery = new String();
			selectOrderQuery ="Update Product set ProductName=\""+productname+"\" "
					+ ",Category=\""+producttype+"\" "
					+ ",ProductCondition=\""+productcondition+"\" "
					+ ",Manufacturer=\""+productmaker+"\" "
					+ ",ProductImage=\""+productimage+"\" "
					+ ",Quantity=\""+Integer.parseInt(productquantity)+"\" "
					+ ",Price=\""+Double.parseDouble(productprice)+"\" "
					+ ",OnSale=\""+productonsale+"\" "
					+ ",Rebate=\""+productrebate+"\" "
					+ "where Id = "+Integer.parseInt(id)+ ";";
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			rs = pst.executeUpdate();
			conn.close();
			Saxpaser.hm.put(id,new Product( productname,  producttype,  productcondition,
					 productmaker,  productimage,  Integer.parseInt(productquantity),  Integer.parseInt(productprice),  productonsale,
					 productrebate,Integer.parseInt(id)));
			}catch (Exception e) {
				e.printStackTrace();
			}

		
	}

	public static int deleteProduct(String productid) {
		
		int rs= 0;
		try
		{	
		getConnection();
		String selectOrderQuery = new String();
		selectOrderQuery ="Delete from Product where Id = "+productid+ " ;";
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		rs = pst.executeUpdate();
		conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		Saxpaser.hm.remove(productid);
		return rs;
	}
	
	public static HashMap<Integer, ArrayList<OrderPayment>> selectMaxOrderId()
	{	

		HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
			
		try
		{					
			getConnection();
			String selectOrderQuery = new String();
				selectOrderQuery ="select * from CutomerOrders where Usertype=\"Customer\""
						+ " order by OrderId;";
			PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				if(!orderPayments.containsKey(rs.getInt("OrderId")))
				{	
					ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
					OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("UserName"),rs.getString("OrderName"),
							Double.parseDouble(rs.getString("OrderPrice")),rs.getString("CardNumber"),rs.getString("Address"),rs.getString("ZipCode"),
							rs.getInt("IsDelivered"));
					arr.add(order);
					orderPayments.put(rs.getInt("OrderId"), arr);
				}
						
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orderPayments;
	}

}
