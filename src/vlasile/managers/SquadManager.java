package vlasile.managers;

import bwapi.Unit;
import vlasile.MapLocations;
import vlasile.squads.Squad;

import java.util.ArrayList;

public class SquadManager {
    private static ArrayList<Squad> squads = new ArrayList<>();
    private static Squad groundArmy = new Squad("Ground");
    private static Squad airArmy = new Squad("Air");

    public static void combatUnitCreated(Unit unit) {
        if(unit.isFlying()) {
            airArmy.getSquadUnits().add(unit);
        }
        else {
            groundArmy.getSquadUnits().add(unit);
            unit.move(MapLocations.getClosestChoke());
        }
    }

    public static ArrayList<Squad> getSquads() {
        return squads;
    }

    public static Squad getGroundArmy() {
        return groundArmy;
    }

    public static Squad getAirArmy() {
        return airArmy;
    }
}
