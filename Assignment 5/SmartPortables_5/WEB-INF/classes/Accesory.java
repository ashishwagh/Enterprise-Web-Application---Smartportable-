
import java.io.Serializable;

public class Accesory implements Serializable {
	String Aname;
	String id;
	String Aimage;

	public Accesory() {
	}

	void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	void setAImage(String Aimage) {
		this.Aimage = Aimage;
	}

	public String getAImage() {
		return Aimage;
	}

	void setAName(String Aname) {
		this.Aname = Aname;
	}

	public String getAName() {
		return Aname;
	}

}
