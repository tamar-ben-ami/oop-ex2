package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Ball extends GameObject {
    private static final double BALL_SPEED = 350;
    private final Sound collisionSound;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param center        The center of the object, in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the object will not be rendered.
     * @param collisionSound The sound to play when the ball collides with something.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Vector2 center, Renderable renderable,
                    Sound collisionSound, String tag) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.setTag(tag);
        this.setCenter(center);
        setRandomVelocity();
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }

    public void setRandomVelocity() {
        float ballVelX = (float) BALL_SPEED;
        float ballVelY = (float) BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean())
            ballVelX *= -1;
        if(rand.nextBoolean())
            ballVelY *= -1;
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }
}
