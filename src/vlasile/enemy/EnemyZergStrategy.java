package vlasile.enemy;

import bwapi.UnitType;
import vlasile.GameMethods;

public class EnemyZergStrategy extends EnemyStrategy{

    public static final EnemyStrategy _4Pool = new EnemyZergStrategy();

    public static void initialize() {
        _4Pool.setName("4 Pool");
    }

    //Assess enemy buildings/units/time passed to determine their strategy
    public static EnemyStrategy detectStrategy() {
        int pool = GameMethods.countUnitsOfType(UnitType.Zerg_Spawning_Pool);
        int spire = GameMethods.countUnitsOfType(UnitType.Zerg_Spire);
        //int expansion = GameMethods.countUnitsOfType(UnitType.Zerg_Hatchery);
        int drones = GameMethods.countUnitsOfType(UnitType.Zerg_Drone);

        if(pool > 0 && GameMethods.getSeconds() < 120 & drones <= 5) {
            return _4Pool;
        }
        return null;
    }
}
