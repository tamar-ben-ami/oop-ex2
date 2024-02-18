package bricker.gameobjects;

import bricker.BrickerGameManager;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class LifeGift extends GameObject {
    private final BrickerGameManager gameManager;
    private static final Vector2 LIFE_GIFT_VELOCITY = new Vector2(0, 100f);


    public LifeGift(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.gameManager = gameManager;
        this.setTag("Brick");
        this.setVelocity(LIFE_GIFT_VELOCITY);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getTopLeftCorner().y() > this.gameManager.getWindowY()) {
            System.out.println("IM OUT");
            this.gameManager.removeGameObject(this);
            System.out.println("removed");
        }
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals("mainPaddle")) {
            this.gameManager.removeGameObject(this);
            this.gameManager.getLifeCounter().increaseLife();
            System.out.println("MORE LIFEEE");
        }
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return !gameManager.isMainBall(other);
    }
}
