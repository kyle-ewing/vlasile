package vlasile.enemy;

import bwapi.Player;
import bwapi.Race;
import bwapi.UnitType;
import vlasile.UnitCount;
import vlasile.Vlasile;

public class EnemyInformation {

    private static Integer strategy = null;
    private static Player _enemy = null;
    private static UnitType baseType;

    public static void getEnemyRace() {
        if(strategy == null) {
            if(enemy().getRace().equals(Race.Terran)) {
                baseType = UnitType.Terran_Command_Center;
                strategy = 0;
            }
            else if(enemy().getRace().equals(Race.Protoss)) {
                baseType = UnitType.Protoss_Nexus;
                strategy = 1;
            }
            else if(enemy().getRace().equals(Race.Zerg)) {
                baseType = UnitType.Zerg_Hatchery;
                strategy = 2;
            }
            else {
                strategy = 3;
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
        //if(UnitCount.getEnemyBuildings().containsValue(EnemyBuildingInfo.))
            return false;
    }

    public static Integer getStrategy() {
        return strategy;
    }
}
