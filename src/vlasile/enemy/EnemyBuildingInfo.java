package vlasile.enemy;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

public class EnemyBuildingInfo {
    private UnitType unitType;
    private TilePosition tilePosition;
    private Unit unitActual;
    private Position position;

    public EnemyBuildingInfo(UnitType unitType, TilePosition tilePosition, Unit unitActual, Position position) {
        this.unitType = unitType;
        this.tilePosition = tilePosition;
        this.unitActual = unitActual;
        this.position = position;
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

    public Unit getUnitActual() {
        return unitActual;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
