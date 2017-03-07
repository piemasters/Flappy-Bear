package net.davidnorton.flappybear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import net.davidnorton.fbhelpers.AssetLoader;
import net.davidnorton.screens.GameScreen;
import net.davidnorton.screens.SplashScreen;

public class FBGame extends Game {

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
