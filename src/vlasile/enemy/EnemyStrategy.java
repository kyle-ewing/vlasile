package vlasile.enemy;

public class EnemyStrategy {
    private static EnemyStrategy enemyStrategy = null;
    private String name;
    private boolean rush = false;
    private boolean air = false;
    private boolean expansion = false;

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public EnemyStrategy setName(String name) {
        this.name = name;
        return this;
    }

    public static boolean isEnemyStratKnown() {
        return enemyStrategy != null;
    }

    public static EnemyStrategy getEnemyStrategy() {
        return enemyStrategy;
    }

    public static void setEnemyStrategy(EnemyStrategy enemyStrategy) {
        EnemyStrategy.enemyStrategy = enemyStrategy;
    }

    public boolean isRush() {
        return rush;
    }

    public EnemyStrategy setRush() {
        this.rush = true;
        return this;
    }

    public boolean isAir() {
        return air;
    }

    public EnemyStrategy setAir() {
        this.air = true;
        return this;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public EnemyStrategy setExpansion() {
        this.expansion = true;
        return this;
    }
}
