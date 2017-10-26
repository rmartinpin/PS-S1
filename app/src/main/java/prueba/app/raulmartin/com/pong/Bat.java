package prueba.app.raulmartin.com.pong;


import android.graphics.RectF;

public class Bat {
    //En que direciones nos podemos mover;
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;
    //para guardar coordenadas
    private RectF rect;
    //El largo y ancho de las palas
    private float longitud;
    private float heigth;
    //X is the far left of the rectangle
    private float x;
    //Velocidad de la pala
    private float paddleSpeed;
    //Se esta moviendo la barra y la direccion
    private int paddleMoving = STOPPED;

    Bat(int screenX, int screenY){

        x = screenX / 2 - 125;

        float y =screenY- 50;

        rect = new RectF(x, y, x+longitud, y + heigth);

        paddleSpeed = 370;
    }


    Bat(int screenX, int screenY, float posX, float posY) {

        longitud = 250;
        heigth = 30;

        x = posX;

        float y = posY;

        rect = new RectF(x, y, x + longitud, y + heigth);

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

