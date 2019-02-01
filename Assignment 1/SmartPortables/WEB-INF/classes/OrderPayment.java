import java.io.Serializable;

public class OrderPayment implements Serializable{
	private int orderId;
	private String userName;
	private String orderName;
	private double orderPrice;
	private String creditCardName;
	private String creditCardNo;
	
	public OrderPayment(int orderId,String userName,String orderName,double orderPrice,String creditCardName,String creditCardNo){
		this.orderId=orderId;
		this.userName=userName;
		this.orderName=orderName;
	 	this.orderPrice=orderPrice;
		this.creditCardName=creditCardName;
	 	this.creditCardNo=creditCardNo;
		}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
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
	
}
