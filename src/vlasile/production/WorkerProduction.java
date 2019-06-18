package vlasile.production;

import bwapi.Unit;
import bwapi.UnitType;
import vlasile.Vlasile;
import vlasile.UnitCount;

public class WorkerProduction {

    private static boolean flag = false;

    private static void scvCap() {
        if(UnitCount.getUnitCount().get(UnitType.Terran_SCV) >= 15) {
            System.out.println("SCV Cap reached");
            flag = true;
        }
    }

    public static void buildScv() {
        if(!flag) {
            for(Unit myUnit : Vlasile.getSelf().getUnits()) {
                if(myUnit.getType() == UnitType.Terran_Command_Center && Vlasile.getSelf().minerals() >= 50 && !myUnit.isTraining()) {
                    myUnit.train(UnitType.Terran_SCV);
                }
            }
            scvCap();
        }
    }
}
