package com.zomiren.memeory;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.zomiren.memeory.tween.SpriteAccessor;


/**
 * Created by Kaju on 05.10.15.
 */
public class SplashScreen implements Screen {


    private Sprite splashSprite;
    private Texture splashTexture;
    private SpriteBatch batch;
    private TweenManager tweenManager;
    Sound hornSound;
    MusicModule musicModule;

    MemeoryGame game;

    public SplashScreen(MemeoryGame game, MusicModule musicModule) {
        this.musicModule = musicModule;
        this.game = game;
        hornSound = Gdx.audio.newSound(Gdx.files.internal("horn.wav"));

    }


    @Override
    public void show() {

        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        splashTexture = new Texture(Gdx.files.internal("splash.jpg"));
        splashSprite = new Sprite(splashTexture);

        Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splashSprite, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
        Tween.to(splashSprite, SpriteAccessor.ALPHA, 3).target(0).delay(3).start(tweenManager);

        float delay = 0.3f;
        Timer.schedule(new Timer.Task() { // delayed sound play
            @Override
            public void run() {
                hornSound.play();
            }
        }, delay);


        delay = 6.2f; // delayed game something
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game, musicModule));
                hornSound.stop();
            }
        }, delay);

/*        splashSprite.setSize(Gdx.graphics.getWidth() - 70, Gdx.graphics.getHeight() - 45);
        splashSprite.setPosition(35, 23);*/

    }



    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1); // RGB, alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        tweenManager.update(delta);

        batch.begin();

        splashSprite.draw(batch);


        batch.end();





    
    }


    @Override
    public void dispose(){
    }



    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }



}
