package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Collision strategy decorator that removes the brick and activates the strategy of the brick
 *
 * @author tamar, yaara
 * @see CollisionStrategy
 */
public abstract class CollisionStrategyDecorator extends CollisionStrategy{
    /**
     * the decorated strategy
     */
    protected final CollisionStrategy decoratedStrategy;

    /**
     * constructor for CollisionStrategyDecorator
     * @param decoratedStrategy the decorated strategy
     */
    public CollisionStrategyDecorator(CollisionStrategy decoratedStrategy) {
        super(decoratedStrategy.gameManager);
        this.decoratedStrategy = decoratedStrategy;
    }

    /**
     * When ball collided to the brick, remove the brick and activate the strategy of the brick
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        decoratedStrategy.onCollision(thisObj, otherObj);
    }
}
