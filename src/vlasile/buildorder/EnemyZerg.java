package vlasile.buildorder;

import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;
import vlasile.Vlasile;

public class EnemyZerg {
    private static Game game = Vlasile.getBwapi();
    private static Player self = Vlasile.getSelf();

    public static void TwoRaxFE() {
        Gathering.update();
    }

}
