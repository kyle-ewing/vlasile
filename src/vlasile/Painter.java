package vlasile;

import bwapi.Color;
import bwapi.Position;
import bwapi.Unit;
import bwta.BWTA;
import bwta.Chokepoint;
import vlasile.unitstatus.WorkerStatus;

import java.util.HashMap;


public class Painter {
    static HashMap workers = UnitCount.getWorkers();

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
            if (unit.getType().isWorker()) {
                if(workers.get(unit.getID()) == WorkerStatus.MINERALS && unit.canAttack()) {
                    paintCircle(unit, 8, Color.Blue);
                }
                else if(workers.get(unit.getID()) == WorkerStatus.SCOUT) {
                    paintCircle(unit, 8, Color.Orange);
                }
                else if(workers.get(unit.getID()) == WorkerStatus.BUILD) {
                    paintCircle(unit, 8, Color.Yellow);
                }
                else if(workers.get(unit.getID()) == WorkerStatus.GAS) {
                    paintCircle(unit, 8, Color.Green);
                }
                else if(workers.get(unit.getID()) == WorkerStatus.ATTACK) {
                    paintCircle(unit, 8, Color.Red);
                }
                else if(workers.get(unit.getID()) == WorkerStatus.REPAIR) {
                    paintCircle(unit, 8, Color.Grey);
                }
            }

        }
    }

    public static void paintChoke() {
        for(Chokepoint choke : BWTA.getChokepoints()) {
            paintCircle(choke.getCenter(), 48, Color.Cyan);
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
