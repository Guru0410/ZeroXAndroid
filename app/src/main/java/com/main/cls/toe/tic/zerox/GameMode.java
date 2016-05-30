package com.main.cls.toe.tic.zerox;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.main.cls.toe.tic.zerox.R;

/**
 * Created by Team 3 - ZeroX Game
 */
public class GameMode extends Activity implements View.OnClickListener {
    SharedPreferences pref;

    String CheckFlag = "";
    String PlayerOne;
    String player_1_name, player_2_name;
    LinearLayout startGame3x3,startGame4x4, about;
    EditText player1, player2;
    RadioGroup rg;
    int flag;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_mode);

        context = this;

        startGame3x3 = (LinearLayout) findViewById(R.id.startGame3);
        startGame4x4 = (LinearLayout) findViewById(R.id.startGame4);
        about = (LinearLayout) findViewById(R.id.about);

        rg = (RadioGroup) findViewById(R.id.rg1);
        rg.check(R.id.r1);
        flag = 1;

        player1 = (EditText) findViewById(R.id.Player1);
        player2 = (EditText) findViewById(R.id.Player2);

        pref = getSharedPreferences("cookies",Context.MODE_PRIVATE);
        player_1_name = pref.getString(PlayerOne, "");

        if(!player_1_name.equals(""))
            player1.setText(pref.getString(PlayerOne, player_1_name));

        startGame3x3.setOnClickListener(this);
        startGame4x4.setOnClickListener(this);
        about.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(rg.getCheckedRadioButtonId() == R.id.r1)
            flag = 1;
        else if(rg.getCheckedRadioButtonId() == R.id.r2)
            flag = 3;
        else if(rg.getCheckedRadioButtonId() == R.id.r3)
            flag = 5;

        if(v == startGame3x3)
        {
            SharedPreferences.Editor editor = pref.edit();
            player_1_name = player1.getText().toString();
            player_2_name = player2.getText().toString();

            if(player_1_name.equals(""))
            {
                player_1_name = " Player 1";
            }
            else {
                editor.putString(PlayerOne, player_1_name);
                editor.commit();
            }

            if(player_2_name.equals(""))
            {
                player_2_name = " Player 2";
            }

            Intent intent = new Intent(GameMode.this, Grid3x3.class);
            intent.putExtra("Series", flag);
            intent.putExtra("Player_1_Name", player_1_name);
            intent.putExtra("Player_2_Name", player_2_name);

            startActivity(intent);
            finish();
        }
        else if(v == startGame4x4)
        {
            SharedPreferences.Editor editor = pref.edit();
            player_1_name = player1.getText().toString();
            player_2_name = player2.getText().toString();

            if(player_1_name.equals(""))
            {
                player_1_name = " Player 1";
            }
            else {
                editor.putString(PlayerOne, player_1_name);
                editor.commit();
            }

            if(player_2_name.equals(""))
            {
                player_2_name = " Player 2";
            }

            Intent intent = new Intent(GameMode.this, Grid4x4.class);
            intent.putExtra("Series", flag);
            intent.putExtra("Player_1_Name", player_1_name);
            intent.putExtra("Player_2_Name", player_2_name);

            startActivity(intent);
            finish();
        }
        else if (v == about)
        {
            popup();
        }

    }

    public void popup() {

        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_about);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

            // dialog.setTitle(dialogHeader);

            dialog.show();
        } catch (Exception e) {

            Toast.makeText(context, "Application Encountered some problem !!",
                    Toast.LENGTH_LONG).show();
        }
    }

}
