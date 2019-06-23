package vlasile.enemy;

import vlasile.buildorders.TwoRaxAllIn;
import vlasile.production.WorkerProduction;

public class EnemyZerg {

    public static void TwoRaxAllIn() {
        WorkerProduction.buildScv();
        TwoRaxAllIn.executeBuild();
    }
}
