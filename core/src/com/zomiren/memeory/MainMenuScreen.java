package com.zomiren.memeory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;


/**
 * Created by Kaju on 04.10.15.
 */
public class MainMenuScreen implements Screen {

    final MemeoryGame game;

    OrthographicCamera camera;

    Texture buttonStartGreen;
    Texture buttonStart_down;
    Texture mainScreenBackground;
    MyInputProcessor inputProcessor;

    Texture two;
    Texture four;
    Texture six;
    Texture eight;

    Texture musicOn;
    Texture musicOff;

    Texture dot;

    int inputX, inputY;
    float fourX, fourY, sixX, sixY, eightX, eightY;

    boolean blockInput;


    Sound gameStart;
    Sound pain;
    Sound ouch1;
    Sound ouch2;
    Sound[] ouches;

    Timer timer;

    Music backgroundDrums;
    boolean musicOnOff;
    boolean soundsOnOff;

    MusicModule musicModule;




    public MainMenuScreen(final MemeoryGame gam, MusicModule musicModule) {

        game = gam;
        this.musicModule = musicModule;
        blockInput = false;
        timer = new Timer();
        buttonStartGreen = new Texture(Gdx.files.internal("buttonStart.png"));
        buttonStart_down = new Texture(Gdx.files.internal("startButton_down.png"));
        mainScreenBackground = new Texture(Gdx.files.internal("mainScreenBackground.png"));

        musicOn = new Texture(Gdx.files.internal(("musicOn.png")));
        musicOff = new Texture(Gdx.files.internal(("musicOff.png")));

        musicOnOff = true;


        gameStart = Gdx.audio.newSound(Gdx.files.internal("game-start.ogg"));
        pain = Gdx.audio.newSound(Gdx.files.internal("pain.wav"));
        ouch1 = Gdx.audio.newSound(Gdx.files.internal("ouch01.wav"));
        ouch2 = Gdx.audio.newSound(Gdx.files.internal("ouch02.wav"));
        ouches = new Sound[3];
        ouches[0] = pain; ouches[1] = ouch1; ouches[2] = ouch2;

        backgroundDrums = Gdx.audio.newMusic(Gdx.files.internal("backgroundDrums.mp3"));
//        backgroundDrums.play();
        musicModule.playMusic(backgroundDrums);

        dot = new Texture(Gdx.files.internal("dot.png"));

        two = new Texture(Gdx.files.internal("2x2.png"));
        four = new Texture(Gdx.files.internal("4x4.png"));
        six = new Texture(Gdx.files.internal("6x6.png"));
        eight = new Texture(Gdx.files.internal("8x8.png"));


        inputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        //camera.setToOrtho(false);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1); // RGB, alpha
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Vector3 input = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(input);

/*        inputX = Gdx.input.getX();
        inputY = 720 - Gdx.input.getY();*/
        inputX = (int)input.x;
        inputY = (int)input.y;
//        System.out.println("X/Y: " + inputX + "/" + inputY);


        game.batch.begin();
            game.batch.draw(mainScreenBackground, 0, 0);

        fourX = camera.viewportWidth/2 - four.getWidth()/2;
        fourY = camera.viewportHeight/2 - four.getHeight()/2;

        sixX = camera.viewportWidth/2 - six.getWidth()/2;
        sixY = camera.viewportHeight/2 - six.getHeight()/2 - 75;

        eightX = camera.viewportWidth/2 - eight.getWidth()/2;
        eightY = camera.viewportHeight/2 - eight.getHeight()/2 - 160;

            game.batch.draw(four, fourX, fourY);
            game.batch.draw(six, sixX, sixY);
            game.batch.draw(eight, eightX, eightY);

        game.batch.draw(dot, inputX, inputY);

        if(musicOnOff)
//            game.batch.draw(musicOn, 20, 180);
            musicModule.renderIcon("on", 20, 180);

        else if(!musicOnOff)
//            game.batch.draw(musicOff, 20, 180);
            musicModule.renderIcon("off", 20, 180);


        game.batch.end();

        if(Gdx.input.isTouched()) {



            if((inputX > fourX) && (inputX < (fourX + four.getWidth() - 30) ) &&
                    (inputY > fourY) && (inputY < (fourY + four.getHeight())) ) {
                gameStart.play();
                game.setScreen(new Memeory(game, 4, musicModule));
            }

            else if((inputX > sixX) && (inputX < (sixX + six.getWidth()- 30) ) &&
                    (inputY > sixY) && (inputY < (sixY + six.getHeight())) ) {
                gameStart.play();
                game.setScreen(new Memeory(game, 6, musicModule));
            }

            else if((inputX > eightX) && (inputX < (eightX + eight.getWidth()- 30) ) &&
                    (inputY > eightY) && (inputY < (eightY + eight.getHeight())) ) {
                gameStart.play();
                game.setScreen(new Memeory(game, 8, musicModule));
            }

            else if((inputX < 100) && (inputY < 150) && !blockInput) {
                int rand = MathUtils.random(2);
                ouches[rand].setVolume(ouches[rand].play(), 0.5f);
                blockInput = true;
                wait(0.5f);
            }

            // music onOff switch
            else if((inputX > 20) && (inputY > 180) && (!blockInput) &&
                    (inputX < (20 + 64)) && (inputY < (180 + 64))) {

                blockInput = true;


                if(musicOnOff == true) {
                    musicModule.stopMusic(backgroundDrums);
                    musicOnOff = false;
                }
                else if (musicOnOff == false) {
                    musicModule.playMusic(backgroundDrums);
                    musicOnOff = true;
                }

                wait(0.5f);
            }


            //dispose();
        }





    }

    public void wait(float delay) {
        Task schedule = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                blockInput = false;
            }
        }, delay);
    }

    public void boolToggle(boolean bul) {
        if(bul == true)
            bul = false;
        else if (bul == false)
            bul = true;
    }



    @Override
    public void dispose(){

        pain.dispose();
       gameStart.dispose();
        game.batch.dispose();
        backgroundDrums.dispose();

    }

    @Override
    public void show() {

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
