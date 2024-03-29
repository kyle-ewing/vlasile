package vlasile.enemy;

import bwapi.TilePosition;
import bwapi.Unit;
import vlasile.GameMethods;
import vlasile.Painter;
import vlasile.UnitCount;
import vlasile.Vlasile;
import vlasile.buildorders.TwoRaxAllIn;
import vlasile.managers.SquadManager;
import vlasile.production.*;
import vlasile.scouting.ScvScout;
import vlasile.unitstatus.WorkerStatus;

import java.util.ArrayList;

//Class that controls what strategy is going to be used, and implementing the build order within that strategy

public class StrategyController {

    private static Integer race = EnemyInformation.getRace();
    private static boolean isInitialized = false;
    private static ArrayList<PlannedItem> plannedItems = new ArrayList<>();

    //Primary method for running all production and strategy logic
    public static void CurrentStrat() {
        executeBuild();
        WorkerProduction.buildScv();

        //Currently only have a strategy for zerg
        if (race == 0) {
            System.out.println("terran");
        }
        else if (race == 1) {
            System.out.println("protoss");
        }
        else if (race == 2) {
            TwoRaxAllIn.update();
            defineEnemyZergStrategy();
            SquadManager.squadTactics(SquadManager.getBioArmy());
        }
        else {
            System.out.println("random");
        }
        UnplannedBuildings.update();
    }

    //Executes build order contained in the plannedItems list based on enum state
    //***move this to a class that makes sense
    private static void executeBuild() {
        if (isInitialized) {
            for (PlannedItem pi : plannedItems) {
                if (pi.getPlannedItemStatus() == PlannedItemStatus.NOT_STARTED) {
                    if (pi.getSupply() <= GameMethods.getSupplyUsed()) {
                        if (GameMethods.getAvailableMinerals() >= pi.getUnitType().mineralPrice()) {
                            if (pi.getPlannedItemType() == PlannedItemType.BUILDING) {
                                if(BuildingRequirements.meetsReq(pi.getUnitType())) {
                                    for (Unit worker : Vlasile.getSelf().getUnits()) {
                                        if (worker.getType().isWorker() && worker.isGatheringMinerals() && ScvScout.getScout() != worker) {
                                            GameMethods.reserveResources(pi.getUnitType());
                                            System.out.println(GameMethods.getReservedMinerals() + " minerals reserved, " + GameMethods.getAvailableMinerals() + " available");
                                            TilePosition plannedPosition = GameMethods.getBuildTile(worker, pi.getUnitType(), Vlasile.getSelf().getStartLocation());
                                            worker.build(pi.getUnitType(), plannedPosition);
                                            UnitCount.getWorkers().put(worker.getID(), WorkerStatus.BUILD);
                                            pi.setPlannedItemStatus(PlannedItemStatus.SCV_ASSIGNED);
                                            System.out.println("SCV assigned to build: " + pi.getUnitType());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else if (pi.getPlannedItemStatus() == PlannedItemStatus.SCV_ASSIGNED) {
                    Boolean progressStarted = null;

                    if(pi.getUnitType() == Vlasile.getNewestBuilding().getType()) {
                        if (Vlasile.getNewestBuilding().getID() == pi.getID()) {
                            progressStarted = true;
                            pi.setPlannedItemStatus(PlannedItemStatus.IN_PROGRESS);
                            GameMethods.unreserveResources(pi.getUnitType());
                            System.out.println(pi.getUnitType() + " in progress");
                        }
                    }

                    if(Vlasile.getFrameCount() % 480 == 0 && (!progressStarted)) {
                        pi.setPlannedItemStatus(PlannedItemStatus.NOT_STARTED);
                        System.out.println("Planned item timeout, resetting status");
                    }
                    else if (Vlasile.getFrameCount() % 480 == 0 && (progressStarted == null)) {
                        progressStarted = false;
                    }

                }
                else if (pi.getPlannedItemStatus() == PlannedItemStatus.IN_PROGRESS) {
                    if (Vlasile.getNewestFinishedBuilding().getType() == pi.getUnitType() && Vlasile.getNewestFinishedBuilding().getID() == pi.getID()) {
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
            if (race == 0) {
                System.out.println("terran");
            }
            else if (race == 1) {
                System.out.println("protoss");
            }
            else if (race == 2) {
                TwoRaxAllIn.initializeBuild();
                EnemyZergStrategy.initialize();
            }
            else {
                System.out.println("random");
            }
            isInitialized = true;
        }
        Painter.drawBuilderText();
    }

    private static void defineEnemyZergStrategy() {
        EnemyStrategy definedStrat = EnemyZergStrategy.detectStrategy();
        if(definedStrat != null) {
            changeEnemyStrategy(definedStrat);
        }
    }

    //Send text in game when the enemy strategy is found (4 pool only currently)
    private static void changeEnemyStrategy(EnemyStrategy strat) {
        if(!EnemyStrategy.isEnemyStratKnown()) {
            Vlasile.getBwapi().sendText("Enemy strategy: " + strat);
        }
        EnemyStrategy.setEnemyStrategy(strat);
    }

    public static ArrayList<PlannedItem> getPlannedItems() {
        return plannedItems;
    }
}
