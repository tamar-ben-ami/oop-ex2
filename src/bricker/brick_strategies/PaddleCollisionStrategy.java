package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;

public class PaddleCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;

    public PaddleCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
        }
    }
}

