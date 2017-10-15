package prueba.app.raulmartin.com.pong;


import android.graphics.RectF;

public class Bat {
    //para guardar coordenadas
    private RectF rect;

    //El largo de las palas
    private float longitud;

    //X is the far left of the rectangle
    private float x;

    //Velocidad de la pala
    private float paddleSpeed;

    //En que direciones nos podemos mover;
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;

    //Se esta moviendo la barra y la direccion
    private int paddleMoving = STOPPED;

    Bat(int screenX, int screenY){

        longitud = 250;
        float heigth = 30;

        x = screenX / 2 - 125;

        float y =screenY- 50;

        rect = new RectF(x, y, x+longitud, y + heigth);

        paddleSpeed = 370;
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
        if(paddleMoving == LEFT){

            x = x - paddleSpeed /fps;
        }

        if(paddleMoving == RIGHT){
            x = x + paddleSpeed / fps;

        }
        rect.left = x;
        rect.right = x + longitud;
    }

}

