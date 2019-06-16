package vlasile.buildorder;

public class StrategyController {

    private static Integer strategy = EnemyInformation.getStrategy();

    public static void CurrentStrat() {
        if(strategy == 0) {
            System.out.println("terran");
        }
        else if(strategy == 1) {
            System.out.println("Protoss");
        }
        else if(strategy == 2) {
            EnemyZerg.TwoRaxAllIn();
        }
        else {
            System.out.println("Random");
        }
    }

}
