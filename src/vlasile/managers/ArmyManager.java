package vlasile.managers;

import bwapi.Color;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.Painter;
import vlasile.Vlasile;

public class ArmyManager {

    public static void update() {
        marineManager();
    }

    private static void marineManager() {
        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.getType() == UnitType.Terran_Marine && !unit.getType().isBuilding()) {
                if(unit.isIdle()) {
                    Painter.paintFilledCircle(unit, 4, Color.White);
                }
                else if(unit.isAttacking()) {
                    Painter.paintFilledCircle(unit, 4, Color.Red);
                }
                else if(unit.isMoving()) {
                    Painter.paintFilledCircle(unit, 4, Color.Purple);
                }
            }
        }
    }
}
