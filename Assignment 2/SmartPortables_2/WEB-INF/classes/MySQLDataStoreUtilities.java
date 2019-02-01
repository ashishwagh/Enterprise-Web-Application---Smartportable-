import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
			String ZipCode,String CardNumber,String DeliveryDate,int IsDelivered)
	{
		try
		{
			System.out.println("Inside insertOrder");
			getConnection();
			String insertIntoCustomerOrderQuery = "INSERT INTO CutomerOrders(UserName,Usertype,OrderName,OrderPrice,Address,ZipCode,"
					+ "CardNumber,DeliveryDate,IsDelivered) "
			+ "VALUES (?,?,?,?,?,?,?,?,?);";	
				
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
	        //select the table 
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
					//orderPayments.put(rs.getInt("OrderId"), arr);
					orderPayments.put(rs.getInt("OrderId"), arr);
				}
				
				//arr.remove(order);
	/*			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
				System.out.println("data is"+rs.getInt("OrderId")+orderPayments.get(rs.getInt("OrderId")));

				//add to orderpayment hashmap
				OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"));
				listOrderPayment.add(order);*/
						
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
        //select the table 
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


}
