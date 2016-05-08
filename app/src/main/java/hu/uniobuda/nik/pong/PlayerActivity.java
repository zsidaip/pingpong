package hu.uniobuda.nik.pong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        String playername="";//playernev kezelese
        try {
            FileInputStream fis = openFileInput("PongStore");
            byte[] buffer = new byte[1024]; int len;
            while((len = fis.read(buffer)) > 0)
                playername=new String(buffer, 0, len);
            fis.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        EditText pname= (EditText) findViewById(R.id.edtplayer);
        pname.setText(playername);
    }

    //playernev mentese es kilepes az activity bol
    public void btnsaveplayerClick(View v) {
        EditText pname= (EditText) findViewById(R.id.edtplayer);
        String name=pname.getText().toString();
        if(name.length()!=0){
                FileOutputStream fos = null;
            try {
                fos = openFileOutput("PongStore", MODE_PRIVATE);
                fos.write(name.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
           finish();
        }
    }
}
