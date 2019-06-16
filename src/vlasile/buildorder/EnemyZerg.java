package vlasile.buildorder;

import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.Vlasile;

public class EnemyZerg {
    private static Game game = Vlasile.getBwapi();
    private static Player self = Vlasile.getSelf();

    public static void TwoRaxFE() {
        //game.setTextSize(10);
        System.out.println(self.getUnits());
        game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace());

        StringBuilder units = new StringBuilder("My units:\n");

        //iterate through my units
        for (Unit myUnit : self.getUnits()) {
            units.append(myUnit.getType()).append(" ").append(myUnit.getTilePosition()).append("\n");

            //if there's enough minerals, train an SCV
            if (myUnit.getType() == UnitType.Terran_Command_Center && self.minerals() >= 50) {
                myUnit.train(UnitType.Terran_SCV);
            }

            //if it's a worker and it's idle, send it to the closest mineral patch
            if (myUnit.getType().isWorker() && myUnit.isIdle()) {
                Unit closestMineral = null;

                //find the closest mineral
                for (Unit neutralUnit : game.neutral().getUnits()) {
                    if (neutralUnit.getType().isMineralField()) {
                        if (closestMineral == null || myUnit.getDistance(neutralUnit) < myUnit.getDistance(closestMineral)) {
                            closestMineral = neutralUnit;
                        }
                    }
                }

                //if a mineral patch was found, send the worker to gather it
                if (closestMineral != null) {
                    myUnit.gather(closestMineral, false);
                }
            }
        }


        //draw my units on screen
        game.drawTextScreen(10, 25, units.toString());
    }

}
