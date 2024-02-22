package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Collision strategy that removes the brick and activates camera zoom upon collision.
 *
 * @author tamar, yaara
 * @see CollisionStrategyDecorator
 */
public class CameraCollisionStrategy extends CollisionStrategyDecorator{

    /**
     * Constructor for CameraCollisionStrategy.
     * @param decoratedStrategy the decorated strategy
     */
    public CameraCollisionStrategy(CollisionStrategy decoratedStrategy) {
        super(decoratedStrategy);
    }

    /**
     * When ball collided to the brick, remove the brick and activate the camera zoom.
     * the camera will be focus on the main ball till 4 collisions.
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if (gameManager.isMainBall(otherObj)) {
            gameManager.setCameraBallZoom();
        }
    }
}
