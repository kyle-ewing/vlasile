package vlasile.production;

import bwapi.Unit;
import bwapi.UnitType;
import vlasile.Vlasile;
import vlasile.UnitCount;

public class WorkerProduction {

    private static boolean scvCap() {
        if(UnitCount.getUnitCount().get(UnitType.Terran_SCV) >= 15) {
            System.out.println("True");
            return true;
        }
        else {
            return false;
        }
    }

    public static void buildScv() {
        if(!scvCap()) {
            for(Unit myUnit : Vlasile.getSelf().getUnits()) {
                if(myUnit.getType() == UnitType.Terran_Command_Center && Vlasile.getSelf().minerals() >= 50) {
                    myUnit.train(UnitType.Terran_SCV);
                }
            }
        }
    }
}
