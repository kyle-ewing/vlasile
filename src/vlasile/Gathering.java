package vlasile;

import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;

public class Gathering {
    private static Player self = Vlasile.getSelf();
    private static Game game = Vlasile.getBwapi();

    public static void assignMining() {
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
        Painter.paintWorkers();
    }

}
