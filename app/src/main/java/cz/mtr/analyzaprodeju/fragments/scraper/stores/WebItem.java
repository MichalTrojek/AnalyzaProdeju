package cz.mtr.analyzaprodeju.fragments.scraper.stores;

public class WebItem {

	private String name, store, eshop, total, vk, regal, price;

	public WebItem() {
		this.name = "";
		this.store = "0";
		this.eshop = "0";
		this.total = "0";
		this.vk = "0/0";
		this.regal = "";
		this.price = "0";

	}

	public String getName() {
		return name;
	}

	public String getStore() {
		return store;
	}

	public String getEshop() {
		return eshop;
	}

	public String getTotal() {
		return total;
	}

	public String getVk() {
		return vk;
	}

	public String getRegal() {
		return regal;
	}

	public String getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public void setEshop(String eshop) {
		this.eshop = eshop;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setVk(String vk) {
		this.vk = vk;
	}

	public void setRegal(String regal) {
		this.regal = regal;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s %s %s %s %s", name, store, eshop, total, vk, regal, price);
	}

}
