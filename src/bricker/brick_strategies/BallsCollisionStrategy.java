package bricker.brick_strategies;

import bricker.main.Constants;
import danogl.GameObject;

/**
 * Collision strategy that removes the brick and create two small puck balls
 *
 * @author tamar, yaara
 * @see CollisionStrategyDecorator
 */
public class BallsCollisionStrategy extends CollisionStrategyDecorator{
    /**
     * constructor for CollisionStrategy
     *
     * @param decoratedStrategy the decorated strategy
     */
    public BallsCollisionStrategy(CollisionStrategy decoratedStrategy){
        super(decoratedStrategy);
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
        super.onCollision(thisObj, otherObj);
        if (gameManager.isMainBall(otherObj)) {
            gameManager.createPucks(otherObj.getCenter(), Constants.NUM_OF_PUCKS);
        }

    }
}
