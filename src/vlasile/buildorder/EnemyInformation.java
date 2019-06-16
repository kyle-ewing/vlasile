package vlasile.buildorder;

import bwapi.Game;
import bwapi.Player;
import bwapi.Race;
import vlasile.GameMethods;
import vlasile.Vlasile;

public class EnemyInformation {

    private static boolean raceChecked = true;

    public static void getEnemyRace() {
            if(GameMethods.enemy().getRace().equals(Race.Terran)) {
                System.out.println("Terran");
            }
            else if(GameMethods.enemy().getRace().equals(Race.Protoss)) {
                System.out.println("Protoss");
            }
            else if(GameMethods.enemy().getRace().equals(Race.Zerg)) {
                raceChecked = false;
                EnemyZerg.TwoRaxFE();
            }
            else {
                System.out.println("Random");
            }

    }
}
