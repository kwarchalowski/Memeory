package com.zomiren.memeory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Kaju on 14.10.15.
 */
public class MusicModule  {

    SpriteBatch batch;
    Texture musicOn;
    Texture musicOff;
    boolean musicOnOff;
    Music backgroundDrums;

    public MusicModule(SpriteBatch batch) {
        this.batch = batch;

        musicOn = new Texture(Gdx.files.internal(("musicOn-2.png")));
        musicOff = new Texture(Gdx.files.internal(("musicOff-2.png")));

        backgroundDrums = Gdx.audio.newMusic(Gdx.files.internal("backgroundDrums.mp3"));
    }

    public void playMusic(Music music) {
        backgroundDrums.play();
        musicOnOff = true;
    }

    public void stopMusic(Music music) {
        backgroundDrums.stop();
        musicOnOff = false;
    }

    public void renderIcon(String onOff, int posX, int posY) {

        if(onOff.equals("on")) {

            batch.draw(musicOn, posX, posY);

        }
        else if (onOff.equals("off")) {

            batch.draw(musicOff, posX, posY);

        }

    }

}

