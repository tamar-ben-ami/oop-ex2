package bricker;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.LifeCounter;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 35;
    private static final double BALL_SPEED = 350;
    private static final int NUM_OF_BRICKS_ROWS = 7;
    private static final int NUM_OF_BRICKS_COLS = 8;
    private static final int BRICK_HEIGHT = 15;
    private static final String BALL_TAG = "Ball";
    public static final String YOU_LOSE = "You Lose!";
    public static final String PLAY_AGAIN = " Play again?";
    public static final String YOU_WIN = "You win!";
    public static final int ZERO = 0;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Paddle paddle;
    private Brick[] bricks;
    private BasicCollisionStrategy basicCollisionStrategy;
    private int numOfBricksRows = NUM_OF_BRICKS_ROWS;
    private int numOfBricksCols = NUM_OF_BRICKS_COLS;
    private ImageReader imageReader;
    private LifeCounter lifeCounter;
    private Counter bricksCounter;


    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.imageReader = imageReader;
        this.windowController = windowController;
        //initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        createBackground();
        //create ball
        createBall(soundReader, windowController);

        //create paddles
        createUserPaddle(inputListener, windowDimensions);

        //create borders
        createBorders(windowDimensions);
        createBricks(windowDimensions);

        createLifeCounter(windowDimensions);
    }

    private void createLifeCounter(Vector2 windowDimensions) {
        lifeCounter = new LifeCounter(imageReader, windowDimensions, gameObjects());
        lifeCounter.addToGameObjects();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();

        String prompt = "";
        if(ballHeight < 0) {
            //we lost
            prompt = YOU_WIN;
        }
        if(ballHeight > windowDimensions.y()) {
            lifeCounter.decreaseLife();
            if (lifeCounter.isAlive()) {
                resetSettings();
            }
            else {
                prompt = YOU_LOSE;
            }
        }
        if (bricksCounter.value() == ZERO) {
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

    private void createBackground() {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", true);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBall(SoundReader soundReader, WindowController windowController) {
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5F));
        gameObjects().addGameObject(ball);

        setRandomVelocity();
    }

    private void setRandomVelocity() {
        float ballVelX = (float) BALL_SPEED;
        float ballVelY = (float) BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean())
            ballVelX *= -1;
        if(rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    private void createUserPaddle(UserInputListener inputListener, Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage(
                "assets/paddle.png", false);
        paddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                this.windowDimensions.x() - BORDER_WIDTH,
                BORDER_WIDTH);

        paddle.setCenter(
                new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(paddle);
    }


    private void createBorders(Vector2 windowDimensions) {
        Renderable borderImage = imageReader.readImage(
                "assets/brick.png", false);
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        borderImage)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x()-BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        borderImage)
        );
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(windowDimensions.x(), BORDER_WIDTH),
                        borderImage)
        );
    }

    private void createBricks(Vector2 windowDimensions) {
        Renderable brickImage =
                imageReader.readImage("assets/brick.png", false);
        basicCollisionStrategy = new BasicCollisionStrategy(gameObjects(), BALL_TAG);

        bricks = new Brick[numOfBricksCols * numOfBricksRows];
        bricksCounter = new Counter(numOfBricksCols * numOfBricksRows);
        float brickWidth = (windowDimensions.x() - BORDER_WIDTH * 2) / numOfBricksCols;
        for (int row = 0; row < numOfBricksRows; row++) {
            for (int col = 0; col < numOfBricksCols; col++) {
                createBrick(row, col, brickWidth, brickImage);
            }
        }
    }

    private void createBrick(int row, int col, float brickWidth, Renderable brickImage) {
        bricks[(row * numOfBricksCols) + col] =
                new Brick(new Vector2(BORDER_WIDTH + brickWidth * col, BORDER_WIDTH + BRICK_HEIGHT * row),
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage, basicCollisionStrategy, bricksCounter);
        gameObjects().addGameObject(bricks[(row * numOfBricksCols) + col]);
    }

    private void resetSettings() {
        for (int row = 0; row < numOfBricksRows; row++) {
            for (int col = 0; col < numOfBricksCols; col++) {
                gameObjects().removeGameObject(bricks[(row * numOfBricksCols) + col]);
            }
        }
        createBricks(windowDimensions);
        paddle.setCenter(
                new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        ball.setCenter(windowDimensions.mult(0.5F));
        setRandomVelocity();
    }

    public static void main(String[] args) {

        BrickerGameManager b = new BrickerGameManager(
                "Bouncing Ball",
                new Vector2(700, 500));
        if (args.length >= 2) {
            b.numOfBricksCols = Integer.parseInt(args[0]);
            b.numOfBricksRows = Integer.parseInt(args[1]);
        }
        b.run();
    }
}
