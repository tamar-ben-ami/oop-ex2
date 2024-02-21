package bricker.brick_strategies;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Collision strategy that removes the brick and activates camera zoom upon collision.
 *
 * @author tamar, yaara
 * @see CollisionStrategy
 */
public class CameraCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;

    /**
     * Constructor for CameraCollisionStrategy.
     * @param gameManager
     */
    public CameraCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * When ball collided to the brick, remove the brick and activate the camera zoom.
     * the camera will be focus on the main ball till 4 collisions.
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isMainBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
            gameManager.cameraBallZoom();
        }
    }
}
