package bricker.brick_strategies;

import bricker.main.BrickerGameManager;

import java.util.Random;

/**
 * Factory of CollisionStrategy
 *
 * @author tamar, yaara
 * @see CollisionStrategy
 */
public class CollisionStrategyFactory {
    private static final String[] SPECIAL_STRATEGIES = {"balls", "camera", "life", "paddle", "double"};

    // returns a collisionStrategy randomly based on probabilities

    /**
     * build a random collision strategy, half probability for basic strategy and half for special strategy
     * @param brickerGameManager the game manager
     * @return a random collision strategy
     */
    public static CollisionStrategy getRandomCollisionStrategy(BrickerGameManager brickerGameManager) {
        Random rand = new Random();
        int randomValue = rand.nextInt(2);
        if (randomValue == 0) {
            return buildCollisionStrategy("basic", brickerGameManager);
        } else {
            return getSpeicalCollisionStrategy(brickerGameManager);
        }
    }

    /**
     * build special collision strategy with uniform probability
     * @param brickerGameManager game manager instance
     * @return a random special collision strategy
     */
    public static CollisionStrategy getSpeicalCollisionStrategy(BrickerGameManager brickerGameManager){
        Random rand = new Random();
        int randomValue = rand.nextInt(SPECIAL_STRATEGIES.length);
        return buildCollisionStrategy(SPECIAL_STRATEGIES[randomValue], brickerGameManager);
    }

    /**
     * builds a collision strategy based on the specified type.
     * @param type  the type of collision strategy to build
     * @param brickerGameManager the game manager
     * @return a collision strategy instance
     */
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
