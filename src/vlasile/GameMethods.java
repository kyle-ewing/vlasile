package vlasile;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.enemy.EnemyUnitInfo;

import java.util.Iterator;
import java.util.Map;

public class GameMethods {
    private static int reservedMinerals = 0;
    private static int reservedGas = 0;
    private static int availableMinerals;
    private static int availableGas;
    private static boolean isReserved = false;

    public GameMethods() {
    }

    public static int getSeconds() {
        return Vlasile.getFrameCount() / 30;
    }

    public static int getSupplyUsed() {
       return Vlasile.getBwapi().self().supplyUsed() / 2;
    }

    public static int getSuppyTotal() {
        return Vlasile.getBwapi().self().supplyTotal() / 2;
    }

    public static int getFreeSupply() {
        return getSuppyTotal() - getSupplyUsed();
    }

    public static void reserveResources(UnitType unitType) {
        reservedMinerals += unitType.mineralPrice();
        reservedGas += unitType.gasPrice();
        availableMinerals = Vlasile.getSelf().minerals() - reservedMinerals;
        availableGas = Vlasile.getSelf().gas() - reservedGas;
        isReserved = true;
    }

    public static void unreserveResources(UnitType unitType) {
        reservedMinerals -= unitType.mineralPrice();
        reservedGas -= unitType.gasPrice();
        availableMinerals = Vlasile.getSelf().minerals() - reservedMinerals;
        availableGas = Vlasile.getSelf().gas() - reservedGas;
        isReserved = false;
    }

    public static TilePosition getBuildTile(Unit builder, UnitType buildingType, TilePosition aroundTile) {
        TilePosition ret = null;
        int maxDist = 3;
        int stopDist = 40;

        // Refinery, Assimilator, Extractor
        if (buildingType.isRefinery()) {

            for (Unit n : Vlasile.getBwapi().neutral().getUnits()) {
                if ((n.getType() == UnitType.Resource_Vespene_Geyser)
                        && (Math.abs(n.getTilePosition().getX() - aroundTile.getX()) < stopDist)
                        && (Math.abs(n.getTilePosition().getY() - aroundTile.getY()) < stopDist) && n.isVisible())
                    return n.getTilePosition();
            }
        }

        while ((maxDist < stopDist) && (ret == null)) {
            for (int i = aroundTile.getX() - maxDist; i <= aroundTile.getX() + maxDist; i++) {
                for (int j = aroundTile.getY() - maxDist; j <= aroundTile.getY() + maxDist; j++) {
                    if (Vlasile.getBwapi().canBuildHere(new TilePosition(i, j), buildingType, builder, false)) {
                        // units that are blocking the tile
                        boolean unitsInWay = false;
                        for (Unit u : Vlasile.getBwapi().getAllUnits()) {
                            if (u.getID() == builder.getID())
                                continue;
                            if ((Math.abs(u.getTilePosition().getX() - i) < 4)
                                    && (Math.abs(u.getTilePosition().getY() - j) < 4))
                                unitsInWay = true;
                        }
                        if (!unitsInWay) {
                            return new TilePosition(i, j);
                        }
                    }
                }
            }
            maxDist += 2;
        }

        if (ret == null)
            Vlasile.getBwapi().printf("Unable to find suitable build position for " + buildingType.toString());
        return ret;
    }

    public static int countUnitsOfType(UnitType unitType) {
        int total = 0;

        Iterator<EnemyUnitInfo> itr = UnitCount.getEnemyUnitCount().values().iterator();
        while(itr.hasNext()) {
            EnemyUnitInfo enemyUnitInfo = itr.next();
            if(enemyUnitInfo.getUnitType() == unitType) {
                total++;
            }
        }
        return total;
    }

    public static void updateUnitType(Unit unit) {

        for(Map.Entry<Integer, EnemyUnitInfo> u : UnitCount.getEnemyUnitCount().entrySet()) {
            if(u.getKey() == unit.getID()) {
                if(u.getValue().getUnitType() != unit.getType()) {
                    System.out.println("Changed " + u.getValue().getUnitType() + " to " + unit.getType());
                    u.getValue().setUnitType(unit.getType());
                }
            }
        }
    }

    public static Position translatePositionByPixels(Position position, int X, int Y) {
        return new Position(position.getX() + X, position.getY() + Y);
    }

    public static int getReservedMinerals() {
        return reservedMinerals;
    }

    public static void setReservedMinerals(int reservedMinerals) {
        GameMethods.reservedMinerals = reservedMinerals;
    }

    public static int getReservedGas() {
        return reservedGas;
    }

    public static void setReservedGas(int reservedGas) {
        GameMethods.reservedGas = reservedGas;
    }

    public static int getAvailableMinerals() {
        return availableMinerals = Vlasile.getSelf().minerals() - reservedMinerals;
    }

    public static int getAvailableGas() {
        return availableGas = Vlasile.getSelf().gas() - reservedGas;
    }

    public static boolean isIsReserved() {
        return isReserved;
    }
}
