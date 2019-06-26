package vlasile.production;

import bwapi.Unit;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.Vlasile;

public class UnitProduction {

    public void buildMarine() {
        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.getType() == UnitType.Terran_Barracks && GameMethods.getAvailableMinerals() >= 50 && !(unit.isTraining())) {
                unit.train(UnitType.Terran_Marine);
            }
        }
    }

    public  void buildScv() {
        for (Unit myUnit : Vlasile.getSelf().getUnits()) {
            if (myUnit.getType() == UnitType.Terran_Command_Center && !myUnit.isTraining() && GameMethods.getAvailableMinerals() >= 50) {
                myUnit.train(UnitType.Terran_SCV);
            }
        }
    }
}
