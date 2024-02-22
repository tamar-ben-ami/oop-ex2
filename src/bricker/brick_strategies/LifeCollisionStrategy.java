package bricker.brick_strategies;

import danogl.GameObject;


/**
 * Collision strategy that removes the brick and creating a gift life
 *
 * @author tamar, yaara
 * @see CollisionStrategyDecorator
 */
public class LifeCollisionStrategy extends CollisionStrategyDecorator{

    /**
     * Constructor for LifeCollisionStrategy.
     * @param decoratedStrategy the decorated strategy
     */
    public LifeCollisionStrategy(CollisionStrategy decoratedStrategy) {
        super(decoratedStrategy);
    }

    /**
     * remove the object and creat falling gift life
     * a heart object will fall from the center of the brick,
     * which the paddle must "collect" in order to get life as a gift
     * the life gift appears at the location of the center of the brick
     *
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if (gameManager.isMainBall(otherObj)) {
            gameManager.createLifeGift(thisObj.getCenter());
        }
    }
}
