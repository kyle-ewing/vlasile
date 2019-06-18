package vlasile;

import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.Vlasile;

import java.util.HashMap;

public class UnitCount {

    private static Player self = Vlasile.getSelf();
    private static HashMap<UnitType, Integer> unitCount;

    public static void getAllFriendlyUnits() {
        unitCount = new HashMap<>();
        for(Unit unit : self.getUnits()) {
            if(!unitCount.containsKey(unit.getType())) {
                unitCount.put(unit.getType(), 1);
            }
            else {
                unitCount.put(unit.getType(), unitCount.get(unit.getType())+1);
            }
        }
    }

    public static HashMap<UnitType, Integer> getUnitCount() {
        return unitCount;
    }
}
