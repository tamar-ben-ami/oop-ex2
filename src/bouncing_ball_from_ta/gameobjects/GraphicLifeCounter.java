package bouncing_ball_from_ta.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private ImageRenderable imageRenderable;
    private GameObject[] gameObjects;
    private static final Vector2 HEART_DIMENSION = new Vector2(30, 30);
    private int curr_life;
    private GameObjectCollection gameObjectCollection;


    public GraphicLifeCounter(ImageRenderable imageRenderable, int livesLeft,
                              Vector2 windowDimensions, GameObjectCollection gameObjectCollection) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        int leftCornerX = 0;
        this.imageRenderable = imageRenderable;
        this.curr_life = livesLeft - 1;
        this.gameObjects = new GameObject[livesLeft];
        this.gameObjectCollection = gameObjectCollection;
        for (int i = 0; i < livesLeft; i++) {
            this.gameObjects[i] = new GameObject(new Vector2(leftCornerX + i * HEART_DIMENSION.x(),
                    windowDimensions.y() - HEART_DIMENSION.y()), HEART_DIMENSION, this.imageRenderable);
        }
    }

    public GameObject[] getGameObjects() {
        return gameObjects;
    }

    public void updateNumLives(int numLives) {
        if (numLives <= curr_life) {
            decreaseLife();
        }
    }

    private void decreaseLife() {
        if (curr_life >= 0) {
            this.gameObjectCollection.removeGameObject(gameObjects[curr_life]);
            curr_life -= 1;
        }
    }
}
