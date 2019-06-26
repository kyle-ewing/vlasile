package vlasile.enemy;

import bwapi.TilePosition;
import bwapi.UnitType;

public class EnemyBuildingInfo {
    private UnitType unitType;
    private TilePosition tilePosition;

    public EnemyBuildingInfo(UnitType unitType, TilePosition tilePosition) {
        this.unitType = unitType;
        this.tilePosition = tilePosition;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public TilePosition getTilePosition() {
        return tilePosition;
    }

    public void setTilePosition(TilePosition tilePosition) {
        this.tilePosition = tilePosition;
    }
}
