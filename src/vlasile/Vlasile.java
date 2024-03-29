package vlasile;

import bwapi.*;
import bwta.BWTA;
import vlasile.enemy.EnemyInformation;
import vlasile.enemy.StrategyController;
import vlasile.managers.SquadManager;
import vlasile.managers.WorkerManager;
import vlasile.production.PlannedItem;


public class Vlasile extends DefaultBWListener {

    private static Vlasile instance;

    private static Mirror mirror = new Mirror();

    private static Game bwapi;
    private static Player self;
    private static int frameCount = 0;
    private static Unit newestBuilding;
    private static Unit newestFinishedBuilding;

    @Override
    public void onStart() {
        bwapi = mirror.getGame();
        self = bwapi.self();

        System.out.print("Analyzing map... ");
        BWTA.readMap();
        BWTA.analyze();
        System.out.println("Map data ready");

        EnemyInformation.getEnemyRace();

        //allow user input
        bwapi.enableFlag(1);
        bwapi.setLocalSpeed(5);
    }

    @Override
    public void onFrame() {
        frameCount++;
        bwapi.drawTextScreen(10,10, "Seconds passed: " + GameMethods.getSeconds());
        bwapi.drawTextScreen(10,25, "Frame count: " + frameCount);
        bwapi.drawTextScreen(10,40, "SCV Count: " + UnitCount.getUnitCount().get(UnitType.Terran_SCV));


        if(UnitCount.getUnitCount().containsKey(UnitType.Terran_Marine)) {
            bwapi.drawTextScreen(10,55, "Marine Count: " + UnitCount.getUnitCount().get(UnitType.Terran_Marine));
        }
        StrategyController.CurrentStrat();
        WorkerManager.update();
        Gathering.assignMining();
        Painter.update();
    }

    @Override
    public void onUnitCreate(Unit unit) {
        if(unit.getType().isBuilding() && unit.getPlayer() == self) {
            newestBuilding = unit;

            for(PlannedItem pi : StrategyController.getPlannedItems()) {
                if(pi.getUnitType() == unit.getType()) {
                    if(pi.getID() == 0) {
                        pi.setID(unit.getID());
                        break;
                    }
                }
            }

            System.out.println("Newest building: " + newestBuilding.getType() + ". Building ID: " + newestBuilding.getID());
        }

        if(unit.getPlayer() != self) {
            if(!unit.getType().isNeutral()) {
                UnitCount.addEnemyUnit(unit);
            }
        }
    }

    @Override
    public void onUnitComplete(Unit unit) {
        if(unit.getPlayer() == self) {
            //System.out.println("Finished Units: " + unit.getType());
            UnitCount.addFriendlyUnit(unit);
            if(unit.getType().isWorker()) {
                UnitCount.addWorker(unit);
            }
            else if(!unit.getType().isBuilding()) {
                SquadManager.combatUnitCreated(unit);
                System.out.println(unit.getType() + " added to squad");
            }
        }
        if(unit.getPlayer() == self && unit.getType().isBuilding()) {
            newestFinishedBuilding = unit;

        }
        if(unit.getPlayer() != self) {
            if(!unit.getType().isNeutral()) {
                UnitCount.addEnemyUnit(unit);
            }
        }
    }

    @Override
    public void onUnitDestroy(Unit unit) {
        if(unit.getPlayer() == self) {
            System.out.println("Removed: " + unit.getType());
            UnitCount.removeFriendlyUnit(unit);
            if(unit.getType().isWorker()) {
                UnitCount.removeWorker(unit);
            }
            SquadManager.combatUnitRemoved(unit);
        }
        else {
            if(unit.getType().isBuilding()) {
                UnitCount.removeEnemyBuilding(unit);
            }
            UnitCount.removeEnemyUnit(unit);
        }
    }

    @Override
    public void onUnitShow(Unit unit) {
        if(unit.getPlayer() != self) {
            if(!unit.getType().isNeutral()) {
                UnitCount.addEnemyUnit(unit);
                GameMethods.updateUnitType(unit);
                if(unit.getType().isBuilding()) {
                    UnitCount.addEnemyBuilding(unit);
                }

            }
        }

    }

    @Override
    public void onUnitMorph(Unit unit) {
        if(unit.getPlayer() != self) {
            UnitCount.addEnemyUnit(unit);
            if(unit.getType().isBuilding()) {
                UnitCount.addEnemyBuilding(unit);
            }
        }
        GameMethods.updateUnitType(unit);
    }

    public static Vlasile getInstance() {
        if(instance == null) {
            instance = new Vlasile();
        }
        return instance;
    }

    public void run() {
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

    public static Game getBwapi() {
        return getInstance().bwapi;
    }

    public static Player getSelf() {
        return self;
    }

    public static Unit getNewestBuilding() {
        return newestBuilding;
    }

    public static Unit getNewestFinishedBuilding() {
        return newestFinishedBuilding;
    }

    public static int getFrameCount() {
        return frameCount;
    }
}
