package prueba.app.raulmartin.com.pong;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.graphics.RectF;



class PongMotor extends SurfaceView implements Runnable{


    //Hilo
    private Thread hiloJuego = null;

    //SurfaceHolder
    private SurfaceHolder ourHolder;

    //nos indica cuando se esta jungando o no
    private volatile boolean playing;


    //Juego pausado al principio
    private boolean pause = true;

    //Canvas y paint
    private Canvas canvas;
    private Paint paint;

    //Como es la pantalla
    private int screenX;
    private int screenY;

    //FPS
    private long fps;


    //Para el calculo de FPS
    private long tiempoDeFrame;


    //Pala 1
    Bat bat;

    //Pala2
    Bat2 bat2;

    Ball bola;


    public PongMotor(Context context, int x, int y) {
        super(context);

        //Inicializamos el holder
        ourHolder = getHolder();
        paint = new Paint();

        //Inicializar screen
        screenX  = x;
        screenY = y;

        //Inicializamos la pala
        bat = new Bat(screenX,screenY);

        //Pala2
        bat2 = new Bat2(screenX,screenY);

        //Inicia la bola
        bola = new Ball();

        restart();
    }

    public void pause(){
        playing = false;
        try {
            hiloJuego.join();

        }catch (InterruptedException e){
            Log.e("Error", "Joining thread");
        }

    }


    public void resume(){
        playing = true;
        hiloJuego = new Thread(this);
        hiloJuego.start();

    }

    @Override
    public void run() {
        while (playing){
            //Captura el tiempo en ms
            long startFrameTime = System.currentTimeMillis();

            //Actualiza el frame
            if(!pause){
                update();
            }
            //Dibuja el frame
            draw();

            //Calcula los fps (para hacer el timing de animaciones...)

            tiempoDeFrame = System.currentTimeMillis() - startFrameTime;
            if(tiempoDeFrame >=1){
                fps = 1000 / tiempoDeFrame;
            }
        }


    }
    //Ejecuta cundo se llama a Pausa en el metodo de PongActivity
    private void update(){
        //move de bat if required
        bat.update(fps);

        //Mueve la pala 2 si es necesario
        bat2.update(fps);

        bola.update(fps);

        // Check for ball colliding with bat
        if(RectF.intersects(bat.getRect(),bola.getRect())) {
            bola.setRandomYVelocity();
            bola.reverseYVelocity();
            //bola.clearObstacleY(bat.getRect().top - 2);
            //soundPool.play(beep1ID, 1, 1, 0, 0, 1);
        }
        // Check for ball colliding with bat2
        if(RectF.intersects(bat2.getRect(),bola.getRect())) {
            bola.setRandomYVelocity();
            bola.reverseYVelocity();

            //bola.clearObstacleY(bat2.getRect().top - 2);
            //soundPool.play(beep1ID, 1, 1, 0, 0, 1);
        }
        // If the ball hits left wall bounce
        if(bola.getRect().left < 0){
            bola.reverseXVelocity();
            //bola.clearObstacleX(2);
            //soundPool.play(beep3ID, 1, 1, 0, 0, 1);
        }

        // If the ball hits right wall bounce
        if(bola.getRect().right > screenX - 10){
            bola.reverseXVelocity();
            //bola.clearObstacleX(screenX - 22);
            //soundPool.play(beep3ID, 1, 1, 0, 0, 1);
        }

        //SI se va la bola por la parte de abajo
        if(bola.getRect().top > screenY +20){
            bola.clearObstacleY(screenY);

            }
            //bola.reverseYVelocity();
            //bola.clearObstacleY(12);
            //soundPool.play(beep2ID, 1, 1, 0, 0, 1);


        //si se va la bola por la parte de abajo
        if(bola.getRect().bottom < 0){
<<<<<<< HEAD


=======
            bola.clearObstacleY(screenY);
            //bola.reset();
            //bola.reverseYVelocity();
            //bola.clearObstacleY(12);
>>>>>>> parent of e0644ac... Ajuste de codigo
            //soundPool.play(beep2ID, 1, 1, 0, 0, 1);

        }
        //Choque con las paredes el bat 1
        if (bat.getRect().left <= 0){
            bat.setMovementState(bat.STOPPED);
        }
        if(bat.getRect().right >= screenX ){
            bat.setMovementState(bat.STOPPED);

        }
        //Choque con las paredes del bat2
        if (bat2.getRect().left <= 0){
            bat2.setMovementState(bat2.STOPPED);

        }
        if(bat2.getRect().right >= screenX){
            bat2.setMovementState(bat2.STOPPED);

        }

    }

    void restart(){

        // Put the ball back to the start


    }

    private void draw(){
        //Nos aseguramos de que es válido
        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();

            //Pon color de fondo
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            //Dibuja lo que sea en la pantalla


            //Dibuja la pala
            canvas.drawRect(bat.getRect(),paint);

            paint.setColor(Color.argb(255,0,0,0));
            //Pala2
            canvas.drawRect(bat2.getRect(),paint);

            paint.setColor(Color.argb(255,255,255,255));

            //Dibuja la bola
            canvas.drawRect(bola.getRect(),paint);

            //Muestra to dibujao'
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // toca la pantalla
            case MotionEvent.ACTION_DOWN:

                pause = false;
                //movimiento pala 1 a la derecha
                if(motionEvent.getX() > screenX / 2 && motionEvent.getY() > screenY /2 ){
                    bat.setMovementState(bat.RIGHT);

                }if(motionEvent.getX() < screenX / 2 && motionEvent.getY() > screenY /2 ){
                    bat.setMovementState(bat.LEFT);
                }
                if((motionEvent.getX() > screenX / 2 && motionEvent.getY() < screenY/2)){
                    bat2.setMovementState(bat2.RIGHT);
                }
                if(motionEvent.getX() < screenX / 2 && motionEvent.getY() < screenY /2 ){
                    bat2.setMovementState(bat2.LEFT);
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                bat.setMovementState(bat.STOPPED);
                bat2.setMovementState(bat2.STOPPED);
                break;
        }


        return true;
    }
}
