package vlasile;

import bwapi.*;
import bwta.BWTA;
import vlasile.enemy.EnemyInformation;
import vlasile.enemy.StrategyController;

public class Vlasile extends DefaultBWListener {

    private static Vlasile instance;

    private static Mirror mirror = new Mirror();

    private static Game bwapi;
    private static Player self;
    private int frameCount = 0;

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
        bwapi.setLocalSpeed(15);

    }

    @Override
    public void onFrame() {
        frameCount++;

        Gathering.assignMining();
        StrategyController.CurrentStrat();
    }

    @Override
    public void onUnitComplete(Unit unit) {

        //Enemy units will be counted if opponent is Terran
        if(unit.getType().getRace().toString().equals("Terran") && (!unit.getType().isSpecialBuilding())) {
            System.out.println(unit.getType());
            UnitCount.addFriendlyUnit(unit);
        }

    }

    @Override
    public void onUnitDestroy(Unit unit) {
        if(unit.getType().getRace().toString().equals("Terran") && (!unit.getType().isSpecialBuilding())) {
            System.out.println("Removed: " + unit.getType());
            UnitCount.removeFriendlyUnit(unit);
        }
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
}
