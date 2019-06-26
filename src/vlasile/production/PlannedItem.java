package vlasile.production;

import bwapi.UnitType;

public class PlannedItem {
    private UnitType unitType;
    private Integer supply = 0;
    private int significance;
    private PlannedItemStatus plannedItemStatus;
    private PlannedItemType plannedItemType;
    private Integer ID;

    public PlannedItem(UnitType unitType, Integer supply, PlannedItemStatus plannedItemStatus, PlannedItemType plannedItemType) {
        this.unitType = unitType;
        this.supply = supply;
        this.plannedItemStatus = plannedItemStatus;
        this.plannedItemType = plannedItemType;
        this.ID = null;
    }

    public PlannedItem(UnitType unitType, Integer supply, int significance, PlannedItemStatus plannedItemStatus, PlannedItemType plannedItemType) {
        this.unitType = unitType;
        this.supply = supply;
        this.significance = significance;
        this.plannedItemStatus = plannedItemStatus;
        this.plannedItemType = plannedItemType;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public Integer getSupply() {
        return supply;
    }

    public void setSupply(Integer supply) {
        this.supply = supply;
    }

    public PlannedItemType getPlannedItemType() {
        return plannedItemType;
    }

    public void setPlannedItemType(PlannedItemType plannedItemType) {
        this.plannedItemType = plannedItemType;
    }

    public PlannedItemStatus getPlannedItemStatus() {
        return plannedItemStatus;
    }

    public void setPlannedItemStatus(PlannedItemStatus plannedItemStatus) {
        this.plannedItemStatus = plannedItemStatus;
    }

    public int getSignificance() {
        return significance;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
