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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportSharedArticle member = (ExportSharedArticle) o;

        if (!getEan().equals(member.getEan())) return false;

        return true;
    }

}
