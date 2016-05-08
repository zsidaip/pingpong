package hu.uniobuda.nik.pong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //jatek inditasa telo vs player
    public void buttonOnClick(View v){
        Intent masik=new Intent(this,GameActivity.class);
        masik.putExtra("mode","comp");
        startActivity(masik);
    }
    //jatekosnev beallitasa inditasa
    public void setplayerOnClick(View v){
        Intent masik=new Intent(this,PlayerActivity.class);
        startActivity(masik);
    }

    public void buttonJoinOnClick(View view) {
        Intent masik=new Intent(this,GameActivity.class);
        masik.putExtra("mode", "join");
        startActivity(masik);
    }

    public void buttonHostOnClick(View view) {
        Intent masik=new Intent(this,GameActivity.class);
        masik.putExtra("mode", "host");
        startActivity(masik);
    }
    //kilepes a jatekbol
    public void btnexitonClick(View v) {
        finish();
        System.exit(0);
    }

}
