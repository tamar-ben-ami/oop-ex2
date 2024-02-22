package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Collision strategy that removes the brick and create a second paddle
 *
 * @author tamar, yaara
 * @see CollisionStrategyDecorator
 */
public class PaddleCollisionStrategy extends CollisionStrategyDecorator{

    /**
     * Constructor for PaddleCollisionStrategy.
     * @param decoratedStrategy the decorated strategy
     */
    public PaddleCollisionStrategy(CollisionStrategy decoratedStrategy) {
        super(decoratedStrategy);
    }

    /**
     * remove the object and create a second paddle in the middle of the screen.
     *
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if (gameManager.isMainBall(otherObj)) {
            gameManager.createSecondPaddle();
        }
    }
}

