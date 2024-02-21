package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Represents a Brick GameObject in the Bricker game.
 *
 * @author tamar, yaara
 * @see GameObject
 */
public class Brick extends GameObject {
    private final Counter bricksCounter;
    private final CollisionStrategy collisionStrategy;
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy collisionStrategy,
                 Counter bricksCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.bricksCounter = bricksCounter;
        this.setTag("Brick");
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
        this.collisionStrategy.onCollision(this, other);
        this.bricksCounter.decrement();
    }
}
