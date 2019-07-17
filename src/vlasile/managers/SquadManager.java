package vlasile.managers;

import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.GameMethods;
import vlasile.MapLocations;
import vlasile.UnitCount;
import vlasile.Vlasile;
import vlasile.enemy.EnemyBuildingInfo;
import vlasile.enemy.EnemyInformation;
import vlasile.enemy.EnemyUnitInfo;
import vlasile.squads.Squad;
import vlasile.squads.SquadStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class SquadManager {
    private static ArrayList<Squad> squads = new ArrayList<>();
    private static Squad bioArmy = new Squad("Bio");
    private static Squad mechArmy = new Squad("Mech");
    private static Squad airArmy = new Squad("Air");

    public static void combatUnitCreated(Unit unit) {
        if(unit.getType().isFlyer()) {
            airArmy.getSquadUnits().add(unit);
        }
        else if(unit.getType().isMechanical() && !unit.getType().isFlyer()) {
            mechArmy.getSquadUnits().add(unit);
        }
        else {
            bioArmy.getSquadUnits().add(unit);
            unit.move(MapLocations.rallyNearMainChoke());
        }
    }

    public static void combatUnitRemoved(Unit unit) {
        if(unit.getType().isFlyer()) {
            airArmy.getSquadUnits().remove(unit);
        }
        else if(unit.getType().isMechanical() && !unit.getType().isFlyer()) {
            mechArmy.getSquadUnits().remove(unit);
        }
        else {
            bioArmy.getSquadUnits().remove(unit);
        }
    }

    public static void squadTactics(Squad squad) {
        if(!squad.getSquadUnits().isEmpty()) {
            if(squad.getStatus() == SquadStatus.ATTACKING) {
                if(EnemyInformation.enemyBaseDiscovered()) {
                    for(Unit unit : squad.getSquadUnits()) {
                        if(Vlasile.getFrameCount() % 30 == 0) {
                            if(!(unit.isAttacking() || unit.isStartingAttack() || unit.isAttackFrame())) {
                                if(enemyArmyVisible()) {
                                    unit.attack(getClosestUnit(unit));
                                }
                                else {
                                    if(getClosestEnemyBuilding(unit).getPosition().isValid()) {
                                        unit.attack(getClosestEnemyBuilding(unit));
                                    }
                                    else {
                                        unit.attack(getClosestEnemyPos(unit));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Unit getClosestUnit(Unit unit) {
        Double distanceToUnit = null;
        Unit closestUnit = null;
        for(Map.Entry<Integer, EnemyUnitInfo> u : UnitCount.getEnemyUnitCount().entrySet()) {
            Unit currentClosest = u.getValue().getUnitActual();

            if((currentClosest.getType().isBuilding() && !(u.getValue().getUnitType().canAttack())) || currentClosest.getType().isWorker() || currentClosest.getType() == UnitType.Zerg_Larva || currentClosest.getType() == UnitType.Zerg_Egg || currentClosest.getType() == UnitType.Zerg_Overlord) {
                continue;
            }

            if(closestUnit == null) {
                closestUnit = currentClosest;
                distanceToUnit = GameMethods.distanceFormula(currentClosest.getX(), currentClosest.getY(), unit.getX(), unit.getY());
            }
            else {
                Double distanceToCurrent = GameMethods.distanceFormula(currentClosest.getX(), currentClosest.getY(), unit.getX(), unit.getY());

                if(distanceToCurrent < distanceToUnit) {
                    closestUnit = currentClosest;
                    distanceToUnit = distanceToCurrent;
                }
            }
        }
        return closestUnit;
    }

    public static Position getClosestEnemyPos(Unit unit) {
        Double distanceToClosest = null;
        Position closestPos = null;
        if(!UnitCount.getEnemyBuildings().isEmpty()) {
            Iterator<EnemyBuildingInfo> itr = UnitCount.getEnemyBuildings().values().iterator();

            while(itr.hasNext()) {
                EnemyBuildingInfo enemyBuildingInfo = itr.next();
                Position currentPos = enemyBuildingInfo.getPosition();


                if(closestPos == null) {
                    closestPos = currentPos;
                    distanceToClosest = GameMethods.distanceFormula(currentPos.getX(), currentPos.getY(), unit.getX(), unit.getY());
                }
                else {
                    Double distanceToCurrent = GameMethods.distanceFormula(currentPos.getX(), currentPos.getY(), unit.getX(), unit.getY());

                    if(distanceToCurrent < distanceToClosest) {
                        closestPos = currentPos;
                    }
                }
            }
            return closestPos;

        }
        return null;
    }

    public static Unit getClosestEnemyBuilding(Unit unit) {
        Unit closestEnemy = null;
        Double distanceToClosest = null;
        if(!UnitCount.getEnemyBuildings().isEmpty()) {
            Iterator<EnemyBuildingInfo> itr = UnitCount.getEnemyBuildings().values().iterator();

            while(itr.hasNext()) {
                EnemyBuildingInfo enemyBuildingInfo = itr.next();
                Unit currentEnemy =  enemyBuildingInfo.getUnitActual();
                Position currentPos = enemyBuildingInfo.getPosition();

                if(closestEnemy == null) {
                    closestEnemy = currentEnemy;
                    distanceToClosest = GameMethods.distanceFormula(currentPos.getX(), currentPos.getY(), unit.getX(), unit.getY());
                }
                else {
                    Double distanceToCurrent = GameMethods.distanceFormula(currentPos.getX(), currentPos.getY(), unit.getX(), unit.getY());

                    if(distanceToCurrent < distanceToClosest) {
                        closestEnemy = currentEnemy;
                    }
                }
            }
            return closestEnemy;

        }
        return null;
    }

    private static boolean enemyArmyVisible() {
        for(Map.Entry<Integer, EnemyUnitInfo> unit : UnitCount.getEnemyUnitCount().entrySet()) {
            if((unit.getValue().getUnitType().isBuilding() && !(unit.getValue().getUnitType().canAttack())) || unit.getValue().getUnitType().isWorker() || unit.getValue().getUnitType() == UnitType.Zerg_Overlord || unit.getValue().getUnitType() == UnitType.Zerg_Egg || unit.getValue().getUnitType() == UnitType.Zerg_Larva) {
                continue;
            }
            else {
                if(unit.getValue().getUnitActual().getPosition().isValid()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<Squad> getSquads() {
        return squads;
    }

    public static Squad getAirArmy() {
        return airArmy;
    }

    public static Squad getBioArmy() {
        return bioArmy;
    }

    public static Squad getMechArmy() {
        return mechArmy;
    }
}
