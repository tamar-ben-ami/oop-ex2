package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * Represents a paddle GameObject in the Bricker game.
 *
 * @author tamar, yaara
 * @see GameObject
 */
public class Paddle extends GameObject {
    private static final double MOVEMENT_SPEED = 400;  // Movement speed of the paddle
    private final UserInputListener inputListener; // Input listener for paddle controls
    private final float width; // Width of the paddle
    private final double rightWallX; // X-coordinate of the right wall
    private final double leftWallX; // X-coordinate of the left wall
    private int countCollision; // Counter for the number of collisions
    private boolean disappearingTimer; // Flag indicating whether disappearing timer is active
    private BrickerGameManager gameManager = null; // Reference to the game manager

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner Position of the paddle, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the paddle. Can be null, in which case
     *                      the paddle will not be rendered.
     * @param inputListener The input listener to use for this paddle.
     * @param rightWallX    The x coordinate of the right wall.
     * @param leftWallX     The x coordinate of the left wall.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener,
                  double rightWallX, double leftWallX) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.width = dimensions.x();
        this.rightWallX = rightWallX;
        this.leftWallX = leftWallX;
        this.disappearingTimer = false;
        this.countCollision = 0;
        this.setTag("UserPaddle");
    }

    /**
     * Handles the event when a collision occurs with another GameObject.
     *
     * @param other     The GameObject with which the collision occurred.
     * @param collision Information about the collision.
     */
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        // if not the main paddle and has collided 4 times, remove the paddle
        if (disappearingTimer && gameManager.isMainBall(other)) {
            countCollision++;
            if (countCollision == 4 && !gameManager.isMainPaddle(this)) {
                gameManager.removeGameObject(this);
            }
        }
    }

    /**
     * Updates the paddle's position based on user input.
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = validateLeftMovementDir(movementDir.add(Vector2.LEFT));
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = validateRightMovementDir(movementDir.add(Vector2.RIGHT));
        }

        setVelocity(movementDir.mult((float) MOVEMENT_SPEED));
    }

    /**
     * Validates the left movement direction based on the paddle's position and left wall boundary.
     *
     * @param movementDir The current movement direction vector.
     * @return The adjusted movement direction vector considering the left wall boundary.
     */
    private Vector2 validateLeftMovementDir(Vector2 movementDir) {
        if (this.getTopLeftCorner().x() < this.leftWallX) {
            return Vector2.ZERO;
        }
        return movementDir;
    }

    /**
     * Validates the right movement direction based on the paddle's position and right wall boundary.
     *
     * @param movementDir The current movement direction vector.
     * @return The adjusted movement direction vector considering the right wall boundary.
     */
    private Vector2 validateRightMovementDir(Vector2 movementDir) {
        if (this.getTopLeftCorner().x() + this.width > this.rightWallX) {
            return Vector2.ZERO;
        }
        return movementDir;
    }

    /**
     * set disappearing timer - after 4 collisions
     * used only for second paddle
     *
     * @param gameManager the game manager
     */
    public void setDisappearingTimer(BrickerGameManager gameManager){
        this.gameManager = gameManager;
        disappearingTimer = true;
    }
}
