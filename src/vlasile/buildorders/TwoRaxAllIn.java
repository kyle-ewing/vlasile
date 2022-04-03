package vlasile.buildorders;

import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.UnitCount;
import vlasile.enemy.StrategyController;
import vlasile.managers.SquadManager;
import vlasile.production.PlannedItem;
import vlasile.production.PlannedItemStatus;
import vlasile.production.PlannedItemType;
import vlasile.production.UnitProduction;
import vlasile.scouting.ScvScout;
import vlasile.squads.SquadStatus;

public class TwoRaxAllIn {
    private static UnitProduction uP = new UnitProduction();
    private static boolean hasScout = false;
    private static boolean hasReachedQuota = false;

    public static void initializeBuild() {
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 9, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Barracks, 10,PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Barracks, 11,PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 14,PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        StrategyController.getPlannedItems().add(new PlannedItem(UnitType.Terran_Supply_Depot, 22,PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        System.out.println("Two Rax All In initialized");
    }

    public static void update() {
        marines();

        if(!hasScout && GameMethods.getSupplyUsed() >= 10) {
            ScvScout.update();
        }
        if(quota()) {
            hasReachedQuota = true;
        }

        if(hasReachedQuota) {
            if(quota()) {
                SquadManager.getBioArmy().setStatus(SquadStatus.ATTACKING);
            }
//            else {
//                SquadManager.getBioArmy().setStatus(SquadStatus.RETREATING);
//            }
        }
    }

    private static void marines() {
        uP.buildMarine();
    }

    private static boolean quota() {
        if(UnitCount.getUnitCount().containsKey(UnitType.Terran_Marine)) {
            if(UnitCount.getUnitCount().get(UnitType.Terran_Marine) >= 16) {
                return true;
            }
        }
        return false;
    }
}
