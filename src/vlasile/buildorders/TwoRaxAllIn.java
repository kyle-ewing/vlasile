package vlasile.buildorders;

import bwapi.Game;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.Vlasile;
import vlasile.enemy.StrategyController;
import vlasile.production.PlannedItem;
import vlasile.production.PlannedItemStatus;
import vlasile.production.PlannedItemType;

import java.util.ArrayList;

public class TwoRaxAllIn {
    public static void initializeBuild() {
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 9, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Barracks, 10, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Barracks, 11, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 14, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 22, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        System.out.println("Two Rax All In initialized");
    }
}
