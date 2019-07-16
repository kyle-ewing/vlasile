package vlasile.enemy;

import bwapi.Unit;
import bwapi.UnitType;

public class EnemyUnitInfo {
    private Unit unitActual;
    private UnitType unitType;

    public EnemyUnitInfo(Unit unitActual, UnitType unitType) {
        this.unitActual = unitActual;
        this.unitType = unitType;
    }

    public Unit getUnitActual() {
        return unitActual;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
}
