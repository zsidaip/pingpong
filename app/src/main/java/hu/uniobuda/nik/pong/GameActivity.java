package hu.uniobuda.nik.pong;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameActivity extends AppCompatActivity {
    Game game;
    SensorManager manager;
    TextView points,result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);

        //game=new Game(this);
        game= (Game) findViewById(R.id.gamelayer);
        points= (TextView) findViewById(R.id.txtvwscore);
        result= (TextView) findViewById(R.id.txtresult);
        //setContentView(game);
        manager= (SensorManager) getSystemService(SENSOR_SERVICE);

    }
    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(game.getResult()=="") {
                float[] values = event.values;
                int direction = 1;
                int move = (int) (values[0] * 4);
                if (move < 0) {
                    move *= -1;
                    direction *= -1;
                }
                if (game.prec1 != null) {
                    game.prec1.setMove(direction, move);
                    points.setText("Jarvis:" + game.point2 + "|Player:" + game.point1);
                }
            }else{
                result.setText(game.getResult());
                points.setText("Jarvis:" + game.point2 + "|Player:" + game.point1);
                Button btn= (Button) findViewById(R.id.btnnewgame);
                btn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(listener);
    }
    public void btnexitonClick(View v) {
        finish();
    }
}
