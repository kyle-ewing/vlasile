package vlasile;

import bwapi.Unit;
import bwapi.UnitType;
import vlasile.enemy.EnemyBuildingInfo;
import vlasile.unitstatus.WorkerStatus;

import java.util.HashMap;

public class UnitCount {
    private static HashMap<UnitType, Integer> unitCount = new HashMap<>();
    private static HashMap<Integer, EnemyBuildingInfo> enemyBuildings = new HashMap<>();
    private static HashMap<Integer, UnitType> enemyUnitCount = new HashMap<>();
    private static HashMap<Integer, WorkerStatus> workers = new HashMap<>();

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
            enemyBuildings.put(unit.getID(), new EnemyBuildingInfo(unit.getType(), unit.getTilePosition(), unit, unit.getPosition()));
        }
        else {
            if(!enemyBuildings.get(unit.getID()).equals(unit.getTilePosition())) {
                enemyBuildings.get(unit.getID()).setTilePosition(unit.getTilePosition());
            }
        }
    }

    public static void addEnemyUnit(Unit unit) {
        if(!enemyUnitCount.containsKey(unit.getID())) {
            enemyUnitCount.put(unit.getID(), unit.getType());
            System.out.println("New enemy " + unit.getType() + " found");
        }

    }

    public static void removeEnemyBuilding(Unit unit) {
        enemyBuildings.remove(unit.getID());
    }

    public static void addWorker(Unit unit) {
        if(!workers.containsKey(unit.getID())) {
            workers.put(unit.getID(), WorkerStatus.IDLE);
        }
    }

    public static void removeWorker(Unit unit) {
        if(workers.containsKey(unit.getID())) {
            workers.remove(unit.getID());
            System.out.println("Removed " + unit.getType() + unit.getID());
        }
    }

    public static HashMap<UnitType, Integer> getUnitCount() {
        return unitCount;
    }

    public static HashMap<Integer, EnemyBuildingInfo> getEnemyBuildings() {
        return enemyBuildings;
    }

    public static HashMap<Integer, UnitType> getEnemyUnitCount() {
        return enemyUnitCount;
    }

    public static HashMap<Integer, WorkerStatus> getWorkers() {
        return workers;
    }
}
