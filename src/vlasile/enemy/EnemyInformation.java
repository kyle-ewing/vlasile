package vlasile.enemy;

import bwapi.*;
import vlasile.UnitCount;
import vlasile.Vlasile;

import java.util.HashMap;
import java.util.Iterator;

public class EnemyInformation {

    private static Integer race = null;
    private static Player _enemy = null;
    private static UnitType baseType;
    private static HashMap enemyBuildings = UnitCount.getEnemyUnitCount();

    public static void getEnemyRace() {
        if(race == null) {
            if(enemy().getRace().equals(Race.Terran)) {
                baseType = UnitType.Terran_Command_Center;
                race = 0;
            }
            else if(enemy().getRace().equals(Race.Protoss)) {
                baseType = UnitType.Protoss_Nexus;
                race = 1;
            }
            else if(enemy().getRace().equals(Race.Zerg)) {
                baseType = UnitType.Zerg_Hatchery;
                race = 2;
            }
            else {
                race = 3;
            }
        }
    }

    private static Player enemy() {
        if(_enemy == null) {
            _enemy = Vlasile.getBwapi().enemies().iterator().next();
        }
        return _enemy;
    }

    public static boolean enemyBaseDiscovered() {
        if(enemyBuildings.containsValue(baseType)) {
            return true;
        }
            return false;
    }

    public static Position getEnemyBasePosition() {
        if(!UnitCount.getEnemyBuildings().isEmpty()) {
            return UnitCount.getEnemyBuildings().values().iterator().next().getTilePosition().toPosition();
        }
        return null;
    }

    public static Integer getRace() {
        return race;
    }
}
