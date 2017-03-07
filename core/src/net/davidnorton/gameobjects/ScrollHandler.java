package net.davidnorton.gameobjects;

import net.davidnorton.fbhelpers.AssetLoader;
import net.davidnorton.gameworld.GameWorld;

public class ScrollHandler {

    // ScrollHandler will create all five objects that we need.
    private Grass frontGrass, backGrass;
    private Fist fist1, fist2, fist3;

    // ScrollHandler will use the constants below to determine
    // how fast we need to scroll and also determine
    // the size of the gap between each pair of fists.
    public static final int SCROLL_SPEED = -59;
    public static final int FIST_GAP = 49;

    private GameWorld gameWorld;

    // Constructor receives a float that tells us where we need to create our
    // Grass and Fist objects.
    public ScrollHandler(GameWorld gameWorld,float yPos) {
        this.gameWorld = gameWorld;
        frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);

        fist1 = new Fist(210, 0, 22, 60, SCROLL_SPEED, yPos);
        fist2 = new Fist(fist1.getTailX() + FIST_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        fist3 = new Fist(fist2.getTailX() + FIST_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
    }

    public void updateReady(float delta) {

        frontGrass.update(delta);
        backGrass.update(delta);

        // Same with grass
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());

        }
    }

    public void update(float delta) {

        // Update our objects
        frontGrass.update(delta);
        backGrass.update(delta);
        fist1.update(delta);
        fist2.update(delta);
        fist3.update(delta);

        // Check if any of the fists are scrolled left, and reset accordingly
        if (fist1.isScrolledLeft()) {
            fist1.reset(fist3.getTailX() + FIST_GAP);

        } else if (fist2.isScrolledLeft()) {
            fist2.reset(fist1.getTailX() + FIST_GAP);

        } else if (fist3.isScrolledLeft()) {
            fist3.reset(fist2.getTailX() + FIST_GAP);
        }

        // Same with grass
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }
    }

    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        fist1.stop();
        fist2.stop();
        fist3.stop();}

    // Return true if ANY fist hits the bear.
    public boolean collides(Bear bear) {

        if (!fist1.isScored() && fist1.getX() + (fist1.getWidth() / 2) < bear.getX() + bear.getWidth()) {
            addScore(1);
            fist1.setScored(true);
            AssetLoader.coin.play();
        } else if (!fist2.isScored() && fist2.getX() + (fist2.getWidth() / 2) < bear.getX() + bear.getWidth()) {
            addScore(1);
            fist2.setScored(true);
            AssetLoader.coin.play();
        } else if (!fist3.isScored()&& fist3.getX() + (fist3.getWidth() / 2) < bear.getX() + bear.getWidth()) {
            addScore(1);
            fist3.setScored(true);
            AssetLoader.coin.play();
        }

        return (fist1.collides(bear) || fist2.collides(bear) || fist3.collides(bear));
    }

    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

    public void onRestart() {
        frontGrass.onRestart(0, SCROLL_SPEED);
        backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
        fist1.onRestart(210, SCROLL_SPEED);
        fist2.onRestart(fist1.getTailX() + FIST_GAP, SCROLL_SPEED);
        fist3.onRestart(fist2.getTailX() + FIST_GAP, SCROLL_SPEED);
    }

    // The getters for our five instance variables
    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Fist getFist1() {
        return fist1;
    }

    public Fist getFist2() {
        return fist2;
    }

    public Fist getFist3() {
        return fist3;
    }

}
