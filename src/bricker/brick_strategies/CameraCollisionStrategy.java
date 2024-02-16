package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;

public class CameraCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;

    public CameraCollisionStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (gameManager.isBall(otherObj)) {
            gameManager.removeGameObject(thisObj);
//            gameManager.cameraBallZoom();
        }
    }
}
