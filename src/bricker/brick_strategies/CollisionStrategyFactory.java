package bricker.brick_strategies;

import bricker.BrickerGameManager;

import java.util.Random;

/**
 * Factory of CollisionStrategy
 */

public class CollisionStrategyFactory {
    private static final String[] SPEICAL_STRATEGIES = {"basic", "balls", "camera", "life", "paddle", "double"};

    // returns a collisionStrategy randomly based on probabilities
    public static CollisionStrategy getRandomCollisionStrategy(BrickerGameManager brickerGameManager,
                                                               String ballTag) {
        Random rand = new Random();
        int randomValue = rand.nextInt(2);
        if (randomValue == 0) {
            return buildCollisionStrategy("basic", brickerGameManager, ballTag);
        } else {
            return getSpeicalCollisionStrategy(brickerGameManager, ballTag);
        }
    }

    public static CollisionStrategy getSpeicalCollisionStrategy(BrickerGameManager brickerGameManager,
                                                                 String ballTag){
        Random rand = new Random();
        int randomValue = rand.nextInt(SPEICAL_STRATEGIES.length);
        return buildCollisionStrategy(SPEICAL_STRATEGIES[randomValue], brickerGameManager, ballTag);
    }

    public static CollisionStrategy buildCollisionStrategy(String type, BrickerGameManager brickerGameManager,
                                                           String ballTag) {
        switch (type) {
            case "basic":
                return new BasicCollisionStrategy(brickerGameManager, ballTag);
            case "balls":
                return new BallsCollisionStrategy(brickerGameManager, ballTag);
            case "camera":
                return new CameraCollisionStrategy(brickerGameManager, ballTag);
            case "life":
                return new LifeCollisionStrategy(brickerGameManager, ballTag);
            case "paddle":
                return new PaddleCollisionStrategy(brickerGameManager, ballTag);
            case "double":
                return new DoubleCollisionStrategy(brickerGameManager, ballTag);
            default:
                return null;
        }
    }
}
