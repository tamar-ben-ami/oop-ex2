package bricker.brick_strategies;

import bricker.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class BasicCollisionStrategy implements CollisionStrategy{
    //TODO add to read me
    private final BrickerGameManager brickerGameManager;
    private final String ballTag;

    public BasicCollisionStrategy(BrickerGameManager brickerGameManager, String ballTag) {
        this.brickerGameManager = brickerGameManager;
        this.ballTag = ballTag;
    }


    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (otherObj.getTag().equals(ballTag)) {
            brickerGameManager.removeGameObject(thisObj);
        }
    }
}
