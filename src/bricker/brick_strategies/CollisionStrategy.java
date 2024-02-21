package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Interface for brick's collision strategy.
 *
 * @author tamar, yaara
 */
public interface CollisionStrategy {
    /**
     * When ball collided to the brick, remove the brick and activate the strategy of the brick
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}
