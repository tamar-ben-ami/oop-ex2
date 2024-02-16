package bricker.brick_strategies;

import bricker.BrickerGameManager;
import danogl.GameObject;

public class BallsCollisionStrategy implements CollisionStrategy{
    private static final int NUM_OF_PUCKS = 3;
    private BrickerGameManager gameManager;

    public BallsCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
            gameManager.createPucks(otherObj.getCenter(), NUM_OF_PUCKS);
        }

    }
}
