package vlasile.buildorders;

import bwapi.UnitType;
import vlasile.UnitCount;
import vlasile.Vlasile;
import vlasile.enemy.StrategyController;
import vlasile.production.PlannedItem;
import vlasile.production.PlannedItemStatus;
import vlasile.production.PlannedItemType;
import vlasile.production.UnitProduction;

public class TwoRaxAllIn {
    public static void initializeBuild() {
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 9, 2, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Barracks, 10, 3,PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Barracks, 11, 3,PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 14, 2,PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 22, 2,PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        System.out.println("Two Rax All In initialized");
    }

    public static void update() {
        marines();
    }

    public static void marines() {
        UnitProduction.buildMarine();
    }

    private static boolean quota() {
        if(UnitCount.getUnitCount().get(UnitType.Terran_Marine) >= 8) {
            return true;
        }
        return false;
    }
}
