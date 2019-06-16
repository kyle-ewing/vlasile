package vlasile;

import bwapi.Player;

public class GameMethods {

    private static Player _enemy = null;

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

    public static Player enemy() {
        if(_enemy == null) {
            _enemy = Vlasile.getBwapi().enemies().iterator().next();
        }
        return _enemy;
    }
}
