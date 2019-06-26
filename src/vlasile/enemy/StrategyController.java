package vlasile.enemy;

import bwapi.TilePosition;
import bwapi.Unit;
import vlasile.GameMethods;
import vlasile.Vlasile;
import vlasile.buildorders.TwoRaxAllIn;
import vlasile.production.PlannedItem;
import vlasile.production.PlannedItemStatus;
import vlasile.production.PlannedItemType;
import vlasile.production.WorkerProduction;

import java.util.ArrayList;

public class StrategyController {

    private static Integer strategy = EnemyInformation.getStrategy();
    private static boolean isInitialized = false;
    private static ArrayList<PlannedItem> plannedItems = new ArrayList<>();

    public static void CurrentStrat() {
        executeBuild();
        WorkerProduction.buildScv();

        if (strategy == 0) {
            System.out.println("terran");
        }
        else if (strategy == 1) {
            System.out.println("protoss");
        }
        else if (strategy == 2) {
            TwoRaxAllIn.update();
        }
        else {
            System.out.println("random");
        }
    }

    private static void executeBuild() {
        if (isInitialized) {
            for (PlannedItem pi : plannedItems) {
                if (pi.getPlannedItemStatus() == PlannedItemStatus.NOT_STARTED) {
                    if (pi.getSupply() <= GameMethods.getSupplyUsed()) {
                        if (GameMethods.getAvailableMinerals() >= pi.getUnitType().mineralPrice()) {
                            if (pi.getPlannedItemType() == PlannedItemType.BUILDING) {
                                for (Unit worker : Vlasile.getSelf().getUnits()) {
                                    if (worker.getType().isWorker() && worker.isGatheringMinerals()) {
                                        GameMethods.reserveResources(pi.getUnitType());
                                        System.out.println(GameMethods.getReservedMinerals() + " minerals reserved, " + GameMethods.getAvailableMinerals() + " available");
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
                else if (pi.getPlannedItemStatus() == PlannedItemStatus.SCV_ASSIGNED) {
                    if (Vlasile.getNewestBuilding().getID() == pi.getID()) {
                        pi.setPlannedItemStatus(PlannedItemStatus.IN_PROGRESS);
                        GameMethods.unreserveResources(pi.getUnitType());
                        System.out.println(pi.getUnitType() + " in progress");
                    }
                }
                else if (pi.getPlannedItemStatus() == PlannedItemStatus.IN_PROGRESS) {
                    if (Vlasile.getNewestFinishedBuilding().getType() == pi.getUnitType()) {
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
            if (strategy == 0) {
                System.out.println("terran");
            }
            else if (strategy == 1) {
                System.out.println("protoss");
            }
            else if (strategy == 2) {
                TwoRaxAllIn.initializeBuild();
            }
            else {
                System.out.println("random");
            }
            isInitialized = true;
        }
    }

    public static ArrayList<PlannedItem> getPlannedItems() {
        return plannedItems;
    }
}
