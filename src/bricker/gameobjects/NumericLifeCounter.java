package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {
    private TextRenderable renderable;
    private int numLives;
    public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, int numLives) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = (TextRenderable) renderable;
        this.numLives = numLives;
        this.renderable.setColor(Color.green);
        setColorByLives();
    }

    public void updateNumLives(int numLives) {
        this.numLives = numLives;
        renderable.setString(Integer.toString(numLives));
        setColorByLives();
    }

    private void setColorByLives() {
        switch (this.numLives) {
            case 2 -> this.renderable.setColor(Color.yellow);
            case 1 -> this.renderable.setColor(Color.red);
            default -> this.renderable.setColor(Color.green);
        }
    }
}
