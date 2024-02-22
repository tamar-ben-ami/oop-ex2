package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Collision strategy that removes the brick and activate the strategy of the brick
 *
 * @author tamar, yaara
 */
public abstract class CollisionStrategy {
    /**
     * the game manager
     */
    protected final BrickerGameManager gameManager;

    /**
     * constructor for CollisionStrategy
     * @param gameManager the game manager
     */
    public CollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }
    /**
     * When ball collided to the brick, remove the brick and activate the strategy of the brick
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    public abstract void onCollision(GameObject thisObj, GameObject otherObj);
}
