package learn.sf.model;

import java.util.Objects;

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

    public Panel(String section, int row, int column, int yearInstalled, PanelMaterial material, boolean isTracking) {
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

    @Override // From unexplained encounters lesson
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panel panel = (Panel) o;
        return panelId == panel.panelId &&
                Objects.equals(section, panel.getSection()) &&
                row == panel.getRow() &&
                column == panel.getColumn() &&
                yearInstalled == panel.getYearInstalled() &&
                Objects.equals(material, panel.getMaterial()) &&
                isTracking == panel.isTracking;
    }

    @Override // From unexplained encounters lesson
    public int hashCode() {
        return Objects.hash(panelId, section, row, column, yearInstalled, material, isTracking);
    }
}
