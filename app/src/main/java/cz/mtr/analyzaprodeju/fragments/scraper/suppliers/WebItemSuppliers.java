package cz.mtr.analyzaprodeju.fragments.scraper.suppliers;

public class WebItemSuppliers {

	private String name = "", availability = "", price = "", date = "";

	public WebItemSuppliers() {

	}

	public String getName() {
		return name;
	}

	public String getAvailability() {
		return availability;
	}

	public String getPrice() {
		return price;
	}

	public String getDate() {
		return date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s %s", name, availability, price, date);
	}

}
