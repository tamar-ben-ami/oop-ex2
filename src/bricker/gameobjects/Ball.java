package bricker.gameobjects;

import bricker.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents a ball GameObject in the Bricker game.
 */
public class Ball extends GameObject {
    private static final double BALL_SPEED = 350; // Initial speed of the ball
    private final Sound collisionSound; // Sound played when the ball collides
    private BrickerGameManager gameManager; // Reference to the game manager
    private Vector2 topLeftCorner; // Top-left corner position of the ball when setting it
    private boolean zoomTimer; // Flag indicating whether zoom timer is active
    private int countCollision; // Counter for the number of collisions

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the ball, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the ball. Can be null, in which case
     *                      the ball will not be rendered.
     * @param collisionSound The sound to play when the ball collides with something.
     * @param tag            A tag to identify the ball.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    Sound collisionSound, String tag) {
        super(topLeftCorner, dimensions, renderable);
        this.topLeftCorner = topLeftCorner;
        this.collisionSound = collisionSound;
        this.setTag(tag);
        setRandomVelocity();
        zoomTimer = false;
        countCollision = 0;
    }

    /**
     * Handles the event when a collision occurs with another GameObject.
     *
     * @param other     The GameObject with which the collision occurred.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        if (zoomTimer && gameManager.isMainBall(this)) {
            countCollision++;
            if (countCollision == 4) {
                gameManager.setCamera(null);
                zoomTimer = false;
            }

        }
    }

    /**
     * Resets the ball to its initial position and gives it a random velocity.
     */
    public void reset(){
        this.setCenter(topLeftCorner);
        setRandomVelocity();
    }

    /**
     * Sets a random velocity for the ball.
     */
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

    /**
     * Sets the zoom timer for the ball, allowing it to trigger specific actions on collisions.
     *
     * @param gameManager The BrickerGameManager instance.
     */
    public void setZoomTimer(BrickerGameManager gameManager){
        this.gameManager = gameManager;
        countCollision = 0;
        zoomTimer = true;
    }
}
