package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class LifeCounter extends GameObject {
    private static final int INITIAL_NUM_LIVES = 3;
    private static final int MAX_NUM_LIVES = 4;
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;
    private int livesLeft = INITIAL_NUM_LIVES;

    // Numeric counter variables
    private TextRenderable numericImage;
    private static final Vector2 NUM_COUNTER_DIM = new Vector2(30, 30);

    // Graphic counter variables
    private GameObject[] graphicCounterHearts;
    private static final Vector2 HEART_DIMENSION = new Vector2(30, 30);
    private ImageRenderable graphicLifeCounterImage;

    public LifeCounter(ImageReader imageReader, Vector2 windowDimensions, GameObjectCollection gameObjectCollection) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = gameObjectCollection;
        createNumericLifeCounter(gameObjectCollection);
        createGraphicLifeCounter(imageReader, windowDimensions);
    }

    private void createGraphicLifeCounter(ImageReader imageReader, Vector2 windowDimensions) {
        this.graphicLifeCounterImage = imageReader.readImage("assets/heart.png", true);
        this.graphicCounterHearts = new GameObject[livesLeft];
        for (int i = 0; i < livesLeft; i++) {
            this.graphicCounterHearts[i] = new GameObject(new Vector2( i * HEART_DIMENSION.x(),
                    windowDimensions.y() - HEART_DIMENSION.y()), HEART_DIMENSION, graphicLifeCounterImage);
            this.gameObjectCollection.addGameObject(this.graphicCounterHearts[i], Layer.BACKGROUND);
        }
    }

    private void createNumericLifeCounter(GameObjectCollection gameObjectCollection) {
        numericImage = new TextRenderable(Integer.toString(livesLeft));
        GameObject numericLifeCounter = new GameObject(Vector2.ZERO, NUM_COUNTER_DIM, numericImage);
        gameObjectCollection.addGameObject(numericLifeCounter);
        numericImage.setColor(Color.green);
        setNumericCounterColorByLives();
    }

    public void increaseLife() {
        if (livesLeft < MAX_NUM_LIVES) {
            livesLeft += 1;
            numericImage.setString(Integer.toString(livesLeft));
            setNumericCounterColorByLives();
            addHeartToGraphicCounterHearts();
            gameObjectCollection.addGameObject(graphicCounterHearts[livesLeft - 1], Layer.BACKGROUND);
        }
    }

    private void addHeartToGraphicCounterHearts() {
        GameObject[] temp = new GameObject[livesLeft];
        if (livesLeft - 1 >= 0) System.arraycopy(graphicCounterHearts, 0, temp, 0, livesLeft - 1);
        temp[livesLeft - 1] = new GameObject(new Vector2( (livesLeft - 1) * HEART_DIMENSION.x(),
                windowDimensions.y() - HEART_DIMENSION.y()), HEART_DIMENSION, graphicLifeCounterImage);
        graphicCounterHearts = temp;
    }

    public void decreaseLife() {
        livesLeft -= 1;
        numericImage.setString(Integer.toString(livesLeft));
        setNumericCounterColorByLives();
        gameObjectCollection.removeGameObject(graphicCounterHearts[livesLeft], Layer.BACKGROUND);
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return !other.getTag().equals("Ball");
    }

    private void setNumericCounterColorByLives() {
        switch (livesLeft) {
            case 2 -> numericImage.setColor(Color.yellow);
            case 1 -> numericImage.setColor(Color.red);
            default -> numericImage.setColor(Color.green);
        }
    }
}
