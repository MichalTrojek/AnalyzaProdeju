package cz.mtr.analyzaprodeju.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Message implements Serializable {

	private static final long serialVersionUID = -6281824019160741963L;
	private HashMap<String, ArticleRow> analysis;
	private ArrayList<ExportArticle> orders;
	private ArrayList<ExportArticle> returns;

	public Message() {

	}

	public void createAnalysis(HashMap<String, ArticleRow> analysis) {
		this.analysis = analysis;
	}

	public void createExport(ArrayList<ExportArticle> orders, ArrayList<ExportArticle> returns) {
		this.orders = orders;
		this.returns = returns;
	}

	public ArrayList<ExportArticle> getOrders() {
		return this.orders;
	}

	public ArrayList<ExportArticle> getReturns() {
		return this.returns;
	}

	public HashMap<String, ArticleRow> getAnalysis() {
		return this.analysis;
	}

}
