package com.zomiren.memeory;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Card {

    Texture back;
    Texture front;
    Texture pairedTexture;
    Vector2 position;
    boolean drawFront = false;
    boolean isPaired = false;
    boolean visible = true; // should draw this card
    boolean blocked = false;

    public Card(Vector2 position, Texture front, Texture back, Texture pairedTexture) {
        this.front = front;
        this.back = back;
        this.position = position;
        this.pairedTexture = pairedTexture;
    }

    public Card() {

    }

    public void setCard(Vector2 position, Texture front, Texture back, Texture pairedTexture) {
        this.front = front;
        this.back = back;
        this.position = position;
        this.pairedTexture = pairedTexture;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isPaired() {
        return isPaired;
    }

    public Texture getPairedTexture() {
        return pairedTexture;
    }

    public void setPaired(boolean isPaired) {
        this.isPaired = isPaired;
    }

    public void flip() {
        if(drawFront) {
            drawFront = false;
        }
        else {
            drawFront = true;
        }
    }

    public boolean isDrawFront() {
        return drawFront;
    }

    public void setDrawFront(boolean drawFront) {
        this.drawFront = drawFront;
    }

    /* back set/get */
    public Texture getBack() {
        return back;
    }

    public void setBack(Texture back) {
        this.back = back;
    }


    /* front set/get */
    public Texture getFront() {
        return front;
    }

    public void setFront(Texture front) {
        this.front = front;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
