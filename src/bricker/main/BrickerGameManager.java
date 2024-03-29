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
 * The BrickerGameManager class is an extension of GameManager.
 * It is the main class of the Bricker game, and is responsible for
 * initializing the game, creating the game objects, and updating the game.
 * It also contains the main function that runs the game.
 * The game is a simple game where the player controls a paddle to prevent
 * a ball from falling off the screen. The ball bounces off the paddle and
 * the walls, and when it hits a brick, the brick is removed from the game.
 * The player has a limited number of lives, and the game ends when the ball
 * falls off the screen or when all the bricks are removed.
 *
 *
 * fkdjkfdlk
 * @author tamar, yaara
 * @see GameManager
 */
public class BrickerGameManager extends GameManager {

    // Instance constants
    private Ball ball;
    private WindowController windowController;
    private Paddle paddle;
    private Paddle secondPaddle;
    private boolean hasSecondPaddle;
    private Brick[] bricks;
    private int numOfBricksRows = Constants.NUM_OF_BRICKS_ROWS;
    private int numOfBricksCols = Constants.NUM_OF_BRICKS_COLS;
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
        lifeCounter = new LifeCounter(imageReader, getWindowDim(), this);
    }

    /**
     * This function creates a life gift object and saves it in the game manager instance
     * @param center center coordinate of the new life gift coordinate
     */
    public void createLifeGift(Vector2 center) {
        ImageRenderable lifeGiftImage = imageReader.readImage(Constants.HEART_IMAGE, true);
        GameObject lifeGift = new LifeGift(new Vector2(center.x(),
                center.y()), Constants.HEART_DIMENSION, lifeGiftImage, this);
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
            prompt = Constants.YOU_WIN;
        }
        if(ballHeight > getWindowY()) {
            lifeCounter.decreaseLife();
            if (lifeCounter.isAlive()) {
                ball.reset();
            }
            else {
                prompt = Constants.YOU_LOSE;
            }
        }
        if (bricksCounter.value() == Constants.ZERO | userInputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = Constants.YOU_WIN;
        }
        if(!prompt.isEmpty()) {
            prompt += Constants.PLAY_AGAIN;
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
        Renderable backgroundImage = imageReader.readImage(Constants.BACKGROUND_IMAGE, true);
        GameObject background = new GameObject(Vector2.ZERO, getWindowDim(), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * This function creates a ball object and saves it in the game manager instance
     */
    private void createBall() {
        Renderable ballImage =
                imageReader.readImage(Constants.BALL_IMAGE, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_COLLISION_SOUND);
        ball = new Ball(
                getWindowDim().mult(0.5F),
                new Vector2(Constants.BALL_RADIUS, Constants.BALL_RADIUS),
                ballImage,
                collisionSound,
                Constants.BALL_TAG,
                this);
        gameObjects().addGameObject(ball);
    }

    /**
     * This function creates pucks objects and saves it in the game manager instance
     */
    public void createPucks(Vector2 center, int numOfPucks) {
        Renderable puckImage = imageReader.readImage(Constants.PUCKS_IMAGE, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_COLLISION_SOUND);
        Ball puck;
        for (int i = 0; i < numOfPucks; i++){
            puck = new Ball(
                    center,
                    new Vector2((float) (Constants.BALL_RADIUS)*Constants.PUCK_RADIUS_FACTOR,
                            (float) (Constants.BALL_RADIUS)*Constants.PUCK_RADIUS_FACTOR),
                    puckImage,
                    collisionSound,
                    Constants.BALL_TAG,
                    this);
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
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IMAGE, false);
        paddle = new Paddle(
                new Vector2(getWindowX()/2, (int) (getWindowY()-Constants.DEFAULT_PADDLE_Y)),
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage,
                userInputListener,
                getWindowX() - Constants.BORDER_WIDTH,
                Constants.BORDER_WIDTH);
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
            Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IMAGE, false);
            secondPaddle = new Paddle(
                    new Vector2(getWindowX()/2, (int) (getWindowY()/2)),
                    new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                    paddleImage,
                    userInputListener,
                    getWindowX() - Constants.BORDER_WIDTH,
                    Constants.BORDER_WIDTH);
            gameObjects().addGameObject(secondPaddle);
            secondPaddle.setDisappearingTimer(this);
    }

    /**
     * This function creates borders objects and saves it in the game manager instance
     */
    private void createBorders() {
        Renderable borderImage = imageReader.readImage(Constants.BORDER_IMAGE, false);
        GameObject leftBorder = new GameObject(
                Vector2.ZERO,
                new Vector2(Constants.BORDER_WIDTH, getWindowY()),
                borderImage);
        GameObject rightBorder = new GameObject(
                new Vector2(getWindowX()-Constants.BORDER_WIDTH, 0),
                new Vector2(Constants.BORDER_WIDTH, getWindowY()),
                borderImage);
        GameObject upperBorder = new GameObject(
                Vector2.ZERO,
                new Vector2(getWindowX(), Constants.BORDER_WIDTH),
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
                imageReader.readImage(Constants.BRICK_IMAGE, false);

        bricks = new Brick[numOfBricksCols * numOfBricksRows];
        bricksCounter = new Counter(numOfBricksCols * numOfBricksRows);
        float brickWidth = (getWindowX() - Constants.BORDER_WIDTH * 2) / numOfBricksCols;
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
     * @param brickWidth the width of a single brick
     * @param brickImage the image of the brick
     */
    private void createBrick(int row, int col, float brickWidth, Renderable brickImage) {
        CollisionStrategy collisionStrategy =
                CollisionStrategyFactory.getRandomCollisionStrategy(this);
        bricks[(row * numOfBricksCols) + col] =
                new Brick(new Vector2(Constants.BORDER_WIDTH + brickWidth * col,
                        Constants.BORDER_WIDTH + Constants.BRICK_HEIGHT * row),
                        new Vector2(brickWidth, Constants.BRICK_HEIGHT),
                        brickImage,  collisionStrategy, bricksCounter);
        gameObjects().addGameObject(bricks[(row * numOfBricksCols) + col]);
    }

    /**
     * This function creates a camera and sets the timer of it active
     */
    public void setCameraBallZoom() {
        if (camera() == null) {
            setCamera(
                    new Camera(
                            ball, //object to follow
                            Vector2.ZERO, //follow the center of the object
                            getWindowDim().mult(Constants.CAMERA_ZOOM_FACTOR), //widen the frame a bit
                            getWindowDim() //share the window dimensions
                        )
                    );
        }
        ball.setZoomTimer(this);
    }

    /**
     * This getter reutrns the dimensions of the game window
     * @return Vector2 of the window dimensions
     */
    public Vector2 getWindowDim(){
        return windowController.getWindowDimensions();
    }

    /**
     * This getter returns the x window dim
     * @return float of the x window dim
     */
    public float getWindowX(){
        return getWindowDim().x();
    }

    /**
     * This getter returns the y window dim
     * @return float of the y window dim
     */
    public float getWindowY(){
        return getWindowDim().y();
    }

    /**
     * This function returns true if the given gameObject is the main ball
     * @param gameObject the game object to check if is main ball
     * @return is the object is main ball
     */
    public boolean isMainBall(GameObject gameObject) {
        return gameObject.equals(ball);
    }

    /**
     * This function returns true if the given gameObject is the main paddle
     * @param gameObject the game object to check if is main paddle
     * @return is the object is main paddle
     */
    public boolean isMainPaddle(GameObject gameObject) {
        return gameObject.equals(paddle);
    }

    /**
     * Main function that runs the game
     * @param args num of row, num of col
     */
    public static void main(String[] args) {

        BrickerGameManager b = new BrickerGameManager(Constants.WINDOW_TITLE,
                new Vector2(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        if (args.length >= 2) {
            b.numOfBricksCols = Integer.parseInt(args[0]);
            b.numOfBricksRows = Integer.parseInt(args[1]);
        }
        b.run();
    }

    /**
     * This function removes a given game object of the GameObjectCollection
     * @param gameObject the game object we want to remove
     */
    public void removeGameObject(GameObject gameObject, int layerId) {
        gameObjects().removeGameObject(gameObject, layerId);
        if (gameObject.equals(secondPaddle)) {
            hasSecondPaddle = false;
        }
    }

    /**
     * This function removes a given game object of the GameObjectCollection
     * @param gameObject the game object we want to remove
     */
    public void removeGameObject(GameObject gameObject) {
        removeGameObject(gameObject, Layer.DEFAULT);
    }

    /**
     * This function adds a given game object of the GameObjectCollection
     * @param gameObject the game object we want to
     */
    public void addGameObject(GameObject gameObject, int layerId) {
        gameObjects().addGameObject(gameObject, layerId);
    }
}
