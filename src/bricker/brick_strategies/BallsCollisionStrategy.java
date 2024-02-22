package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Collision strategy that removes the brick and create two small puck balls
 *
 * @author tamar, yaara
 * @see CollisionStrategy
 */
public class BallsCollisionStrategy implements CollisionStrategy{
    private static final int NUM_OF_PUCKS = 3;
    private final BrickerGameManager gameManager;

    /**
     * constructor for BallsCollisionStrategy
     * @param gameManager the game manager
     */
    public BallsCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * When breaking a brick with this behavior:
     * 2 white puck balls will be created in the center of the brick's location
     *
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isMainBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
            gameManager.createPucks(otherObj.getCenter(), NUM_OF_PUCKS);
        }

    }
}
