package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;

public class DoubleCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;
    public static final int MAX_COLLISION_STRATEGIES = 3;
    private CollisionStrategy[] collisionStrategies;


    public DoubleCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;

//        count_not_double = 0;
//        while (count_not_double < MAX_COLLISION_STRATEGIES) {
//            collisionStrategies[count_not_double] = CollisionStrategyFactory.getRandomCollisionStrategy(brickerGameManager, ballTag);
//            count_not_double++;
//        }
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
        }
    }
}

