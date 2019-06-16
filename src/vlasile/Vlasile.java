package vlasile;

import bwapi.*;
import bwta.BWTA;
import vlasile.buildorder.EnemyInformation;
import vlasile.buildorder.StrategyController;

public class Vlasile extends DefaultBWListener {

    private static Vlasile instance;

    private static Mirror mirror = new Mirror();

    private static Game bwapi;
    private static Player self;

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
        bwapi.setLocalSpeed(50);

    }

    @Override
    public void onFrame() {
        StrategyController.CurrentStrat();
    }

    @Override
    public void onUnitCreate(Unit unit) {
        //System.out.println("New unit created: " + unit.getType());;
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