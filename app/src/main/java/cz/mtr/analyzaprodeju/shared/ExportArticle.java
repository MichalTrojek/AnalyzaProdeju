package cz.mtr.analyzaprodeju.shared;

public class ExportArticle extends ArticleRow {

	private static final long serialVersionUID = -9167557047115507192L;
	private String exportAmount;

	public ExportArticle() {

	}

	public String getExportAmount() {
		return this.exportAmount;
	}

	public void setExportAmount(String amount) {
		this.exportAmount = amount;
	}

}
