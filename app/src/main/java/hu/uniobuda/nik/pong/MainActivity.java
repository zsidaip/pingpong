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
    public void buttonOnClick(View v){
//        setContentView(R.layout.activity_main2);
        Intent masik=new Intent(this,GameActivity.class);
        startActivity(masik);
    }
    public void setplayerOnClick(View v){
//        setContentView(R.layout.activity_main2);
        Intent masik=new Intent(this,PlayerActivity.class);
        startActivity(masik);
    }
    public void btnexitonClick(View v) {
        finish();
        System.exit(0);
    }

}
