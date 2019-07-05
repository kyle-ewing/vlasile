package vlasile.managers;

import bwapi.Unit;
import vlasile.Painter;
import vlasile.UnitCount;
import vlasile.Vlasile;
import vlasile.scouting.ScvScout;
import vlasile.unitstatus.WorkerStatus;

import java.util.HashMap;

public class WorkerManager {

    public static void update() {
        workerStatus();
    }

    private static void workerStatus() {
        HashMap workers = UnitCount.getWorkers();

        for(Unit unit : Vlasile.getSelf().getUnits()) {
            if(unit.isGatheringMinerals() && workers.get(unit.getID()) != WorkerStatus.MINERALS) {
                workers.put(unit.getID(), WorkerStatus.MINERALS);
            }
            else if(unit.isGatheringGas() && workers.get(unit.getID()) != WorkerStatus.GAS) {
                workers.put(unit.getID(), WorkerStatus.GAS);
            }
            else if(ScvScout.getScout() != null && unit.getID() == ScvScout.getScout().getID() && workers.get(unit.getID()) != WorkerStatus.SCOUT) {
                workers.put(unit.getID(), WorkerStatus.SCOUT);
            }
            else if(unit.isAttacking() && workers.get(unit.getID()) != WorkerStatus.ATTACK) {
                workers.put(unit.getID(), WorkerStatus.ATTACK);
            }
            else if(unit.isRepairing() && workers.get(unit.getID()) != WorkerStatus.REPAIR) {
                workers.put(unit.getID(), WorkerStatus.REPAIR);
            }
            else if(unit.isConstructing() && workers.get(unit.getID()) != WorkerStatus.BUILD) {
                workers.put(unit.getID(), WorkerStatus.BUILD);
            }
        }
    }
}
