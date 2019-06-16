package vlasile.buildorder;

import bwapi.Game;
import bwapi.Player;
import bwapi.Race;
import vlasile.GameMethods;
import vlasile.Vlasile;

public class EnemyInformation {

    private static Integer strategy = null;
    private static Player _enemy = null;

    public static void getEnemyRace() {
        if(strategy == null) {
            if(enemy().getRace().equals(Race.Terran)) {
                strategy = 0;
            }
            else if(enemy().getRace().equals(Race.Protoss)) {
                strategy = 1;
            }
            else if(enemy().getRace().equals(Race.Zerg)) {
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

    public static Integer getStrategy() {
        return strategy;
    }
}
