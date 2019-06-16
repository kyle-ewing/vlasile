package vlasile;

public class GameMethods {

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


}
