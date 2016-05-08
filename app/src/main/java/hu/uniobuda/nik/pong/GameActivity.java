package hu.uniobuda.nik.pong;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

public class GameActivity extends AppCompatActivity {
    Game game;
    AEnemyPlayer enemyPlayer;
    SensorManager manager;
    TextView points, result;
    private BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice enemyDevice;
    public static final int MESSAGE_READ = 2;
    ConnectedThread connThread;
    String playername;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playername = "";
        try {
            FileInputStream fis = openFileInput("PongStore");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0)
                playername = new String(buffer, 0, len);
            fis.close();
        } catch (FileNotFoundException e) {
            Intent masik = new Intent(this, PlayerActivity.class);
            startActivity(masik);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (playername == "") {
            Intent masik = new Intent(this, PlayerActivity.class);
            startActivity(masik);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);

        //game=new Game(this);
        game = (Game) findViewById(R.id.gamelayer);
        points = (TextView) findViewById(R.id.txtvwscore);
        result = (TextView) findViewById(R.id.txtresult);
        //setContentView(game);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (!setUpMode()) finish();
        game.SetEnemyPlayer(enemyPlayer);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (game.getResult() == "") {
                float[] values = event.values;
                int direction = 1;
                int move = (int) (values[0] * 4);
                if (move < 0) {
                    move *= -1;
                    direction *= -1;
                }
                if (game.prec1 != null) {
                    game.prec1.setMove(direction, move);
                    points.setText("Jarvis:" + game.point2 + "|" + playername + ":" + game.point1);
                }
            } else {
                result.setText(game.getResult());
                points.setText("Jarvis:" + game.point2 + "|" + playername + ":" + game.point1);
                Button btn = (Button) findViewById(R.id.btnnewgame);
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

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String address = null;
            switch (msg.what) {
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);


                    break;

            }
        }
    };

    public boolean setUpMode() {
        String mode = getIntent().getStringExtra("mode");
        switch (mode) {
            case "comp":
                EnemyCompPlayer ePlayer = new EnemyCompPlayer();
                enemyPlayer = ePlayer;
                return true;

            case "join":
                if (!EnableBT()) return false;
                QueryDevices();
                BTConnect connClass = new BTConnect(enemyDevice, mBluetoothAdapter);
                while (!connClass.run()) {
                } // wait for connect
                BluetoothSocket jsocket = connClass.BTSocket;
                ConnectedThread jconnectedThread = new ConnectedThread(jsocket, mHandler);
                break;

            case "host":
                if (!EnableBT()) return false;
                QueryDevices();
                BTAcceptConnection connAccepterClass = new BTAcceptConnection(mBluetoothAdapter);
                while (!connAccepterClass.run()) {
                } //wait for connect
                BluetoothSocket hsocket = connAccepterClass.BTSocket;
                ConnectedThread hconnectedThread = new ConnectedThread(hsocket, mHandler);
                break;
        }
        return true;
    }

    public void QueryDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            enemyDevice = pairedDevices.iterator().next();
        } else {
            Toast.makeText(getApplicationContext(), R.string.bth_paroztat, Toast.LENGTH_LONG).show();
        }
    }


    public boolean EnableBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), R.string.bth_nincs, Toast.LENGTH_LONG).show();
            return false; //Nincs BT a készülékben
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Game Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://hu.uniobuda.nik.pong/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Game Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://hu.uniobuda.nik.pong/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
