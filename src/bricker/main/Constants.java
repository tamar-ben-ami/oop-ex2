package bricker.main;
import danogl.util.Vector2;

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
    /**
     * heart dimension
     */
    public static final Vector2 HEART_DIMENSION = new Vector2(30, 30);
    /**
     * window title
     */
    public static final String WINDOW_TITLE = "Bouncing Ball";
    /**
     * Background image
     */
    public static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
    /**
     * Ball image
     */
    public static final String BALL_IMAGE = "assets/ball.png";
    /**
     * Pucks image
     */
    public static final String PUCKS_IMAGE = "assets/mockBall.png";
    /**
     * Ball collision sound
     */
    public static final String BALL_COLLISION_SOUND = "assets/blop_cut_silenced.wav";
    /**
     * Paddle collision sound
     */
    public static final String PADDLE_IMAGE = "assets/paddle.png";
    /**
     * Paddle collision sound
     */
    public static final String BORDER_IMAGE = "assets/brick.png";
    /**
     * Brick image
     */
    public static final String BRICK_IMAGE = "assets/brick.png";
    /**
     * Brick collision sound
     */
    public static final String BALL_TAG = "Ball";
    /**
     * Puck tag
     */
    public static final String PUCK_TAG = "Puck";
    /**
     * Paddle tag
     */
    public static final String YOU_LOSE = "You Lose!";
    /**
     * Play again?
     */
    public static final String PLAY_AGAIN = " Play again?";
    /**
     * You win!
     */
    public static final String YOU_WIN = "You win!";
    /**
     * Zero constance
     */
    public static final int ZERO = 0;

}
