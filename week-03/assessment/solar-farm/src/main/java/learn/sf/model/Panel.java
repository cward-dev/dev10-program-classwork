package learn.sf.model;

public class Panel {

    private int panelId;
    private String section;
    private int row;
    private int column;
    private int yearInstalled;
    private PanelMaterial material;
    private boolean isTracking;

    public Panel() {

    }

    public Panel(int panelId, String section, int row, int column, int yearInstalled, PanelMaterial material, boolean isTracking) {
        this.panelId = panelId;
        this.section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.material = material;
        this.isTracking = isTracking;
    }

    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getYearInstalled() {
        return yearInstalled;
    }

    public void setYearInstalled(int yearInstalled) {
        this.yearInstalled = yearInstalled;
    }

    public PanelMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PanelMaterial material) {
        this.material = material;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }
}
