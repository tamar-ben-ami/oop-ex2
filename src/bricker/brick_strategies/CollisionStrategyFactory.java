package bricker.brick_strategies;

import bricker.BrickerGameManager;

import java.util.Random;

/**
 * Factory of CollisionStrategy
 */

public class CollisionStrategyFactory {
    // TODO check!!
    private static final String[] SPECIAL_STRATEGIES = {"life"};
//    private static final String[] SPECIAL_STRATEGIES = {"balls", "camera", "life", "paddle", "double"};

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
        int randomValue = rand.nextInt(SPECIAL_STRATEGIES.length);
        return buildCollisionStrategy(SPECIAL_STRATEGIES[randomValue], brickerGameManager);
    }

    public static CollisionStrategy buildCollisionStrategy(String type, BrickerGameManager brickerGameManager) {
        return switch (type) {
            case "basic" -> new BasicCollisionStrategy(brickerGameManager);
            case "balls" -> new BallsCollisionStrategy(brickerGameManager);
            case "camera" -> new CameraCollisionStrategy(brickerGameManager);
            case "life" -> new LifeCollisionStrategy(brickerGameManager);
            case "paddle" -> new PaddleCollisionStrategy(brickerGameManager);
            case "double" -> new DoubleCollisionStrategy(brickerGameManager);
            default -> null;
        };
    }
}
