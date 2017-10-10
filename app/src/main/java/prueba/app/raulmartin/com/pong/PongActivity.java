package prueba.app.raulmartin.com.pong;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Point;
import android.view.Display;


public class PongActivity extends Activity {

    PongMotor pongMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Recuperar la pantalla para acceder a detalles
        Display display = getWindowManager().getDefaultDisplay();
        //Cargar la resolución en un objeto Punto
        Point size = new Point();
        display.getSize(size);

        //Inicializar la vista del juego y definirla como la vista

        pongMotor = new PongMotor(this, size.x, size.y);
        setContentView(pongMotor);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //Avisa a la visa que continue el metodo
        pongMotor.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();

        //Avisa a la vista que pare el metodo en ejecucion
        pongMotor.pause();
    }
}
