package cz.mtr.analyzaprodeju.shared;

public class ExportSharedArticle extends SharedArticle {

    private static final long serialVersionUID = -9167557047115507192L;
    private String exportAmount;

    public ExportSharedArticle() {

    }

    public String getExportAmount() {
        return this.exportAmount;
    }

    public void setExportAmount(String amount) {
        this.exportAmount = amount;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ExportSharedArticle)) {
            return false;
        }

        ExportSharedArticle other = (ExportSharedArticle) obj;
        return this.getEan() == other.getEan();
    }

}
