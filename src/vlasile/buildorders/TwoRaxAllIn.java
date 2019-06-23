package vlasile.buildorders;

import bwapi.Game;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.Vlasile;
import vlasile.production.PlannedItem;
import vlasile.production.PlannedItemStatus;
import vlasile.production.PlannedItemType;

import java.util.ArrayList;

public class TwoRaxAllIn {
    private static ArrayList<PlannedItem> plannedItems = new ArrayList<>();
    private static boolean isInitialized = false;

    private static void initializeBuild() {
        plannedItems.add(new PlannedItem(UnitType.Terran_Supply_Depot, 9, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
        isInitialized = true;
        System.out.println("Two Rax All In initialized");
    }

    public static void executeBuild() {
        if(isInitialized) {
            for(PlannedItem pi : plannedItems) {
                if(pi.getPlannedItemStatus() == PlannedItemStatus.NOT_STARTED) {
                    if(pi.getSupply() <= GameMethods.getSupplyUsed()) {
                        if(GameMethods.getAvailableMinerals() >= pi.getUnitType().mineralPrice()) {
                            if(pi.getPlannedItemType() == PlannedItemType.BUILDING) {
                                    for(Unit worker : Vlasile.getSelf().getUnits()) {
                                        if(worker.getType().isWorker()) {
                                            GameMethods.reserveResources(pi.getUnitType());
                                            TilePosition plannedPosition = GameMethods.getBuildTile(worker, pi.getUnitType(), Vlasile.getSelf().getStartLocation());
                                            worker.build(pi.getUnitType(), plannedPosition);
                                            pi.setPlannedItemStatus(PlannedItemStatus.SCV_ASSIGNED);
                                            System.out.println("SCV assigned to build: " + pi.getUnitType());
                                            break;
                                        }
                                    }

                            }
                        }
                    }
                }
                else if(pi.getPlannedItemStatus() == PlannedItemStatus.SCV_ASSIGNED) {
                    if(Vlasile.getNewestBuilding().getType() == pi.getUnitType()) {
                        GameMethods.unreserveResources(pi.getUnitType());
                        pi.setPlannedItemStatus(PlannedItemStatus.IN_PROGRESS);
                        System.out.println(pi.getUnitType() + " in progress");
                    }
                }
                else if(pi.getPlannedItemStatus() == PlannedItemStatus.IN_PROGRESS) {
                    if(Vlasile.getNewestFinishedBuilding().getType() == pi.getUnitType()) {
                        pi.setPlannedItemStatus(PlannedItemStatus.COMPLETE);
                    }
                }
                else {
                    System.out.println("Removing: " + pi.getUnitType() + " from list");
                    plannedItems.remove(pi);
                }
            }
        }
        else {
            initializeBuild();
        }

    }
}
