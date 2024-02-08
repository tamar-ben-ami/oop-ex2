package bouncing_ball_from_ta.gameobjects;

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
    private int livesLeft = INITIAL_NUM_LIVES;
    private GraphicLifeCounter graphicLifeCounter;
    private NumericLifeCounter numericLifeCounter;
    private GameObjectCollection gameObjectCollection;
    private static final Vector2 NUM_COUNTER_DIM = new Vector2(30, 30);

    public LifeCounter(ImageReader imageReader, Vector2 windowDimensions, GameObjectCollection gameObjectCollection) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        Renderable numericImage = new TextRenderable(Integer.toString(livesLeft));
        this.numericLifeCounter = new NumericLifeCounter(Vector2.ZERO, NUM_COUNTER_DIM, numericImage);
        this.gameObjectCollection = gameObjectCollection;
        ImageRenderable graphicLifeCounterImage = imageReader.readImage("assets/heart.png", true);
        this.graphicLifeCounter = new GraphicLifeCounter(graphicLifeCounterImage, livesLeft, windowDimensions, gameObjectCollection);
    }

    public void increaseLife() {
        livesLeft += 1;
        numericLifeCounter.updateNumLives(livesLeft);
        numericLifeCounter.updateNumLives(livesLeft);
    }

    public void decreaseLife() {
        livesLeft -= 1;
        System.out.println(livesLeft);
        numericLifeCounter.updateNumLives(livesLeft);
        graphicLifeCounter.updateNumLives(livesLeft);
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
