package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents a LifeCounter GameObject in the Bricker game.
 *
 * @author tamar, yaara
 * @see GameObject
 */
public class LifeCounter extends GameObject {
    private static final int INITIAL_NUM_LIVES = 3;
    private static final int MAX_NUM_LIVES = 4;
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 windowDimensions;
    private int livesLeft = INITIAL_NUM_LIVES;
    private ImageReader imageReader;

    // Numeric counter variables
    private TextRenderable numericImage;
    private static final Vector2 NUM_COUNTER_DIM = new Vector2(30, 30);

    // Graphic counter variables
    private GameObject[] graphicCounterHearts;
    private static final Vector2 HEART_DIMENSION = new Vector2(30, 30);
    private ImageRenderable graphicLifeCounterImage;

    /**
     * Construct a new LifeCounter instance.
     * @param imageReader imageReader of the gameManager
     * @param windowDimensions the window dimensions of game
     * @param gameObjectCollection the game objects collection of the manager,
     *                            where the graphic and numeric counters would be added to
     */
    public LifeCounter(ImageReader imageReader, Vector2 windowDimensions,
                       GameObjectCollection gameObjectCollection) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.windowDimensions = windowDimensions;
        this.imageReader = imageReader;
        this.gameObjectCollection = gameObjectCollection;
        createNumericLifeCounter();
        createGraphicLifeCounter();
    }

    /**
     * This function creates the graphic life counter, and creates array of hearts objects
     */
    private void createGraphicLifeCounter() {
        this.graphicLifeCounterImage = imageReader.readImage("assets/heart.png", true);
        this.graphicCounterHearts = new GameObject[livesLeft];
        for (int i = 0; i < livesLeft; i++) {
            this.graphicCounterHearts[i] = new GameObject(new Vector2( i * HEART_DIMENSION.x(),
                    windowDimensions.y() - HEART_DIMENSION.y()), HEART_DIMENSION, graphicLifeCounterImage);
            this.gameObjectCollection.addGameObject(this.graphicCounterHearts[i], Layer.BACKGROUND);
        }
    }

    /**
     * This funciton creates the numeric life counter game object
     */
    private void createNumericLifeCounter() {
        numericImage = new TextRenderable(Integer.toString(livesLeft));
        GameObject numericLifeCounter = new GameObject(Vector2.ZERO, NUM_COUNTER_DIM, numericImage);
        gameObjectCollection.addGameObject(numericLifeCounter);
        numericImage.setColor(Color.green);
        setNumericCounterColorByLives();
    }

    /**
     * This function increases life in numeric and graphic counters
     */
    public void increaseLife() {
        if (livesLeft < MAX_NUM_LIVES) {
            livesLeft += 1;
            numericImage.setString(Integer.toString(livesLeft));
            setNumericCounterColorByLives();
            addHeartToGraphicCounterHearts();
            gameObjectCollection.addGameObject(graphicCounterHearts[livesLeft - 1], Layer.BACKGROUND);
        }
    }

    /**
     * This function adds heart object to the graphicCounterHearts array
     */
    private void addHeartToGraphicCounterHearts() {
        GameObject[] temp = new GameObject[livesLeft];
        if (livesLeft - 1 >= 0) System.arraycopy(graphicCounterHearts, 0, temp, 0, livesLeft - 1);
        temp[livesLeft - 1] = new GameObject(new Vector2( (livesLeft - 1) * HEART_DIMENSION.x(),
                windowDimensions.y() - HEART_DIMENSION.y()), HEART_DIMENSION, graphicLifeCounterImage);
        graphicCounterHearts = temp;
    }

    /**
     * This function decreases life in numeric and graphic counters
     */
    public void decreaseLife() {
        livesLeft -= 1;
        numericImage.setString(Integer.toString(livesLeft));
        setNumericCounterColorByLives();
        gameObjectCollection.removeGameObject(graphicCounterHearts[livesLeft], Layer.BACKGROUND);
    }

    /**
     * This function returns true if the player has more life, false otherwise
     * @return true if the player has more life, false otherwise
     */
    public boolean isAlive() {
        return livesLeft > 0;
    }

    /**
     * This function is overriding the gameObject shouldCollideWith,
     * and validates the life counter does not collide with the ball
     * @param other object to collide with
     * @return
    true if the objects should collide. This does not guarantee a collision would actually collide if
    they overlap, since the other object has to confirm this one as well.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return !other.getTag().equals("Ball");
    }

    /**
     * This function sets the color of numeric life counter by the number of lives left
     */
    private void setNumericCounterColorByLives() {
        switch (livesLeft) {
            case 2 -> numericImage.setColor(Color.yellow);
            case 1 -> numericImage.setColor(Color.red);
            default -> numericImage.setColor(Color.green);
        }
    }
}
