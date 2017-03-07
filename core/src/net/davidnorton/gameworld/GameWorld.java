package net.davidnorton.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import net.davidnorton.fbhelpers.AssetLoader;
import net.davidnorton.gameobjects.Bear;
import net.davidnorton.gameobjects.ScrollHandler;

/**
 * Created by David on 04/10/2014.
 */
public class GameWorld {

    private ScrollHandler scroller;
    private Bear bear;
    private Rectangle ground;
    private int score = 0;
    public enum GameState {MENU, READY, RUNNING, GAMEOVER, HIGHSCORE}
    private GameState currentState;
    public int midPointY;
    private float runTime = 0;

    public GameWorld(int midPointY) {
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        bear = new Bear(33, midPointY - 5, 17, 12);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 136, 11);
    }

    public void update(float delta) {

        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;

            default:
                break;
        }
    }

    private void updateReady(float delta) {
        bear.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta) {

        // Add a delta cap so that if our game takes too long
        // to update, we will not break our collision detection.

        if (delta > .15f) {
            delta = .15f;
        }

        bear.update(delta);
        scroller.update(delta);

        if (scroller.collides(bear) && bear.isAlive()) {
            scroller.stop();
            bear.die();
            AssetLoader.dead.play();
        }

        if (Intersector.overlaps(bear.getBoundingCircle(), ground)) {
            scroller.stop();
            bear.die();
            bear.decelerate();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public Bear getBear() {
        return bear;
    }

    public int getMidPointY() {
        return midPointY;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        bear.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
}
