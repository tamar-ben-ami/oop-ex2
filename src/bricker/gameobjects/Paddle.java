package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final double MOVEMENT_SPEED = 400;
    private UserInputListener inputListener;
    private float width;
    private double rightWallX;
    private double leftWallX;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener,
                  double rightWallX, double leftWallX) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.width = dimensions.x();
        this.rightWallX = rightWallX;
        this.leftWallX = leftWallX;
        this.setTag("UserPaddle");
    }

    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
    }

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

    private Vector2 validateLeftMovementDir(Vector2 movementDir) {
        if (this.getTopLeftCorner().x() < this.leftWallX) {
            return Vector2.ZERO;
        }
        return movementDir;
    }

    private Vector2 validateRightMovementDir(Vector2 movementDir) {
        if (this.getTopLeftCorner().x() + this.width > this.rightWallX) {
            return Vector2.ZERO;
        }
        return movementDir;
    }
}
