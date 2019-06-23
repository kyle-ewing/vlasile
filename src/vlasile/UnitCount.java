package vlasile;

import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.HashMap;

public class UnitCount {
    private static HashMap<UnitType, Integer> unitCount = new HashMap<>();;

//    public static void initialFriendlyUnits() {
//        for(Unit unit : self.getUnits()) {
//            addFriendlyUnit(unit);
//        }
//    }

    public static void addFriendlyUnit(Unit unit) {
        if(!unitCount.containsKey(unit.getType())) {
            unitCount.put(unit.getType(), 1);
        }
        else {
            unitCount.put(unit.getType(), unitCount.get(unit.getType()) +1);
        }
    }

    public static void removeFriendlyUnit(Unit unit) {
        if(unitCount.get(unit.getType()) == 1) {
            unitCount.remove(unit.getType());
        }
        else {
            unitCount.put(unit.getType(), unitCount.get((unit.getType())) -1);
        }
    }

    public static HashMap<UnitType, Integer> getUnitCount() {
        return unitCount;
    }
}
