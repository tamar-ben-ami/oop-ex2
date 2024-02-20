package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;

/**
 * Collision strategy that removes the brick and create a second paddle
 */
public class PaddleCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;

    /**
     * constructor for PaddleCollisionStrategy
     * @param gameManager the game manager
     */
    public PaddleCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * remove the object and create a second paddle in the middle of the screen.
     *
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isMainBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
            gameManager.createSecondPaddle();
        }
    }
}

