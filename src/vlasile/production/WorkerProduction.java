package vlasile.production;

import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.Vlasile;
import vlasile.UnitCount;
import vlasile.enemy.StrategyController;

public class WorkerProduction {

    private static boolean scvCap() {
        if(UnitCount.getUnitCount().get(UnitType.Terran_SCV) >= 15) {
            return false;
        }
        else {
            return true;
        }
    }

    public static void buildScv() {
        for(PlannedItem pi : StrategyController.getPlannedItems()) {
            if(pi.getSignificance() > 0 && pi.getSupply() <= GameMethods.getSupplyUsed() && pi.getPlannedItemStatus() == PlannedItemStatus.NOT_STARTED) {

            }
            else {
                if(Vlasile.getFrameCount() % 30 == 0) {
                    if(scvCap()) {
                        for(Unit myUnit : Vlasile.getSelf().getUnits()) {
                            if(myUnit.getType() == UnitType.Terran_Command_Center && Vlasile.getSelf().minerals() >= 50 && !myUnit.isTraining() && GameMethods.getAvailableMinerals() >=50) {
                                myUnit.train(UnitType.Terran_SCV);
                            }
                        }
                    }
                }
            }
        }
    }
}
