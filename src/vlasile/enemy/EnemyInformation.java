package vlasile.enemy;

import bwapi.Player;
import bwapi.Race;
import bwapi.UnitType;
import vlasile.Vlasile;

public class EnemyInformation {

    private static Integer race = null;
    private static Player _enemy = null;
    private static UnitType baseType;

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
        //if(UnitCount.getEnemyBuildings().containsValue(EnemyBuildingInfo.))
            return false;
    }

    public static Integer getRace() {
        return race;
    }
}
