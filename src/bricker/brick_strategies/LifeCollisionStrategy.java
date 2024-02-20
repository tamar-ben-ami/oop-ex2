package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;

/**
 * When breaking a brick holding this behavior, a heart object will fall from the center of the brick,
 * which the paddle must "collect" in order to get life as a gift
 *
 */
/**
 * Collision strategy that removes the brick and creating a gift life
 */
public class LifeCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;

    /**
     * constructor for LifeCollisionStrategy
     * @param gameManager the game manager
     */
    public LifeCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
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
        if (gameManager.isMainBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
            gameManager.createLifeGift(thisObj.getCenter());
        }
    }
}
