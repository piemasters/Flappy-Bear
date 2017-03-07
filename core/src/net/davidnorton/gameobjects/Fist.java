package net.davidnorton.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by David on 05/10/2014.
 */
public class Fist extends Scrollable {

    private Random r;

    public static final int VERTICAL_GAP = 45;
    public static final int FIST_WIDTH = 24;
    public static final int FIST_HEIGHT = 11;
    private float groundY;
    private Rectangle fistUp, fistDown, barUp, barDown;
    private boolean isScored = false;

    // When Fist's constructor is invoked, invoke the super (Scrollable) constructor
    public Fist(float x, float y, int width, int height, float scrollSpeed, float groundY) {

        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation
        r = new Random();
        fistUp = new Rectangle();
        fistDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();

        this.groundY = groundY;
    }

    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);

        // The set() method allows you to set the top left corner's x, y coordinates,
        // along with the width and height of the rectangle

        barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
                groundY - (position.y + height + VERTICAL_GAP));

        // Our fist width is 24. The bar is only 22 pixels wide. So the fist
        // must be shifted by 1 pixel to the left (so that the fist is centered
        // with respect to its bar).

        // This shift is equivalent to: (FIST_WIDTH - width) / 2
        fistUp.set(position.x - (FIST_WIDTH - width) / 2, position.y + height
                - FIST_HEIGHT, FIST_WIDTH, FIST_HEIGHT);
        fistDown.set(position.x - (FIST_WIDTH - width) / 2, barDown.y,
                FIST_WIDTH, FIST_HEIGHT);

    }

    public boolean collides(Bear bear) {
        if (position.x < bear.getX() + bear.getWidth()) {
            return (Intersector.overlaps(bear.getBoundingCircle(), barUp)
                    || Intersector.overlaps(bear.getBoundingCircle(), barDown)
                    || Intersector.overlaps(bear.getBoundingCircle(), fistUp) || Intersector
                    .overlaps(bear.getBoundingCircle(), fistDown));
        }
        return false;
    }

    @Override
    public void reset(float newX) {

        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        height = r.nextInt(90) + 15;

        isScored = false;
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public Rectangle getFistUp() {
        return fistUp;
    }

    public Rectangle getFistDown() {
        return fistDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }

}
