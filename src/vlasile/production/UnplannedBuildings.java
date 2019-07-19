package vlasile.production;

import bwapi.Game;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.enemy.StrategyController;

import java.util.ArrayList;

public class UnplannedBuildings {
    private static int freeSupply;
    private static int totalSupply;

    public static void addDepotToQueue() {
        totalSupply = GameMethods.getSuppyTotal();
        ArrayList<PlannedItem> plannedItems = StrategyController.getPlannedItems();

        if(!depotInQueue()) {
            freeSupply = GameMethods.getFreeSupply();

            if(totalSupply <= 11) {
                if(freeSupply <= 2) {
                    plannedItems.add( new PlannedItem(UnitType.Terran_Supply_Depot, 0, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
                }
            }
            else if(totalSupply <= 20) {
                if(freeSupply <=4) {
                    plannedItems.add( new PlannedItem(UnitType.Terran_Supply_Depot, 0, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
                }
            }
            else if(totalSupply <=40) {
                if(freeSupply <= 7) {
                    plannedItems.add( new PlannedItem(UnitType.Terran_Supply_Depot, 0, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
                }
            }
            else if(totalSupply <= 100) {
                if(freeSupply <= 10) {
                    plannedItems.add( new PlannedItem(UnitType.Terran_Supply_Depot, 0, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
                }
            }
            else if(totalSupply <= 200) {
                if(freeSupply <= 14) {
                    plannedItems.add( new PlannedItem(UnitType.Terran_Supply_Depot, 0, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
                }
            }
        }
    }

    private static boolean depotInQueue() {
        for(PlannedItem pi : StrategyController.getPlannedItems()) {
            if(pi.getUnitType() == UnitType.Terran_Supply_Depot) {
                return true;
            }
        }
        return false;
    }
}
