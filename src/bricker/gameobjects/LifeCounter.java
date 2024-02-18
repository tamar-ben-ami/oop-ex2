package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

public class LifeCounter extends GameObject {
    private static final int INITIAL_NUM_LIVES = 3;
    private static final int MAX_NUM_LIVES = 4;
    private int livesLeft = INITIAL_NUM_LIVES;
    private final GraphicLifeCounter graphicLifeCounter;
    private final NumericLifeCounter numericLifeCounter;
    private final GameObjectCollection gameObjectCollection;
    private static final Vector2 NUM_COUNTER_DIM = new Vector2(30, 30);

    public LifeCounter(ImageReader imageReader, Vector2 windowDimensions, GameObjectCollection gameObjectCollection) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        Renderable numericImage = new TextRenderable(Integer.toString(livesLeft));
        this.numericLifeCounter = new NumericLifeCounter(Vector2.ZERO, NUM_COUNTER_DIM, numericImage, livesLeft);
        this.gameObjectCollection = gameObjectCollection;
        ImageRenderable graphicLifeCounterImage = imageReader.readImage("assets/heart.png", true);
        this.graphicLifeCounter = new GraphicLifeCounter(graphicLifeCounterImage, livesLeft, windowDimensions, gameObjectCollection);
    }

    public void increaseLife() {
        if (livesLeft < MAX_NUM_LIVES) {
            livesLeft += 1;
            numericLifeCounter.increaseLife();
            graphicLifeCounter.increaseLife();
            gameObjectCollection.addGameObject(graphicLifeCounter.getGameObjects()[livesLeft - 1]);
        }
    }

    public void decreaseLife() {
        livesLeft -= 1;
        numericLifeCounter.decreaseLife();
        graphicLifeCounter.decreaseLife();
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public void addToGameObjects() {
        gameObjectCollection.addGameObject(numericLifeCounter, Layer.UI);
        for (int i = 0; i < livesLeft; i++){
            gameObjectCollection.addGameObject(graphicLifeCounter.getGameObjects()[i]);
        }
    }
}
