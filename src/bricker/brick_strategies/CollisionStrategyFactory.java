package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.util.Counter;
import java.util.Random;

/**
 * Factory of CollisionStrategy
 *
 * @author tamar, yaara
 * @see CollisionStrategy
 */
public class CollisionStrategyFactory {
    /**
     * default constructor of the class
     */
    public CollisionStrategyFactory() {
    }

    private static final String[] SPECIAL_STRATEGIES = {"balls", "camera", "life", "paddle", "double"};


    /**
     * build a random collision strategy, half probability for basic strategy and half for special strategy
     *
     * @param brickerGameManager the game manager
     * @return a random collision strategy
     */
    public static CollisionStrategy getRandomCollisionStrategy(BrickerGameManager brickerGameManager) {
        BasicCollisionStrategy strategy = new BasicCollisionStrategy(brickerGameManager);
        Random rand = new Random();
        int randomValue = rand.nextInt(2);
        if (randomValue == 0) {
            return strategy;
        } else {
            return addSpecialStrategy(strategy, new Counter());
        }
    }

    /**
     * build special collision strategy with uniform probability
     *
     * @param strategy the basic strategy
     * @param numOfDouble allowed only one double decorator (so it will be 3 other strategies)
     * @return a random special collision strategy
     */
    private static CollisionStrategy addSpecialStrategy(CollisionStrategy strategy, Counter numOfDouble) {
        // else give random special strategy
        Random rand = new Random();
        int randomValue;
        // if more "double" decorator twice, do not random "double" decorator
        if (numOfDouble.value() >= Constants.MAX_DOUBLE_STRATEGIES) {
            randomValue = rand.nextInt(SPECIAL_STRATEGIES.length-1);
        }
        else {
            randomValue = rand.nextInt(SPECIAL_STRATEGIES.length);
        }
        return switch (SPECIAL_STRATEGIES[randomValue]) {
            case "balls" -> new BallsCollisionStrategy(strategy);
            case "camera" -> new CameraCollisionStrategy(strategy);
            case "life" -> new LifeCollisionStrategy(strategy);
            case "paddle" -> new PaddleCollisionStrategy(strategy);
            default -> {
                numOfDouble.increment();
                yield addSpecialStrategy(addSpecialStrategy(strategy, numOfDouble), numOfDouble);
            }
        };
    }
}
