package bouncing_ball_from_ta.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class AIPaddle extends GameObject {
    private static final double PADDLE_SPEED = 400;
    private GameObject objectToFollow;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public AIPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    GameObject objectToFollow) {
        super(topLeftCorner, dimensions, renderable);
        this.objectToFollow = objectToFollow;
        this.setTag("AIPaddle");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(objectToFollow.getCenter().x() < getCenter().x())
            movementDir = Vector2.LEFT;
        if(objectToFollow.getCenter().x() > getCenter().x())
            movementDir = Vector2.RIGHT;
        setVelocity(movementDir.mult((float) PADDLE_SPEED));
    }
}
