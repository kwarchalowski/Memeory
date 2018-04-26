package com.zomiren.memeory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class Memeory implements Screen {

    final MemeoryGame game;

	SpriteBatch batch;
    OrthographicCamera camera;
    Stage stage;
    Card pepe; //test card
    MyInputProcessor inputProcessor;
    Card[][] cardsTemplate;
    Card currentCard; // along with prevCard makes logic
    int clickCounter; // helps to track clicks
    int columns;
    int rows;
    Card[] pair;
    Texture pepeTexture;
    Texture fuuTexture;
    Texture backTexture;
    Texture tickTexture;
    Texture ifYouKnowTexture;
    Texture herpTexture;
    Texture motherOfGodTexture;
    Texture trollfaceTexture;
    Texture trueStoryTexture;
    Texture whatTheTexture;
    Texture whyYouNoTexture;
    Texture[] frontTextures;
    Texture randomFront;
    int delay; // delay time in ms
    Timer timer;
    float blockedSquareX = 0f,  blockedSquareY = 0f;
    Texture blockedTexture; // for testing purposes
    Texture background;
    boolean shouldWait = false;
    Sound cardFlipSound;
    Sound cardErrorSound;
    Sound pointsSound;
    float cardErrorVolume;
    boolean blockInput;
    boolean musicOnOff;
    BitmapFont writer;
    int cardsLeft;
    int movesDone;
    int n = 6; // number of columns x rows
    int boardSize = n;
    int numberOfAllCards, numberOfPairs, numberOfFronts, numberOfCardsEachFront;
    MusicModule musicModule;
    Music backgroundDrums;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public Memeory(final MemeoryGame gam, int boardSize, MusicModule musicModule) {
        this.game = gam;
        this.boardSize = boardSize;
        this.musicModule = musicModule;

        background = new Texture(Gdx.files.internal("background.png"));



        backgroundDrums = Gdx.audio.newMusic(Gdx.files.internal("backgroundDrums.mp3"));



        /* board size initialization */
        switch (boardSize) {
            case 2:
                n = 2;
                numberOfAllCards = 4;
                numberOfPairs = 2;
                numberOfFronts = 2;
                numberOfCardsEachFront = 2;
                break;
            case 4:
                n = 4;
                numberOfAllCards = 16;
                numberOfPairs = 8;
                numberOfFronts = 8;
                numberOfCardsEachFront = 2;
                break;
            case 6:
                n = 6;
                numberOfAllCards = 36;
                numberOfPairs = 18;
                numberOfFronts = 9;
                numberOfCardsEachFront = 4;
                break;
            case 8:
                n = 8;
                numberOfAllCards = 64;
                numberOfPairs = 32;
                numberOfFronts = 8;
                numberOfCardsEachFront = 8;
                break;
        }


        /* camera initialization */

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        //camera.zoom = 0.2f;
//        writer = new BitmapFont(Gdx.files.internal("impact.ttf"));


        /* SpriteBatch initialization */
		batch = new SpriteBatch();
        /* stage initialization */
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        /* input processor */
        inputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
        musicModule.batch = batch;
        //this.musicModule = new MusicModule(batch);
        batch.setProjectionMatrix(camera.combined);

        /* fonts */
        generator = new FreeTypeFontGenerator(Gdx.files.internal("bl-as.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;

        parameter.color = Color.valueOf("C9F5FF");
        parameter.color = Color.WHITE;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK;

        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
//        writer = new BitmapFont(Gdx.files.internal("calibri-16.fnt"));
        writer = font12;

        /* stage of Cards initialization */
        columns = n;
        rows = n;
        cardsTemplate = new Card[columns+1][rows+1];
        cardsLeft = n*n;
        movesDone = 0;

        /* sounds */
        cardFlipSound = Gdx.audio.newSound(Gdx.files.internal("cardFlip.wav"));
        cardErrorSound = Gdx.audio.newSound(Gdx.files.internal("error.wav"));
        pointsSound = Gdx.audio.newSound(Gdx.files.internal(("points.wav")));



        /* other things */
        blockInput = false;
        timer = new Timer();
        cardErrorVolume = 0.45f;
        delay = 1000;
        musicOnOff = true;

        /* textures */
        pepe = new Card(
                new Vector2(100, 600),
                new Texture(Gdx.files.internal("pepe.png")),
                new Texture(Gdx.files.internal("back.png")),
                new Texture(Gdx.files.internal("tick2.png"))
        );

        blockedTexture = new Texture(Gdx.files.internal("blocked.png"));
        pepeTexture = new Texture(Gdx.files.internal("pepe.png"));
        herpTexture = new Texture(Gdx.files.internal("herp.png"));
        ifYouKnowTexture = new Texture(Gdx.files.internal("ifYouKnow.png"));
        motherOfGodTexture = new Texture(Gdx.files.internal("motherOfGod.png"));
        trollfaceTexture = new Texture(Gdx.files.internal("trollface.png"));
        trueStoryTexture= new Texture(Gdx.files.internal("trueStory.png"));
        whatTheTexture = new Texture(Gdx.files.internal("whatThe.png"));
        whyYouNoTexture = new Texture(Gdx.files.internal("whyYouNo.png"));
        fuuTexture = new Texture(Gdx.files.internal("fuu.png"));
        backTexture = new Texture(Gdx.files.internal("back.png"));
        tickTexture = new Texture(Gdx.files.internal("tick3.png"));
        frontTextures = new Texture[10];

        frontTextures[0] = pepeTexture;
        frontTextures[1] = fuuTexture;
        frontTextures[2] = herpTexture;
        frontTextures[3] = ifYouKnowTexture;
        frontTextures[4] = motherOfGodTexture;
        frontTextures[5] = trollfaceTexture;
        frontTextures[6] = trueStoryTexture;
        frontTextures[7] = whatTheTexture;
        frontTextures[8] = whyYouNoTexture;
        frontTextures[9] = fuuTexture;



        /* make an empty board */
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                cardsTemplate[i][j] = new Card();
            }
        }



    for(int i = 0; i < columns; i++) {
        for(int j = 0; j < rows; j++) {

                while(cardsTemplate[i][j].getFront() == null) {
                    randomFront = getRandomFront(numberOfFronts);

                    if(checkIfWeCanPutThatFrontOnTheBoard(randomFront)) {
                        cardsTemplate[i][j].setCard(
                                new Vector2(camera.viewportWidth/2 + (i * 66) - n*64/2, camera.viewportHeight/2 + (j * 66) - n*64/2), // position of the board
                                randomFront,
                                backTexture,
                                tickTexture
                        );

                        //cardsTemplate[i][j].setFront(randomFront);
                    } else { }

                }
            }
        }

/*        cardsTemplate[i][j] = new Card(
                new Vector2(camera.viewportWidth/2 + (i * 66) - n*64/2, camera.viewportHeight/2 + (j * 66) - n*64/2), // position of the board
                frontTextures[MathUtils.random(0, numberOfFronts-1)],
                backTexture,
                tickTexture
        );*/



        /* set currentCard */
        currentCard = new Card();
        //prevCard = new Card();
        pair = new Card[2];



	}

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        //rainMusic.play();
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


    private boolean checkIfWeCanPutThatFrontOnTheBoard(Texture randomFront) {

        int counter = 0;

        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                if(cardsTemplate[i][j].getFront() != null) {
                    if(cardsTemplate[i][j].getFront() == randomFront) {
                        counter++;
                    }
                }
            }
        }

        if(counter >= numberOfCardsEachFront) {
            return false;
        } else {
            return true;
        }


    }

    private Texture getRandomFront(int numberOfFronts) {

        return frontTextures[MathUtils.random(0, numberOfFronts-1)];

    }

    @Override
	public void render (float delta) {

        /* input */
/*        int inputX = Gdx.input.getX();
        int inputY = 720 - Gdx.input.getY(); // inverted axis (?)*/
        Vector3 input = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(input);

/*        inputX = Gdx.input.getX();
        inputY = 720 - Gdx.input.getY();*/
        int inputX = (int)input.x;
        int inputY = (int)input.y;


        /* camera update */


        camera.update();



		Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1); // RGB, alpha
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       // batch.setProjectionMatrix(camera.combined);

        /* batch rendering */
		batch.begin();


        batch.draw(background, 0, 0);

        writer.setColor(0.9f, 0.9f, 0.9f, 1f);

        writer.draw(batch, "Pairs left: ", camera.viewportWidth/2 - n*64/2, camera.viewportHeight/2 - (n)*64/2 - 20);
        writer.setColor(0.7f,1f,0.7f,1f);
        writer.draw(batch, Integer.toString(cardsLeft/2), camera.viewportWidth/2 - n*64/2 + 80, camera.viewportHeight/2 - (n)*64/2 - 20);

        writer.setColor(0.9f, 0.9f, 0.9f, 1f);
        writer.draw(batch, "Moves: ", camera.viewportWidth/2 - n*64/2, camera.viewportHeight/2 - (n)*64/2 - 50);
        writer.setColor(0.7f,1f,0.7f,1f);
        writer.draw(batch, Integer.toString(movesDone), camera.viewportWidth/2 - n*64/2 + 80, camera.viewportHeight/2 - (n)*64/2 - 50);
        writer.setColor(0.9f, 0.9f, 0.9f, 1f);

        /* draw all cards */
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {

                /* set current card */
                currentCard = cardsTemplate[i][j];



                /* draws pepe card back or front, depends on bool*/
                if((!currentCard.drawFront) && (currentCard.visible)) {
                    batch.draw(currentCard.getBack(), currentCard.getPosition().x, currentCard.getPosition().y);
                }
                else if((currentCard.drawFront) && (currentCard.visible)){
                    batch.draw(currentCard.getFront(), currentCard.getPosition().x, currentCard.getPosition().y);
                }
                else {}

                /* draw tick if card is already paired */
                if((currentCard.isPaired())) {
                    if(shouldWait) {
                            batch.draw(currentCard.getFront(), currentCard.getPosition().x, currentCard.getPosition().y);
                        shouldWait = false;
                    }
                    batch.draw(currentCard.getPairedTexture(), currentCard.getPosition().x, currentCard.getPosition().y);
            }

                // music onOff switch
                if(Gdx.input.justTouched() && (inputX > 20) && (inputY > 180) && (!blockInput) &&
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

                /* check if click is in bound */
                if( (inputX > currentCard.getPosition().x) && (inputX < currentCard.getPosition().x + 64)) {
                    if( (inputY > currentCard.getPosition().y) && (inputY < currentCard.getPosition().y + 64)) {


                        /* act on click */
                        if(Gdx.input.justTouched() && (!blockInput)) {



                             /* if second click occurs on the same flipped (blocked) square - do nothing */
                             if( (clickCounter == 1) && (inputX > blockedSquareX) && (inputX < blockedSquareX + 64) &&(inputY > blockedSquareY) && (inputY < blockedSquareY + 64) && (!currentCard.isBlocked())) {
                                 System.out.println("BLOCKED SQUARE");
                                 cardErrorSound.setVolume(cardErrorSound.play(), cardErrorVolume);

                             } else {

                                    /* flips current card */
                                     if(!currentCard.isDrawFront())
                                         currentCard.setDrawFront(true);
                                     else
                                         currentCard.setDrawFront(false);

                                    clickCounter++;

                                    blockedSquareX = currentCard.getPosition().x;
                                    blockedSquareY = currentCard.getPosition().y;

                                    System.out.println("clickCounter: " + clickCounter);
                                    System.out.println("blockedSquareX: " + blockedSquareX);
                                    System.out.println("blockedSquareY: " + blockedSquareY);
                                    System.out.println("X/Y : " + inputX + "/" + inputY);



                                    if(clickCounter == 1) {
                                        pair[0] = currentCard;
                                        if(currentCard.isBlocked()) {
                                            System.out.println("BLOCKED SQUARE");
                                            cardErrorSound.setVolume(cardErrorSound.play(), cardErrorVolume);
                                            //currentCard.flip();



                                            clickCounter--;
                                        } else {
                                            if(!currentCard.isDrawFront())
                                                currentCard.setDrawFront(true);
                                            else
                                                currentCard.setDrawFront(false);
                                            cardFlipSound.play(); // play flip sound
                                            currentCard.setDrawFront(true);

                                        }

                                    } else if(clickCounter == 2) {

                                        if(currentCard.isBlocked()) {
                                            System.out.println("BLOCKED SQUARE");

                                            if(!currentCard.isDrawFront())
                                                currentCard.setDrawFront(true);
                                            else
                                                currentCard.setDrawFront(false);

                                            clickCounter--;
                                            cardErrorSound.setVolume(cardErrorSound.play(), cardErrorVolume);
                                        } else {
                                            pair[1] = currentCard;

                                            cardFlipSound.play(); // play flip sound

                                            batch.draw(pair[1].getFront(), pair[1].getPosition().x, pair[1].getPosition().y);

                                            /* checks if clicked pair is the same */
                                            checkPair();
                                            blockInput = true;
                                            float delay = 1; // seconds

                                            Timer.schedule(new Timer.Task(){
                                                @Override
                                                public void run() {
                                                    if(pair[0].getFront() == pair[1].getFront()) {
                                                        pair[0].setPaired(true);
                                                        pair[1].setPaired(true);
                                                    } else {
                                                        pair[0].setPaired(false);
                                                        pair[1].setPaired(false);;
                                                        pair[0].setDrawFront(false);
                                                        pair[1].setDrawFront(false);
                                                        pair[0].setBlocked(false);
                                                        pair[1].setBlocked(false);
                                                        clickCounter = 0;

                                                    }

                                                    blockInput = false;
                                                }
                                            }, delay);



                                            shouldWait = true;



                                        }
                                    }




                                 /* block the current card at the end of loop */
                                 currentCard.setBlocked(true);



                             }

                        }
                    }



                }

            }




        }

        if(musicOnOff)
//            game.batch.draw(musicOn, 20, 180);
            musicModule.renderIcon("on", 20, 180);

        else if(!musicOnOff)
//            game.batch.draw(musicOff, 20, 180);
            musicModule.renderIcon("off", 20, 180);


        batch.end();

	}



    public void checkPair() {
        if(pair[0].getFront() == pair[1].getFront()) {

            System.out.println("-- same fronts -- ");
            clickCounter = 0;
            pair[0].setDrawFront(true);
            pair[1].setDrawFront(true);

                //batch.draw(pair[1].getFront(), pair[1].getPosition().x, pair[1].getPosition().y); // draw a card for a second :)
            cardsLeft -= 2;
            pointsSound.play();

        } else {
            clickCounter = 0;

        }

        movesDone += 1;

    }




    public void wait(float delay) {
        Timer.Task schedule = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                blockInput = false;
            }
        }, delay);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        writer.dispose();
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
    }




}
