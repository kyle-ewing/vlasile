package vlasile;

import bwapi.AbstractPoint;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BWTA;
import bwta.BaseLocation;
import bwta.Chokepoint;

import java.util.ArrayList;
import java.util.List;

public class MapLocations {
    private static Unit mainBase;
    private static Chokepoint closestChoke = null;

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

    private static List<BaseLocation> getStartingLocations() {
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

    private static Position getMainBaseLocation() {
        if(mainBase == null) {
            setMainBase();
        }
        return mainBase.getPosition();
    }

    public static Position getClosestChoke() {
        return BWTA.getNearestChokepoint(getMainBaseLocation()).getCenter();
    }

    public static Position rallyNearMainChoke() {
        Chokepoint closestChoke = BWTA.getNearestChokepoint(getMainBaseLocation());
        Position rallyPoint = new Position((closestChoke.getCenter().getX() + getMainBaseLocation().getX()) / 2, (closestChoke.getCenter().getY() + getMainBaseLocation().getY()) / 2);
        return rallyPoint;
    }

//    public static Position translatePositionByPercent(Position from, Position to, double percent) {
//        int newX = (int) ((100 - percent) * from.getX() + percent * to.getX()) / 100;
//        int newY = (int) ((100 - percent) * from.getY() + percent * to.getY()) / 100;
//        return new Position(newX, newY);
//    }

    public static Position bunkerPosition() {
        Chokepoint closestChoke = BWTA.getNearestChokepoint(getMainBaseLocation());
        int posX = (((closestChoke.getCenter().getX() + getMainBaseLocation().getX()) / 2) + closestChoke.getCenter().getX()) / 2;
        //posX = (closestChoke.getCenter().getX() + posX) / 2;
        int posY =  (((closestChoke.getCenter().getY() + getMainBaseLocation().getY()) / 2) + closestChoke.getCenter().getY()) / 2;
        //posY =  (closestChoke.getCenter().getY() + posY) / 2;
        return new Position(posX, posY);
    }

//    public static Chokepoint getClosestChoke(Position position) {
//        double dist = Double.MAX_VALUE;
//
//        for(Chokepoint base : BWTA.getChokepoints()) {
//            double closetDist = position.getApproxDistance(base.getCenter().getPoint());
//            System.out.println(closetDist);
//            if(closestChoke == null || closetDist < dist) {
//                closestChoke = base;
//                dist = closetDist;
//            }
//        }
//        return closestChoke;
//    }
}
