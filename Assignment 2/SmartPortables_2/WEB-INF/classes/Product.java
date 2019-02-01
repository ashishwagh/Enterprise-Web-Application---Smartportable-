
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
	String name;
	String id;
	String category;
	String condition;
	String manufacturer;
	int price;
	List<Accesory> accessories;
	String image;

	public Product() {
		accessories = new ArrayList<Accesory>();
	}

	void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCondition() {
		return condition;
	}

	void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	void setAccesory(List<Accesory> accessories) {
		this.accessories = accessories;
	}

	public List<Accesory> getAccesory() {
		return accessories;
	}

	void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

}
