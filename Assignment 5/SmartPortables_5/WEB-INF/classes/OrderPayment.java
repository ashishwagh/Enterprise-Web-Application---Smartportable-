import java.io.Serializable;

public class OrderPayment implements Serializable{
	private int orderId;
	private String userName;
	private String orderName;
	private double orderPrice;
	private String creditCardNo;
	private String address;
	private String zipcode;
	private int IsDelivered;
	
	public OrderPayment(int orderId,String userName,String orderName,double orderPrice,String creditCardNo
			,String address,String zipcode,int isDelivered){
		this.orderId=orderId;
		this.userName=userName;
		this.orderName=orderName;
	 	this.orderPrice=orderPrice;
	 	this.creditCardNo=creditCardNo;
	 	this.address=address;
	 	this.zipcode=zipcode;
	 	this.IsDelivered=isDelivered;
		}

	public int getIsDelivered() {
		return IsDelivered;
	}

	public void setIsDelivered(int isDelivered) {
		IsDelivered = isDelivered;
	}



	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
}
