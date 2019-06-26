package vlasile;

import bwapi.Player;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.enemy.EnemyBuildingInfo;

import java.util.HashMap;

public class UnitCount {
    private static HashMap<UnitType, Integer> unitCount = new HashMap<>();
    private static HashMap<Integer, EnemyBuildingInfo> enemyBuildings = new HashMap<>();

//    public static void initialFriendlyUnits() {
//        for(Unit unit : self.getUnits()) {
//            addFriendlyUnit(unit);
//        }
//    }

    public static void addFriendlyUnit(Unit unit) {
        if(!unitCount.containsKey(unit.getType())) {
            unitCount.put(unit.getType(), 1);
        }
        else {
            unitCount.put(unit.getType(), unitCount.get(unit.getType()) +1);
        }
    }

    public static void removeFriendlyUnit(Unit unit) {
        if(unitCount.get(unit.getType()) == 1) {
            unitCount.remove(unit.getType());
        }
        else {
            unitCount.put(unit.getType(), unitCount.get((unit.getType())) -1);
        }
    }

    public static void addEnemyBuilding(Unit unit) {
        if(!enemyBuildings.containsKey(unit.getID())) {
            enemyBuildings.put(unit.getID(), new EnemyBuildingInfo(unit.getType(), unit.getTilePosition()));
        }
        else {
            if(!enemyBuildings.get(unit.getID()).equals(unit.getTilePosition())) {
                enemyBuildings.get(unit.getID()).setTilePosition(unit.getTilePosition());
            }
        }
    }

    public static HashMap<UnitType, Integer> getUnitCount() {
        return unitCount;
    }

    public static HashMap<Integer, EnemyBuildingInfo> getEnemyBuildings() {
        return enemyBuildings;
    }
}
