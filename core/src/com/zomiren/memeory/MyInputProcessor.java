package com.zomiren.memeory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;


public class MyInputProcessor implements InputProcessor {

    @Override
    public boolean keyDown (int keycode) {
        if(keycode == Input.Keys.SPACE) {
            //System.out.println("space");
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        if(keycode == Input.Keys.SPACE) {
            return false;
        }
        return false;
    }

    @Override
    public boolean keyTyped (char character) {

        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }



}
