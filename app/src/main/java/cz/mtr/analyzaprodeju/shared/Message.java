package cz.mtr.analyzaprodeju.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Message implements Serializable {

	private static final long serialVersionUID = -6281824019160741963L;
	private HashMap<String, SharedArticle> analysis;
	private ArrayList<ExportSharedArticle> orders;
	private ArrayList<ExportSharedArticle> returns;

	public Message() {

	}

	public void createAnalysis(HashMap<String, SharedArticle> analysis) {
		this.analysis = analysis;
	}

	public void createExport(ArrayList<ExportSharedArticle> orders, ArrayList<ExportSharedArticle> returns) {
		this.orders = orders;
		this.returns = returns;
	}

	public ArrayList<ExportSharedArticle> getOrders() {
		return this.orders;
	}

	public ArrayList<ExportSharedArticle> getReturns() {
		return this.returns;
	}

	public HashMap<String, SharedArticle> getAnalysis() {
		return this.analysis;
	}

}
