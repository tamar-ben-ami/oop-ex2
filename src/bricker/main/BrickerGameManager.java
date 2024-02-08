package bricker.main;

import danogl.GameManager;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager {


    public static void main(String[] args) {
        new GameManager("hi", new Vector2(700, 500)).run();
        System.out.println("Ojkjj ");
    }
}
