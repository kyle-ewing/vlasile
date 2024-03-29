package vlasile.squads;

import bwapi.Unit;

import java.util.ArrayList;

public class Squad {
    private static int squadNumbers = 0;
    private int squadNumber = squadNumbers++;
    private ArrayList<Unit> squadUnits = new ArrayList<>();
    private String name;
    private SquadStatus status;

    public Squad(String name) {
        this.name = name;
        this.status = SquadStatus.IDLE;
    }

    public Squad(ArrayList<Unit> squadUnits) {
        this.squadUnits = squadUnits;
    }

    public ArrayList<Unit> getSquadUnits() {
        return squadUnits;
    }

    public void setSquadUnits(ArrayList<Unit> squadUnits) {
        this.squadUnits = squadUnits;
    }

    public int getSquadNumber() {
        return squadNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SquadStatus getStatus() {
        return status;
    }

    public void setStatus(SquadStatus status) {
        this.status = status;
    }
}
