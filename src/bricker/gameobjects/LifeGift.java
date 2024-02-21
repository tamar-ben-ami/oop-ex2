package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a LifeGift GameObject in the Bricker game.
 */
public class LifeGift extends GameObject {
    private final BrickerGameManager gameManager;
    private static final Vector2 LIFE_GIFT_VELOCITY = new Vector2(0, 100f);

    /**
     * This constructs the Life Gift object
     * @param topLeftCorner coordinate where the life gift is created
     * @param dimensions dimenstions of the life gift
     * @param renderable the renderable of the life gift
     * @param gameManager the manager of the game
     */
    public LifeGift(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.setVelocity(LIFE_GIFT_VELOCITY);
    }

    /**
     * Updates the life gift position and validates it's not out of the boundaries of game
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getTopLeftCorner().y() > this.gameManager.getWindowY()) {
            this.gameManager.removeGameObject(this);
        }
    }

    /**
     * Handles the event when a collision occurs with another GameObject.
     *
     * @param other     The GameObject with which the collision occurred.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals("mainPaddle")) {
            this.gameManager.removeGameObject(this);
            this.gameManager.getLifeCounter().increaseLife();
        }
    }

    /**
     * This function is overriding the gameObject shouldCollideWith,
     * and validates the life gift does not collide with the main ball
     * @param other object to collide with
     * @return
    true if the objects should collide. This does not guarantee a collision would actually collide if
    they overlap, since the other object has to confirm this one as well.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return !gameManager.isMainBall(other);
    }
}
