package vlasile.production;

import bwapi.Game;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.enemy.EnemyStrategy;
import vlasile.enemy.StrategyController;

import java.util.ArrayList;

//Class containing any methods involving building creation that don't originate in the initial build order

public class UnplannedBuildings {
    private static int freeSupply;
    private static int totalSupply;
    private static boolean initializeResponse = false;
    private static ArrayList<PlannedItem> plannedItems = StrategyController.getPlannedItems();
    private static boolean depotBlock = false;

    public static void update() {
        addDepotToQueue();
        supplyBlocked();

        if(!initializeResponse) {
            earlyGameResponse();
        }
    }

    //Queue additional supply depots when there are none currently in the build queue, and also a certain supply threshold is met
    private static void addDepotToQueue() {
        totalSupply = GameMethods.getSuppyTotal();

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
        depotBlock = false;
        return false;
    }

    private static void supplyBlocked() {
        freeSupply = GameMethods.getFreeSupply();

        if(freeSupply < 0 && (!depotBlock)) {
            plannedItems.add( new PlannedItem(UnitType.Terran_Supply_Depot, 0, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
            depotBlock = true;
            System.out.println("Supply Block Detected. Adding Supply Depot to Queue");
        }
    }

    //Add a bunker to built as soon as possible if the enemy is 4 pooling
    private static void earlyGameResponse() {
            if(EnemyStrategy.getEnemyStrategy() != null) {
                if(EnemyStrategy.getEnemyStrategy().getName().equals("4 Pool")) {
                  plannedItems.add(new PlannedItem(UnitType.Terran_Bunker, 0, PlannedItemStatus.NOT_STARTED, PlannedItemType.BUILDING));
                  System.out.println(UnitType.Terran_Bunker + " added to build plan");
                }
                initializeResponse = true;
            }

    }
}
