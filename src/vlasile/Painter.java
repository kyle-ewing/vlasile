package vlasile;

import bwapi.Color;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BWTA;
import bwta.BaseLocation;
import bwta.Chokepoint;
import vlasile.managers.SquadManager;
import vlasile.squads.Squad;
import vlasile.unitstatus.WorkerStatus;

import java.util.HashMap;

//Painter methods to help with debugging

public class Painter {
    static HashMap workers = UnitCount.getWorkers();

    public static void update() {
        paintWorkers();
        paintChoke();
        paintMarines();
        drawAttackLine();
        paintPatches();
    }


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

    private static void paintWorkers() {
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

    private static void paintMarines() {
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

    private static void paintChoke() {
        for(Chokepoint choke : BWTA.getChokepoints()) {
            paintCircle(choke.getCenter(), 48, Color.Cyan);
        }
    }

    private static void paintPatches() {
        for(BaseLocation base : BWTA.getBaseLocations()) {
            paintCircle(base.getPosition(), 36, Color.Purple);
        }
    }

    public static void drawBuilderText() {
        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.getType().isWorker() && unit.isConstructing()) {
                drawUnitText(unit.getPosition(), "Builder");
            }
        }
    }

    private static void drawAttackLine() {
        Squad squad = SquadManager.getBioArmy();
        if(!(squad.getSquadUnits().isEmpty())) {
            for(Unit unit : squad.getSquadUnits()) {
                if(SquadManager.getClosestUnit(unit).getPosition().isValid()) {
                    Vlasile.getBwapi().drawLineMap(unit.getPosition(), SquadManager.getClosestUnit(unit).getPosition(), Color.Teal);
                }
            }
        }

    }

    public static void drawBuilderID() {
        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.getType().isWorker()) {
                drawUnitText(unit.getPosition(), Integer.toString(unit.getID()));
            }
        }
    }
}
