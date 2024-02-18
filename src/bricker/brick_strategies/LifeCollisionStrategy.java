package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;

public class LifeCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;

    public LifeCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
//        if (gameManager.isBorder(otherObj)) {
//            System.out.println("FOUND THIS DUDE " + otherObj.getTag());
//        }
        if (gameManager.isMainBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
            gameManager.createLifeGift(thisObj.getCenter());
        }
    }
}
