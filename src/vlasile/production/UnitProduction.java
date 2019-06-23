package vlasile.production;

import bwapi.Unit;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.Vlasile;

public class UnitProduction {

    public static void buildMarine() {
        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.getType() == UnitType.Terran_Barracks && GameMethods.getAvailableMinerals() >= 50 && !(unit.isTraining())) {
                unit.train(UnitType.Terran_Marine);
            }
        }
    }
}
