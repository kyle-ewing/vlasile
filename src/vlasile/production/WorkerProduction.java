package vlasile.production;

import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.UnitCount;
import vlasile.enemy.StrategyController;

public class WorkerProduction {
    private static UnitProduction scv = new UnitProduction();

    //Arbitrary cap to stop building scvs
    //Needs to be removed when logic for additional bases is added
    private static boolean scvCap() {
        if (UnitCount.getUnitCount().get(UnitType.Terran_SCV) >= 18) {
            return false;
        } else {
            return true;
        }
    }

    public static void buildScv() {
        for (PlannedItem pi : StrategyController.getPlannedItems()) {
            if(pi.getPlannedItemStatus() != PlannedItemStatus.NOT_STARTED) {
                continue;
            }
            if (pi.getSupply() > GameMethods.getSupplyUsed()) {
                if (scvCap()) {
                    scv.buildScv();
                }
                break;
            }
            break;
        }
    }
}
