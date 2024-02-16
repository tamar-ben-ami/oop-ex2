package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static bricker.BrickerGameManager.*;

public class BallsCollisionStrategy implements CollisionStrategy{
    private static final int NUM_OF_PUCKS = 3;
    private BrickerGameManager brickerGameManager;
    private String ballTag;

    public BallsCollisionStrategy(BrickerGameManager brickerGameManager, String ballTag) {
        this.brickerGameManager = brickerGameManager;
        this.ballTag = ballTag;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (otherObj.getTag().equals(ballTag)) {
            brickerGameManager.removeGameObject(thisObj);
//            brickerGameManager.createPucks(otherObj.getCenter(), NUM_OF_PUCKS);
        }

    }
}
