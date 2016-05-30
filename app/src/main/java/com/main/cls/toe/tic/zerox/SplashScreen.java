package com.main.cls.toe.tic.zerox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.main.cls.toe.tic.zerox.R;

/**
 * Created by gsdevgun on 29-05-2016.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_zerox);

        Thread tThread = new Thread(){
        public void run(){
        try{
            sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            Intent intent = new Intent(SplashScreen.this,GameMode.class);
            startActivity(intent);
        }
    }
    };
        tThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
