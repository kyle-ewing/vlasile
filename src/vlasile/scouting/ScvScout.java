package vlasile.scouting;

import bwapi.Unit;
import bwta.BaseLocation;
import vlasile.Vlasile;
import vlasile.enemy.EnemyInformation;

public class ScvScout {
    private static Unit scout;

    public static void update() {
        if(scout == null) {
            assignScout();
        }


        findEnemyBase(scout);

    }

    public static void findEnemyBase(Unit scout) {
        BaseLocation startingLocation = MapLocations.getUnexploredStartingLocation();

        if(scout.isMoving() || scout.isAttacking()) {
            return;
        }

        if(startingLocation != null) {
            scout.move(startingLocation.getPosition());
            return;
        }
    }

    public static Unit assignScout() {
        for(Unit u : Vlasile.getSelf().getUnits()) {
            if(u.getType().isWorker() && u.isGatheringMinerals()) {
                scout = u;
                System.out.println("Scouting SCV assigned");
                return scout;
            }
        }
        return null;
    }


}
