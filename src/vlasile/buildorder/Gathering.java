package vlasile.buildorder;

import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.Vlasile;

public class Gathering {
    private static int freeSupply = GameMethods.getFreeSupply();
    private static Player self = Vlasile.getSelf();
    private static Game game = Vlasile.getBwapi();
    private static int scvCount;

    public static void update() {
        assignMining();
        buildScv();
    }

    private static boolean scvCap() {
        scvCount = 0;
        for(Unit myUnit : self.getUnits()) {
            if(myUnit.getType().isWorker()) {
                scvCount++;
            }
        }

        if(scvCount > 15) {
//            for(Unit myUnit : self.getUnits()) {
//                if(myUnit.getType() == UnitType.Terran_Command_Center) {
//                    myUnit.cancelTrain(4);
//                }
//            }
            return true;
        }
        else {
            return false;
        }

    }

    private static void assignMining() {
        for(Unit myUnit : self.getUnits()) {
            if(myUnit.getType().isWorker()) {

                if(myUnit.getType().isWorker() && myUnit.isIdle()) {
                    Unit mineralPatch = null;

                    for(Unit neutralUnit : game.neutral().getUnits()) {
                        if(neutralUnit.getType().isMineralField()) {
                            if(mineralPatch == null || myUnit.getDistance(neutralUnit) < myUnit.getDistance(mineralPatch)) {
                                mineralPatch = neutralUnit;
                            }
                        }
                    }

                    if(mineralPatch != null) {
                        myUnit.gather(mineralPatch, false);
                    }
                }
            }
        }
    }

    private static void buildScv() {
        if(!scvCap()) {
            for(Unit myUnit : self.getUnits()) {

                if(myUnit.getType() == UnitType.Terran_Command_Center && self.minerals() >= 50) {
                    myUnit.train(UnitType.Terran_SCV);
                }
            }
        }
    }
}
