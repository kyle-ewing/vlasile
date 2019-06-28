package vlasile;

import bwapi.Color;
import bwapi.Position;
import bwapi.Unit;


public class Painter {

    public static void paintCircle(Unit unit, int radius, Color color) {
        paintCircle(unit.getPosition(), radius, color);
    }

    public static void paintCircle(Position position, int radius, Color color) {
        if(position == null) {
            return;
        }
        Vlasile.getBwapi().drawCircleMap(position, radius, color, false);
    }

    public static void paintWorkers() {
        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.getType().isWorker() && unit.isGatheringMinerals()) {
                paintCircle(unit, 8, Color.Blue);
            }
        }
    }
}
