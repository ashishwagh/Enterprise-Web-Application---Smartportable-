
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
	int quantity;
	String onsale;
	String rebate;

	public Product() {
		accessories = new ArrayList<Accesory>();
	}
	
	

	public Product(String name,String category, String condition, String manufacturer,String image, int quantity, int price,
			  String onsale, String rebate) {
		super();
		this.name = name;
		this.category = category;
		this.condition = condition;
		this.manufacturer = manufacturer;
		this.price = price;
		this.image = image;
		this.quantity = quantity;
		this.onsale = onsale;
		this.rebate = rebate;
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

	public List<Accesory> getAccessories() {
		return accessories;
	}

	public void setAccessories(List<Accesory> accessories) {
		this.accessories = accessories;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOnsale() {
		return onsale;
	}

	public void setOnsale(String onsale) {
		this.onsale = onsale;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

}
