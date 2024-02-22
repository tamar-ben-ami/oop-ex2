package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;

/**
 * Basic collision strategy that removes the brick upon collision.
 *
 * @author tamar, yaara
 * @see CollisionStrategy
 */
public class BasicCollisionStrategy extends CollisionStrategy {
    /**
     * Constructor for BasicCollisionStrategy.
     * @param gameManager the game manager instance
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        super(gameManager);
    }


    /**
     * When ball collided to the brick, remove the brick.
     * @param thisObj the brick
     * @param otherObj the object that collided with the brick
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (otherObj.getTag().equals(Constants.BALL_TAG)){
            gameManager.removeGameObject(thisObj);
        }
    }
}
