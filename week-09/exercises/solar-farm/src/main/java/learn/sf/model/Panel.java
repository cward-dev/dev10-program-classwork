package learn.sf.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@NotNull(message = "Panel cannot be null.")
public class Panel {

    private int panelId;

    @NotBlank(message = "Section is required.")
    private String section;

    @Min(value = 1, message = "Row must be a positive number less than or equal to 250.")
    @Max(value = 250, message = "Row must be a positive number less than or equal to 250.")
    private int row;

    @Min(value = 1, message = "Column must be a positive number less than or equal to 250.")
    @Max(value = 250, message = "Column must be a positive number less than or equal to 250.")
    private int column;

    @Max(value = 2021, message = "Year must be between 1954 and 2021.")
    @Min(value = 1954, message = "Year must be between 1954 and 2021.")
    private int yearInstalled;

    @NotNull(message = "Material is required.")
    private PanelMaterial material;

    private boolean tracking;

    public Panel() {
    }

    public Panel(String section, int row, int column, int yearInstalled, PanelMaterial material, boolean tracking) {
        this.section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.material = material;
        this.tracking = tracking;
    }

    public Panel(int panelId, String section, int row, int column, int yearInstalled, PanelMaterial material, boolean tracking) {
        this.panelId = panelId;
        this.section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.material = material;
        this.tracking = tracking;
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
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    @Override // From unexplained encounters lesson
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panel panel = (Panel) o;
        return this.panelId == panel.panelId &&
                this.section.equals(panel.getSection()) &&
                this.row == panel.getRow() &&
                this.column == panel.getColumn() &&
                this.yearInstalled == panel.getYearInstalled() &&
                Objects.equals(this.material, panel.getMaterial()) &&
                this.tracking == panel.tracking;
    }

    @Override // From unexplained encounters lesson
    public int hashCode() {
        return Objects.hash(panelId, section, row, column, yearInstalled, material, tracking);
    }
}
