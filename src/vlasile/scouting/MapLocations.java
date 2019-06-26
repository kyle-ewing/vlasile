package vlasile.scouting;

import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BWTA;
import bwta.BaseLocation;
import vlasile.Vlasile;

import java.util.ArrayList;
import java.util.List;

public class MapLocations {
    private static Unit mainBase;

    public static BaseLocation getUnexploredStartingLocation() {
        ArrayList<BaseLocation> startingLocation = new ArrayList<>();
        startingLocation.addAll(getStartingLocations());

        for(BaseLocation baseLocation : startingLocation) {
            if(!isExplored(baseLocation.getPosition())) {
                return baseLocation;
            }
        }
        return null;
    }

    public static List<BaseLocation> getStartingLocations() {
        ArrayList<BaseLocation> startingLocations = new ArrayList<>();
        for(BaseLocation baseLocation : BWTA.getBaseLocations()) {
            if(baseLocation.isStartLocation()) {
//                if(distanceToBase(getMainBase(), baseLocation.getPosition()) <= 10) {
//                	System.out.println("hits this");
//                    continue;
//                }
                startingLocations.add(baseLocation);
            }
        }
        return startingLocations;
    }

    private static double distanceToBase(Unit base1, Object base2) {
    	System.out.println("sdfsdfsdf");
        Unit fromUnit = (Unit) base1;
        Unit toUnit = (Unit) base2;
        System.out.println("Distance to base: " + fromUnit.getDistance(toUnit) / 32);
        return fromUnit.getDistance(toUnit) / 32;
    }

    private static boolean isExplored(Position position) {
        return Vlasile.getBwapi().isExplored(position.toTilePosition());
    }

    private static void setMainBase() {
        if(mainBase == null) {
            for(Unit u : Vlasile.getSelf().getUnits()) {
                if(u.getType() == UnitType.Terran_Command_Center) {
                    mainBase = u;
                }
            }
        }
    }

    public static Unit getMainBase() {
        if(mainBase == null) {
            setMainBase();
        }
        return mainBase;
    }
}
