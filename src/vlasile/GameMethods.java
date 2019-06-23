package vlasile;

import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import javafx.scene.shape.VLineTo;

public class GameMethods {
    private static int reservedMinerals = 0;
    private static int reservedGas = 0;
    private static int availableMinerals;
    private static int availableGas;

    public GameMethods() {
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
    }

    public static void unreserveResources(UnitType unitType) {
        reservedMinerals -= unitType.mineralPrice();
        reservedGas -= unitType.gasPrice();
        availableMinerals = Vlasile.getSelf().minerals() - reservedMinerals;
        availableGas = Vlasile.getSelf().gas() - reservedGas;
    }

    public static TilePosition getBuildTile(Unit builder, UnitType buildingType, TilePosition aroundTile) {
        TilePosition ret = null;
        int maxDist = 5;
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

}
