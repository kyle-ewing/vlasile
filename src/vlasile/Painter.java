package vlasile;

import bwapi.Color;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.scouting.ScvScout;


public class Painter {

    public static void paintCircle(Unit unit, int radius, Color color) {
        paintCircle(unit.getPosition(), radius, color);
    }

    private static void paintCircle(Position position, int radius, Color color) {
        if(position == null) {
            return;
        }
        Vlasile.getBwapi().drawCircleMap(position, radius, color, false);
    }

    public static void paintFilledCircle(Unit unit, int radius, Color color) {
        if(unit.canAttack()) {
            paintFilledCircle(unit.getPosition(), radius, color);
        }
    }

    private static void paintFilledCircle(Position position, int radius, Color color) {
        if(position == null) {
            return;
        }
        Vlasile.getBwapi().drawCircleMap(position, radius, color, true);
    }

    public static void paintRectangle(Unit unit, int width, int height, Color color) {
        if(unit.canMove()) {
            paintRectangle(unit.getPosition(), width, height, color);
        }
    }

    private static void paintRectangle(Position position, int width, int height, Color color) {
        Vlasile.getBwapi().drawBoxMap(position, GameMethods.translatePositionByPixels(position, width, height), color);
    }

    public static void drawUnitText(Position position, String string) {
        if(position == null) {
            return;
        }
        Vlasile.getBwapi().drawTextMap(position, string);
    }

    public static void paintWorkers() {
        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.getType().isWorker() && unit.isGatheringMinerals()) {
                paintCircle(unit, 8, Color.Blue);
            }
        }
    }

    public static void paintScout() {
        if(ScvScout.getScout() != null) {
            paintCircle(ScvScout.getScout(), 8, Color.Orange);
        }
    }

    public static void drawBuilderText() {
        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.getType().isWorker() && unit.isConstructing()) {
                drawUnitText(unit.getPosition(), "Builder");
            }
        }
    }
}
