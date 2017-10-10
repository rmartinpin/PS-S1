package prueba.app.raulmartin.com.pong;

import android.graphics.RectF;


public class Bat {

    //para guardar coordenadas
    private RectF rect;

    //El largo de las palas
    private float longitud;

    //X is the far left of the rectangle
    private float y;


    //Velocidad de la pala
    private float paddleSpeed;

    //En que direciones nos podemos mover;
    final int STOPPED = 0;
    final int TOP = 1;
    final int BOT = 2;

    //Se esta moviendo la barra y la direccion
    private int paddleMoving = STOPPED;

    Bat(int screenX, int screenY){

        longitud = 30;
        float heigth = 130;

        y = screenY / 2;

        float x = screenX;

        rect = new RectF(y, x, y+longitud, x + heigth);

        paddleSpeed = 350;
    }


    //Dibujar la pala
    RectF getRect(){
        return rect;
    }
    //Para definir el movimiento, si vamos a izquierda o derecha.
    void setMovementState(int state){
        paddleMoving = state;
    }
    //Actualiza el motor, determina si la pala necesita cambios de movimiento
    //y coordina si es necesario
    void update(long fps){
        if(paddleMoving == BOT){

            y = y - paddleSpeed /fps;
        }

        if(paddleMoving == TOP){
            y = y + paddleSpeed / fps;

        }
        rect.left = y +  longitud;
        rect.right = y;
    }

}

