package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class PaddleCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;
    private final String ballTag;

    public PaddleCollisionStrategy(BrickerGameManager brickerGameManager, String ballTag) {
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

