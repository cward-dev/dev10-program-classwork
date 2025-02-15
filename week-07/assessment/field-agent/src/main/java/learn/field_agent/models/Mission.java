package learn.field_agent.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Mission {

    private int missionId;
    private String codeName;
    private String notes;
    private LocalDate startDate;
    private LocalDate projectedEndDate;
    private LocalDate actualEndDate;
    private BigDecimal operationalCost;
    private int agencyId;

    public Mission() {
    }

    public Mission(String codeName, String notes, LocalDate startDate, LocalDate projectedEndDate, LocalDate actualEndDate, BigDecimal operationalCost, int agencyId) {
        this.codeName = codeName;
        this.notes = notes;
        this.startDate = startDate;
        this.projectedEndDate = projectedEndDate;
        this.actualEndDate = actualEndDate;
        this.operationalCost = operationalCost;
        this.agencyId = agencyId;
    }

    public Mission(int missionId, String codeName, String notes, LocalDate startDate, LocalDate projectedEndDate, LocalDate actualEndDate, BigDecimal operationalCost, int agencyId) {
        this.missionId = missionId;
        this.codeName = codeName;
        this.notes = notes;
        this.startDate = startDate;
        this.projectedEndDate = projectedEndDate;
        this.actualEndDate = actualEndDate;
        this.operationalCost = operationalCost;
        this.agencyId = agencyId;
    }

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getProjectedEndDate() {
        return projectedEndDate;
    }

    public void setProjectedEndDate(LocalDate projectedEndDate) {
        this.projectedEndDate = projectedEndDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public BigDecimal getOperationalCost() {
        return operationalCost;
    }

    public void setOperationalCost(BigDecimal operationalCost) {
        this.operationalCost = operationalCost;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return missionId == mission.missionId
                && agencyId == mission.agencyId
                && Objects.equals(codeName, mission.codeName)
                && Objects.equals(notes, mission.notes)
                && Objects.equals(startDate, mission.startDate)
                && Objects.equals(projectedEndDate, mission.projectedEndDate)
                && Objects.equals(actualEndDate, mission.actualEndDate)
                && Objects.equals(operationalCost, mission.operationalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(missionId, codeName, notes, startDate, projectedEndDate, actualEndDate, operationalCost, agencyId);
    }
}
