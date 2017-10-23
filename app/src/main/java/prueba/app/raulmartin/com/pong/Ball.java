package prueba.app.raulmartin.com.pong;

import android.graphics.RectF;
import java.util.Random;

public class Ball {

    private RectF rect;
    private float xVelocity;
    private float yVelocity;
    private float ballWidth = 20;
    private float ballHeight  = 20;
    private float puntoX;
    private float puntoY;
    private float ancho;
    private float alto;

    //Cambiamos el constructor de la bola
    Ball(){
        xVelocity = 210;
        yVelocity = -370;

        rect = new RectF();
    }

    Ball(float puntoX, float puntoY,float ancho, float alto){
        xVelocity = 210;
        yVelocity = 0;

        ancho = ancho + ballWidth;
        alto = alto + ballHeight;

        rect = new RectF(puntoX,puntoY,ancho,alto);
    }
    RectF getRect(){

        return rect;
    }

    void update(long fps){

        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + (ballWidth);
        rect.bottom = rect.top - (ballHeight);
    }

    void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    void reverseXVelocity(){
        xVelocity = - xVelocity;
    }

    void setRandomYVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    void clearObstacleY(float y){
        rect.bottom = y;
        rect.top = y - ballHeight;
    }

    void clearObstacleX(float x){
        rect.left = x;
        rect.right = x + ballWidth;
    }

    void reset(int x, int y){
        rect.left = x / 2;
        rect.top = y - 50;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 50 - ballHeight;
    }
}
