package vlasile.managers;

import bwapi.Position;
import bwapi.Unit;
import com.sun.org.apache.bcel.internal.classfile.Unknown;
import vlasile.MapLocations;
import vlasile.UnitCount;
import vlasile.Vlasile;
import vlasile.enemy.EnemyBuildingInfo;
import vlasile.enemy.EnemyInformation;
import vlasile.squads.Squad;
import vlasile.squads.SquadStatus;

import java.util.ArrayList;
import java.util.Iterator;

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

    public static void squadTactics(Squad squad) {
        if(!squad.getSquadUnits().isEmpty()) {
            if(squad.getStatus() == SquadStatus.ATTACKING) {
                if(EnemyInformation.enemyBaseDiscovered()) {
                    for(Unit unit : squad.getSquadUnits()) {
                        if(Vlasile.getFrameCount() % 30 == 0) {
                            if(!(unit.isAttacking() || unit.isStartingAttack() || unit.isAttackFrame())) {
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
                    distanceToClosest = Math.sqrt(((currentPos.getX() - unit.getX()) * (currentPos.getX() - unit.getX())) + ((currentPos.getY() - unit.getY()) * (currentPos.getY() - unit.getY())));
                }
                else {
                    Double distanceToCurrent = Math.sqrt(((currentPos.getX() - unit.getX()) * (currentPos.getX() - unit.getX())) + ((currentPos.getY() - unit.getY()) * (currentPos.getY() - unit.getY())));

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
                    distanceToClosest = Math.sqrt(((currentPos.getY() - unit.getY()) * (currentPos.getY() - unit.getY())) + ((currentPos.getY() - unit.getY()) * (currentPos.getY() - unit.getY())));
                }
                else {
                    Double distanceToCurrent = Math.sqrt(((currentPos.getY() - unit.getY()) * (currentPos.getY() - unit.getY())) + ((currentPos.getY() - unit.getY()) * (currentPos.getY() - unit.getY())));

                    if(distanceToCurrent < distanceToClosest) {
                        closestEnemy = currentEnemy;
                    }
                }
            }
            return closestEnemy;

        }
        return null;
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
