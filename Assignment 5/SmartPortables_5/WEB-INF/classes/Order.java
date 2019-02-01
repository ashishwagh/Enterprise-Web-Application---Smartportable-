

import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Order implements Serializable {
   String OrderId;
    String username;
    int price;
    String Address;
	String Zip;
	String date;
    
    public Order()
	{
	}

	
void setprice(int price) {
	this.price = price;
}
public int getprice()
{
	return price;
}
void setOrderId(String OrderId) {
	this.OrderId = OrderId;
}

public String getOrderId()
{
	return OrderId;
}
void setDate(String date) {
	this.date = date;
}

public String getDate()
{
	return date;
}
void setZip(String Zip) {
	this.Zip = Zip;
}

public String getZip()
{
	return Zip;
}
void setUsername(String username) {
	this.username = username;
}
public String getUsername()
{
	return username;
}
void setAddress(String Address) {
	this.Address = Address;
}
public String getAddress()
{
	return Address;
}



}
