package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollisionStrategyFactory;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Constances for BrickerGameManager.
 *
 * @author tamar, yaara
 */
public class Constants {

    /**
     * Window width
     */
    public static final int WINDOW_WIDTH = 700;
    /**
     * Window height
     */
    public static final int WINDOW_HEIGHT = 500;
    /**
     * Border width
     */
    public static final int BORDER_WIDTH = 10;
    /**
     * Paddle height
     */
    public static final int PADDLE_HEIGHT = 20;
    /**
     * Paddle width
     */
    public static final int PADDLE_WIDTH = 100;
    /**
     * Ball radius
     */
    public static final int BALL_RADIUS = 35;
    /**
     * Brick height
     */
    public static final int BRICK_HEIGHT = 15;

    /**
     * Number of bricks rows
     */
    public static final int NUM_OF_BRICKS_ROWS = 7;
    /**
     * Number of bricks columns
     */
    public static final int NUM_OF_BRICKS_COLS = 8;

    /**
     * Default paddle y coordinate
     */
    public static final int DEFAULT_PADDLE_Y = 30;

    /**
     * Camera zoom factor
     */
    public static final float CAMERA_ZOOM_FACTOR = 1.2f;
    /**
     * Puck radius factor
     */
    public static final float PUCK_RADIUS_FACTOR = 3/4f;
    public static final Vector2 HEART_DIMENSION = new Vector2(30, 30);
    public static final String WINDOW_TITLE = "Bouncing Ball";
    public static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
    public static final String BALL_IMAGE = "assets/ball.png";
    public static final String PUCKS_IMAGE = "assets/mockBall.png";
    public static final String BALL_COLLISION_SOUND = "assets/blop_cut_silenced.wav";
    public static final String PADDLE_IMAGE = "assets/paddle.png";
    public static final String BORDER_IMAGE = "assets/brick.png";
    public static final String BRICK_IMAGE = "assets/brick.png";
    public static final String BALL_TAG = "Ball";
    public static final String PUCK_TAG = "Puck";
    public static final String YOU_LOSE = "You Lose!";
    public static final String PLAY_AGAIN = " Play again?";
    public static final String YOU_WIN = "You win!";
    public static final int ZERO = 0;

}
