package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;

public class DoubleCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;
    private static final int DOUBLE_COLLISION_STRATEGIES = 2;
    private static final int MAX_COLLISION_STRATEGIES = 3;
    private int count_strategies = 0;
    private CollisionStrategy[] collisionStrategies = new CollisionStrategy[MAX_COLLISION_STRATEGIES];


    public DoubleCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
        int count_double = 0;
        while (count_strategies - count_double < DOUBLE_COLLISION_STRATEGIES
                & count_strategies < MAX_COLLISION_STRATEGIES) {
            collisionStrategies[count_strategies] = CollisionStrategyFactory.getSpeicalCollisionStrategy(gameManager);
            count_strategies++;
        }
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isMainBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
            for (int i = 0; i < count_strategies; i++) {
                collisionStrategies[i].onCollision(thisObj, otherObj);
            }
        }
    }
}

