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
 * Represents a BrickerGameManager GameManager in the Bricker game.
 *
 * @author tamar, yaara
 * @see GameManager
 */
public class BrickerGameManager extends GameManager {

    // Game objects size
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    public static final int BALL_RADIUS = 35;
    private static final int BRICK_HEIGHT = 15;

    // Number of bricks
    private static final int NUM_OF_BRICKS_ROWS = 7;
    private static final int NUM_OF_BRICKS_COLS = 8;

    // Paddle position
    private static final int DEFAULT_PADDLE_Y = 30;

    // Factor
    private static final float CAMERA_ZOOM_FACTOR = 1.2f;
    private static final float PUCK_RADIUS_FACTOR = 3/4f;
    private static final Vector2 HEART_DIMENSION = new Vector2(30, 30);

    // Strings
    public static final String WINDOW_TITLE = "Bouncing Ball";
    public static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
    public static final String BALL_IMAGE = "assets/ball.png";
    public static final String PUCKS_IMAGE = "assets/mockBall.png";
    public static final String BALL_COLLISION_SOUND = "assets/blop_cut_silenced.wav";
    public static final String PADDLE_IMAGE = "assets/paddle.png";
    public static final String BORDER_IMAGE = "assets/brick.png";
    public static final String BRICK_IMAGE = "assets/brick.png";
    private static final String BALL_TAG = "Ball";
    private static final String PUCK_TAG = "Puck";
    public static final String YOU_LOSE = "You Lose!";
    public static final String PLAY_AGAIN = " Play again?";
    public static final String YOU_WIN = "You win!";
    private static final int ZERO = 0;

    // Instance constants
    private Ball ball;
    private WindowController windowController;
    private Paddle paddle;
    private Paddle secondPaddle;
    private boolean hasSecondPaddle;
    private Brick[] bricks;
    private int numOfBricksRows = NUM_OF_BRICKS_ROWS;
    private int numOfBricksCols = NUM_OF_BRICKS_COLS;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private LifeCounter lifeCounter;
    private Counter bricksCounter;
    private UserInputListener userInputListener;

    /**
     * Constructs the game manager instance
     * @param windowTitle title of the window diaplayed
     * @param windowDimensions dimension of the window of the game
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * The method will be called once when a GameGUIComponent is created,
     * and again after every invocation of windowController.resetGame().
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     * @see ImageReader
     * @see SoundReader
     * @see UserInputListener
     * @see WindowController
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowController = windowController;
        this.userInputListener = inputListener;
        //initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        createBackground();
        createBall();
        createPaddle();
        createBorders();
        createBricks();
        createLifeCounter();
        hasSecondPaddle = false;
    }

    /**
     * This function creates a life counter object and saves it in the game manager instance
     */
    private void createLifeCounter() {
        lifeCounter = new LifeCounter(imageReader, getWindowDim(), gameObjects());
    }

    /**
     * This function creates a life gift object and saves it in the game manager instance
     * @param center center coordinate of the new life gift coordinate
     */
    public void createLifeGift(Vector2 center) {
        ImageRenderable lifeGiftImage = imageReader.readImage("assets/heart.png", true);
        GameObject lifeGift = new LifeGift(new Vector2(center.x(),
                center.y()), HEART_DIMENSION, lifeGiftImage, this);
        this.gameObjects().addGameObject(lifeGift);

    }

    /**
     * Updates the game manager and checks if game is over by now
     *
     * @param deltaTime The time passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * This function checks conditions for ending game and generating adjusted prompt if ended.
     */
    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();

        String prompt = "";
        if(ballHeight < 0) {
            prompt = YOU_WIN;
        }
        if(ballHeight > getWindowY()) {
            lifeCounter.decreaseLife();
            if (lifeCounter.isAlive()) {
                ball.reset();
            }
            else {
                prompt = YOU_LOSE;
            }
        }
        if (bricksCounter.value() == ZERO | userInputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = YOU_WIN;
        }
        if(!prompt.isEmpty()) {
            prompt += PLAY_AGAIN;
            if(windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
    }

    /**
     * This function creates background object and saves it in the game manager instance
     */
    private void createBackground() {
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMAGE, true);
        GameObject background = new GameObject(Vector2.ZERO, getWindowDim(), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * This function creates a ball object and saves it in the game manager instance
     */
    private void createBall() {
        Renderable ballImage =
                imageReader.readImage(BALL_IMAGE, true);
        Sound collisionSound = soundReader.readSound(BALL_COLLISION_SOUND);
        ball = new Ball(
                getWindowDim().mult(0.5F),
                new Vector2(BALL_RADIUS, BALL_RADIUS),
                ballImage,
                collisionSound,
                BALL_TAG);
        gameObjects().addGameObject(ball);
    }

    /**
     * This function creates pucks objects and saves it in the game manager instance
     */
    public void createPucks(Vector2 center, int numOfPucks) {
        Renderable puckImage = imageReader.readImage(PUCKS_IMAGE, true);
        Sound collisionSound = soundReader.readSound(BALL_COLLISION_SOUND);
        Ball puck;
        for (int i = 0; i < numOfPucks; i++){
            puck = new Ball(
                    center,
                    new Vector2((float) (BALL_RADIUS)*PUCK_RADIUS_FACTOR, (float) (BALL_RADIUS)*PUCK_RADIUS_FACTOR),
                    puckImage,
                    collisionSound,
                    PUCK_TAG);
            gameObjects().addGameObject(puck);
        }
    }

    /**
     * This function returns the life counter object
     * @return LifeCounter of instance
     */
    public LifeCounter getLifeCounter() {
        return lifeCounter;
    }

    /**
     * This function creates paddle object and saves it in the game manager instance
     */
    private void createPaddle() {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE, false);
        paddle = new Paddle(
                new Vector2(getWindowX()/2, (int) (getWindowY()-DEFAULT_PADDLE_Y)),
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                userInputListener,
                getWindowX() - BORDER_WIDTH,
                BORDER_WIDTH);
        paddle.setTag("mainPaddle");
        gameObjects().addGameObject(paddle);
    }

    /**
     * This function creates second paddle object and saves it in the game manager instance
     */
    public void createSecondPaddle(){
        // check if exists paddle
        if (hasSecondPaddle){return;}
            hasSecondPaddle = true;
            Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE, false);
            secondPaddle = new Paddle(
                    new Vector2(getWindowX()/2, (int) (getWindowY()/2)),
                    new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                    paddleImage,
                    userInputListener,
                    getWindowX() - BORDER_WIDTH,
                    BORDER_WIDTH);
            gameObjects().addGameObject(secondPaddle);
            secondPaddle.setDisappearingTimer(this);
    }

    /**
     * This function creates borders objects and saves it in the game manager instance
     */
    private void createBorders() {
        Renderable borderImage = imageReader.readImage(BORDER_IMAGE, false);
        GameObject leftBorder = new GameObject(
                Vector2.ZERO,
                new Vector2(BORDER_WIDTH, getWindowY()),
                borderImage);
        GameObject rightBorder = new GameObject(
                new Vector2(getWindowX()-BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, getWindowY()),
                borderImage);
        GameObject upperBorder = new GameObject(
                Vector2.ZERO,
                new Vector2(getWindowX(), BORDER_WIDTH),
                borderImage);

        gameObjects().addGameObject(rightBorder);
        gameObjects().addGameObject(leftBorder);
        gameObjects().addGameObject(upperBorder);
    }

    /**
     * This function creates bricks objects and saves it in the game manager instance
     */
    private void createBricks() {
        Renderable brickImage =
                imageReader.readImage(BRICK_IMAGE, false);

        bricks = new Brick[numOfBricksCols * numOfBricksRows];
        bricksCounter = new Counter(numOfBricksCols * numOfBricksRows);
        float brickWidth = (getWindowX() - BORDER_WIDTH * 2) / numOfBricksCols;
        for (int row = 0; row < numOfBricksRows; row++) {
            for (int col = 0; col < numOfBricksCols; col++) {

                createBrick(row, col, brickWidth, brickImage);
            }
        }
    }

    /**
     * This function creates a single brick object and adds it to the game object collection
     * @param row row of the brick
     * @param col column of the brick
     * @param brickWidth
     * @param brickImage
     */
    private void createBrick(int row, int col, float brickWidth, Renderable brickImage) {
        CollisionStrategy collisionStrategy =
                CollisionStrategyFactory.getRandomCollisionStrategy(this);
        bricks[(row * numOfBricksCols) + col] =
                new Brick(new Vector2(BORDER_WIDTH + brickWidth * col, BORDER_WIDTH + BRICK_HEIGHT * row),
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage,  collisionStrategy, bricksCounter);
        gameObjects().addGameObject(bricks[(row * numOfBricksCols) + col]);
    }

    /**
     * This function creates a camera and sets the timer of it active
     */
    public void cameraBallZoom() {
        setCamera(
                new Camera(
                        ball, //object to follow
                        Vector2.ZERO, //follow the center of the object
                        getWindowDim().mult(CAMERA_ZOOM_FACTOR), //widen the frame a bit
                        getWindowDim() //share the window dimensions
                )
        );
        ball.setZoomTimer(this);
    }

    /**
     * This getter reutrns the dimensions of the game window
     * @return
     */
    public Vector2 getWindowDim(){
        return windowController.getWindowDimensions();
    }

    /**
     * This getter returns the x coordinate of the point
     * @return
     */
    public float getWindowX(){
        return getWindowDim().x();
    }

    /**
     * This getter returns the y coordinate of the point
     * @return
     */
    public float getWindowY(){
        return getWindowDim().y();
    }

    /**
     * This funciton returns true if the given gameObject is the main ball
     * @param gameObject
     * @return
     */
    public boolean isMainBall(GameObject gameObject) {
        return gameObject.getTag().equals(BALL_TAG);
    }

    /**
     * This funciton returns true if the given gameObject is the main paddle
     * @param gameObject
     * @return
     */
    public boolean isMainPaddle(GameObject gameObject) {
        return gameObject.equals(paddle);
    }

    /**
     * Main function that runs the game
     * @param args
     */
    public static void main(String[] args) {

        BrickerGameManager b = new BrickerGameManager(WINDOW_TITLE,
                new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT));
        if (args.length >= 2) {
            b.numOfBricksCols = Integer.parseInt(args[0]);
            b.numOfBricksRows = Integer.parseInt(args[1]);
        }
        b.run();
    }

    /**
     * This function removes a given game object of the GameObjectCollection
     * @param gameObject
     */
    public void removeGameObject(GameObject gameObject) {
        // TODO: check if gameObject in gameObjects
        gameObjects().removeGameObject(gameObject);
        if (gameObject.equals(secondPaddle)) {
            hasSecondPaddle = false;
        }
        // TODO: if gameObject is brick, decrease bricksCounter
    }
}
