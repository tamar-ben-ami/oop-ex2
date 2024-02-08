package bouncing_ball_from_ta.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class BasicCollisionStrategy implements CollisionStrategy{
    //TODO add to read me
    private GameObjectCollection gameObjectCollection;
    private String ballTag;

    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection, String ballTag) {
        this.gameObjectCollection = gameObjectCollection;
        this.ballTag = ballTag;
    }


    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (otherObj.getTag().equals(ballTag)) {
            gameObjectCollection.removeGameObject(thisObj);
        }
    }
}
