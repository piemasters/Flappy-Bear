package net.davidnorton.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import net.davidnorton.TweenAccessors.Value;
import net.davidnorton.TweenAccessors.ValueAccessor;
import net.davidnorton.fbhelpers.AssetLoader;
import net.davidnorton.fbhelpers.InputHandler;
import net.davidnorton.gameobjects.Bear;
import net.davidnorton.gameobjects.Fist;
import net.davidnorton.gameobjects.Grass;
import net.davidnorton.gameobjects.ScrollHandler;
import net.davidnorton.ui.SimpleButton;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by David on 04/10/2014.
 */
public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;

    // Game Objects
    private Bear bear;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Fist fist1, fist2, fist3;

    // Game Assets
    private TextureRegion bg, grass;
    private Animation bearAnimation;
    private TextureRegion bearMid, bearDown, bearUp;
    private TextureRegion fistUp, fistDown, bar;

    // Tween stuff
    private TweenManager manager;
    private Value alpha = new Value();

    // Buttons
    private List<SimpleButton> menuButtons;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;

        // We are setting the instance variables' values to be that of the
        // parameters passed in from GameScreen.
        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, 204);

        batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        // Call helper methods to initialize instance variables
        initGameObjects();
        initAssets();
        setupTweens();
    }

    private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
                .start(manager);
    }

    private void initGameObjects() {
        bear = myWorld.getBear();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        fist1 = scroller.getFist1();
        fist2 = scroller.getFist2();
        fist3 = scroller.getFist3();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        bearAnimation = AssetLoader.bearAnimation;
        bearMid = AssetLoader.bear;
        bearDown = AssetLoader.bearDown;
        bearUp = AssetLoader.bearUp;
        fistUp = AssetLoader.fistUp;
        fistDown = AssetLoader.fistDown;
        bar = AssetLoader.bar;
    }

    private void drawGrass() {
        // Draw the grass
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawFists() {

        batcher.draw(fistUp, fist1.getX() - 1,
                fist1.getY() + fist1.getHeight() - 14, 24, 14);
        batcher.draw(fistDown, fist1.getX() - 1,
                fist1.getY() + fist1.getHeight() + 45, 24, 14);

        batcher.draw(fistUp, fist2.getX() - 1,
                fist2.getY() + fist2.getHeight() - 14, 24, 14);
        batcher.draw(fistDown, fist2.getX() - 1,
                fist2.getY() + fist2.getHeight() + 45, 24, 14);

        batcher.draw(fistUp, fist3.getX() - 1,
                fist3.getY() + fist3.getHeight() - 14, 24, 14);
        batcher.draw(fistDown, fist3.getX() - 1,
                fist3.getY() + fist3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {

        batcher.draw(bar, fist1.getX(), fist1.getY(), fist1.getWidth(),
                fist1.getHeight());
        batcher.draw(bar, fist1.getX(), fist1.getY() + fist1.getHeight() + 45,
                fist1.getWidth(), midPointY + 66 - (fist1.getHeight() + 45));

        batcher.draw(bar, fist2.getX(), fist2.getY(), fist2.getWidth(),
                fist2.getHeight());
        batcher.draw(bar, fist2.getX(), fist2.getY() + fist2.getHeight() + 45,
                fist2.getWidth(), midPointY + 66 - (fist2.getHeight() + 45));

        batcher.draw(bar, fist3.getX(), fist3.getY(), fist3.getWidth(),
                fist3.getHeight());
        batcher.draw(bar, fist3.getX(), fist3.getY() + fist3.getHeight() + 45,
                fist3.getWidth(), midPointY + 66 - (fist3.getHeight() + 45));
    }

    private void drawBearCentered(float runTime) {
        batcher.draw(bearAnimation.getKeyFrame(runTime), 59, bear.getY() - 15,
                bear.getWidth() / 2.0f, bear.getHeight() / 2.0f,
                bear.getWidth(), bear.getHeight(), 1, 1, bear.getRotation());
    }

    private void drawBear(float runTime) {

        if (bear.shouldntFlap()) {
            batcher.draw(bearMid, bear.getX(), bear.getY(),
                    bear.getWidth() / 2.0f, bear.getHeight() / 2.0f,
                    bear.getWidth(), bear.getHeight(), 1, 1, bear.getRotation());

        } else {
            batcher.draw(bearAnimation.getKeyFrame(runTime), bear.getX(),
                    bear.getY(), bear.getWidth() / 2.0f,
                    bear.getHeight() / 2.0f, bear.getWidth(), bear.getHeight(),
                    1, 1, bear.getRotation());
        }

    }

    private void drawMenuUI() {
        batcher.draw(AssetLoader.fbLogo, 136 / 2 - 56, midPointY - 50,
                AssetLoader.fbLogo.getRegionWidth() / 1.2f,
                AssetLoader.fbLogo.getRegionHeight() / 1.2f);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }

    public void render(float delta, float runTime) {

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(79 / 255.0f, 206 / 255.0f, 251 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();

        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        batcher.draw(bg, 0, midPointY + 23, 136, 43);

        // 1. Draw Grass
        drawGrass();

        // 2. Draw Pipes
        drawPipes();

        // The bird needs transparency, so we enable that again.
        batcher.enableBlending();

        // 3. Draw Skulls (requires transparency)
        drawFists();

        // Draw bird at its coordinates. Retrieve the Animation object from AssetLoader
        // Pass in the runTime variable to get the current frame.
        if (myWorld.isRunning()) {
            drawBear(runTime);
            drawScore();
        } else if (myWorld.isReady()) {
            drawBear(runTime);
            drawScore();
        } else if (myWorld.isMenu()) {
            drawBearCentered(runTime);
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
            drawBear(runTime);
            drawScore();
        } else if (myWorld.isHighScore()) {
            drawBear(runTime);
            drawScore();
        }

        batcher.end();
        drawTransition(delta);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }

}
