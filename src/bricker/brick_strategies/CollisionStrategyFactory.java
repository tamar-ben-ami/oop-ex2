package bricker.brick_strategies;

import bricker.BrickerGameManager;

import java.util.Random;

/**
 * Factory of CollisionStrategy
 */

public class CollisionStrategyFactory {
    private static final String[] SPEICAL_STRATEGIES = {"basic", "balls", "camera", "life", "paddle", "double"};

    // returns a collisionStrategy randomly based on probabilities
    public static CollisionStrategy getRandomCollisionStrategy(BrickerGameManager brickerGameManager) {
        Random rand = new Random();
        int randomValue = rand.nextInt(2);
        if (randomValue == 0) {
            return buildCollisionStrategy("basic", brickerGameManager);
        } else {
            return getSpeicalCollisionStrategy(brickerGameManager);
        }
    }

    public static CollisionStrategy getSpeicalCollisionStrategy(BrickerGameManager brickerGameManager){
        Random rand = new Random();
        int randomValue = rand.nextInt(SPEICAL_STRATEGIES.length);
        return buildCollisionStrategy(SPEICAL_STRATEGIES[randomValue], brickerGameManager);
    }

    public static CollisionStrategy buildCollisionStrategy(String type, BrickerGameManager brickerGameManager) {
        switch (type) {
            case "basic":
                return new BasicCollisionStrategy(brickerGameManager);
            case "balls":
                return new BallsCollisionStrategy(brickerGameManager);
            case "camera":
                return new CameraCollisionStrategy(brickerGameManager);
            case "life":
                return new LifeCollisionStrategy(brickerGameManager);
            case "paddle":
                return new PaddleCollisionStrategy(brickerGameManager);
            case "double":
                return new DoubleCollisionStrategy(brickerGameManager);
            default:
                return null;
        }
    }
}
