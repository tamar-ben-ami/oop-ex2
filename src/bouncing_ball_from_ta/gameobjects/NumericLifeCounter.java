package bouncing_ball_from_ta.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

public class NumericLifeCounter extends GameObject {
    TextRenderable renderable;
    public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = (TextRenderable) renderable;
    }

    public void updateNumLives(int numLives) {
        renderable.setString(Integer.toString(numLives));
    }
}
