package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private ImageRenderable imageRenderable;
    private GameObject[] gameObjects;
    private static final Vector2 HEART_DIMENSION = new Vector2(30, 30);
    private int curr_life;
    private int livesLeft;
    private GameObjectCollection gameObjectCollection;
    private static final int leftCornerX = 0;
    private Vector2 windowDimensions;

    public GraphicLifeCounter(ImageRenderable imageRenderable, int livesLeft,
                              Vector2 windowDimensions, GameObjectCollection gameObjectCollection) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        int leftCornerX = 0;
        this.imageRenderable = imageRenderable;
        this.curr_life = livesLeft - 1;
        this.livesLeft = livesLeft;
        this.gameObjects = new GameObject[livesLeft];
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        for (int i = 0; i < livesLeft; i++) {
            this.gameObjects[i] = new GameObject(new Vector2(leftCornerX + i * HEART_DIMENSION.x(),
                    windowDimensions.y() - HEART_DIMENSION.y()), HEART_DIMENSION, this.imageRenderable);
        }
    }

    public GameObject[] getGameObjects() {
        return gameObjects;
    }

    public void updateNumLives(int numLives) {
        if (curr_life == numLives - 1) {return;}
        while (numLives <= curr_life) {
            decreaseGraphicLife();
        }
        while (numLives > curr_life + 1) {
            increaseGraphicLife();
        }
    }

    private void decreaseGraphicLife() {
        System.out.println("decrease from " + curr_life + " to " + (curr_life - 1));

        if (curr_life >= 0) {
            gameObjectCollection.removeGameObject(gameObjects[curr_life]);
            curr_life -= 1;
            livesLeft -= 1;
        }
    }

    private void increaseGraphicLife() {
        livesLeft += 1;
        curr_life += 1;
        GameObject[] temp = new GameObject[livesLeft];
        if (livesLeft - 1 >= 0) System.arraycopy(gameObjects, 0, temp, 0, livesLeft - 1);
        temp[curr_life] = new GameObject(new Vector2(leftCornerX + curr_life * HEART_DIMENSION.x(),
                windowDimensions.y() - HEART_DIMENSION.y()), HEART_DIMENSION, this.imageRenderable);
        gameObjects = temp;
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return !other.getTag().equals("Ball");
    }
}
