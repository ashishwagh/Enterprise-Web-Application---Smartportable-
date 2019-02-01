
import java.io.Serializable;

public class Cart implements Serializable {
	String name;
	int id;
	String image;
	String price;
	String Zip;
	public Cart(String name, String price, String image, int id) {
		this.name = name;
		this.price = price;
		this.image = image;
		this.id = id;
	}

	void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
void setZip(String Zip) {
	this.Zip = Zip;
}
public String getZip()
{
	return Zip;
}
	void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return price;
	}

}
