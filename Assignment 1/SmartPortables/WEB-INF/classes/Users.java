
public class Users implements java.io.Serializable {
	private String username;
	private String password;
	private String usertype;
	private String firstname;
	private String lastname;

	public Users(String username, String password, String usertype, String firstname, String lastname) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
		this.firstname = firstname;
		this.lastname = lastname;

	}

	public Users() {
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getfirstname() {
		return firstname;
	}

	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getlastname() {
		return lastname;
	}

	public void setlastname(String lastname) {
		this.lastname = lastname;
	}

}
