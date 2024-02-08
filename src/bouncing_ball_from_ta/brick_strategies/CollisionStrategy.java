package bouncing_ball_from_ta.brick_strategies;

import danogl.GameObject;

public interface CollisionStrategy {
    void onCollision(GameObject thisObj, GameObject otherObj);
}
