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
        bat = new Bat(screenY,screenX);

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
            bola.setRandomXVelocity();
            bola.reverseYVelocity();
            bola.clearObstacleY(bat.getRect().top - 2);
            //soundPool.play(beep1ID, 1, 1, 0, 0, 1);
        }
        // Check for ball colliding with bat
        if(RectF.intersects(bat2.getRect(),bola.getRect())) {
            bola.setRandomXVelocity();
            bola.reverseYVelocity();
            //bola.clearObstacleY(bat2.getRect().top - 2);
            //soundPool.play(beep1ID, 1, 1, 0, 0, 1);
        }
    }

    void restart(){

        bola.reset(screenX,screenY);

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

                if(motionEvent.getY() > screenY / 2){
                    bat.setMovementState(bat.TOP);
                    //bat2.setMovementState(bat2.TOP);
                }
                else{
                    bat.setMovementState(bat.BOT);
                    //bat2.setMovementState(bat2.BOT);
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
