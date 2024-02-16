package bricker.brick_strategies;

import bricker.BrickerGameManager;
import danogl.GameObject;

public class BasicCollisionStrategy implements CollisionStrategy{
    //TODO add to read me
    private final BrickerGameManager gameManager;

    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
        }
    }
}
