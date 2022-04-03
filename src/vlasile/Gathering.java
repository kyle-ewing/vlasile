package vlasile;

import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BWTA;
import bwta.BaseLocation;
import vlasile.unitstatus.WorkerStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class Gathering {
    private static Player self = Vlasile.getSelf();
    private static Game game = Vlasile.getBwapi();
    private static HashMap<Integer, HashMap<Unit, ArrayList<WorkerStatus>>> mineralFields = new HashMap<>();

    //Find the closest mineral patch the scv and mine it
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
    }

}
