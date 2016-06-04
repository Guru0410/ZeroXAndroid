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

import org.w3c.dom.Text;

/**
 * Displays the game settings for Multiplayer screen
 */
public class GameMode extends Activity implements View.OnClickListener {
    SharedPreferences pref;

    String CheckFlag = "";
    String PlayerOne;
    String player_1_name, player_2_name;
    LinearLayout startGame3x3,startGame4x4, about;
    LinearLayout multiplayer, computer, vsComputer, vsPlayer, player_1_choose, player_2_choose, novice, expert;
    TextView vsComputerText, vsPlayerText, txt_player_1_choose, txt_player_2_choose, txt_novice, txt_expert;
    EditText player1, player2, and_Player1;
    RadioGroup rg;
    int flag,mode, turn_mode, level;
    Context context;
    TextView vsAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_mode);

        context = this;

        startGame3x3 = (LinearLayout) findViewById(R.id.startGame3);
        startGame4x4 = (LinearLayout) findViewById(R.id.startGame4);

        multiplayer = (LinearLayout) findViewById(R.id.multiplayer);
        computer = (LinearLayout) findViewById(R.id.computer);
        vsComputer = (LinearLayout) findViewById(R.id.vsComputer);
        vsPlayer = (LinearLayout) findViewById(R.id.vsPlayer);
        player_1_choose = (LinearLayout) findViewById(R.id.player_1_choose);
        player_2_choose = (LinearLayout) findViewById(R.id.player_2_choose);
        novice = (LinearLayout) findViewById(R.id.novice);
        expert = (LinearLayout) findViewById(R.id.expert);

        about = (LinearLayout) findViewById(R.id.about);
        vsPlayerText = (TextView) findViewById(R.id.vsPlayerText);
        vsComputerText = (TextView) findViewById(R.id.vsComputerText);
        txt_player_1_choose = (TextView) findViewById(R.id.txt_player_1_choose);
        txt_player_2_choose = (TextView) findViewById(R.id.txt_player_2_choose);
        txt_novice = (TextView) findViewById(R.id.txt_novice);
        txt_expert= (TextView) findViewById(R.id.txt_expert);

        rg = (RadioGroup) findViewById(R.id.rg1);
        rg.check(R.id.r1);
        flag = 1;

        player1 = (EditText) findViewById(R.id.Player1);
        player2 = (EditText) findViewById(R.id.Player2);
        and_Player1 = (EditText) findViewById(R.id.and_Player1);

        pref = getSharedPreferences("cookies",Context.MODE_PRIVATE);
        player_1_name = pref.getString(PlayerOne, "");

        if(!player_1_name.equals("")) {
            player1.setText(pref.getString(PlayerOne, player_1_name));
            and_Player1.setText(pref.getString(PlayerOne, player_1_name));
        }
        startGame3x3.setOnClickListener(this);
        startGame4x4.setOnClickListener(this);
        about.setOnClickListener(this);
        multiplayer.setOnClickListener(this);
        computer.setOnClickListener(this);
        vsComputer.setOnClickListener(this);
        vsPlayer.setOnClickListener(this);
        player_1_choose.setOnClickListener(this);
        player_2_choose.setOnClickListener(this);
        novice.setOnClickListener(this);
        expert.setOnClickListener(this);

        mode = 1;
    }

    @Override
    public void onClick(View v) {

        if(rg.getCheckedRadioButtonId() == R.id.r1)
            flag = 1;
        else if(rg.getCheckedRadioButtonId() == R.id.r2)
            flag = 3;
        else if(rg.getCheckedRadioButtonId() == R.id.r3)
            flag = 5;

        if(v == startGame3x3) {
            if (mode == 1) {
                SharedPreferences.Editor editor = pref.edit();
                player_1_name = player1.getText().toString();
                player_2_name = player2.getText().toString();

                if (player_1_name.equals("")) {
                    player_1_name = " Player 1";
                } else {
                    editor.putString(PlayerOne, player_1_name);
                    editor.commit();
                }

                if (player_2_name.equals("")) {
                    player_2_name = " Player 2";
                }

                Intent intent = new Intent(GameMode.this, Grid3x3.class);
                intent.putExtra("Series", flag);
                intent.putExtra("TurnMode", turn_mode);
                intent.putExtra("Player_1_Name", player_1_name);
                intent.putExtra("Player_2_Name", player_2_name);

                startActivity(intent);
                finish();
            } else if (mode == 2)
            {
                SharedPreferences.Editor editor = pref.edit();
                player_1_name = and_Player1.getText().toString();

                if (player_1_name.equals("")) {
                    player_1_name = " Player 1";
                } else {
                    editor.putString(PlayerOne, player_1_name);
                    editor.commit();
                }


                Intent intent = new Intent(GameMode.this, ComputerMode.class);
                intent.putExtra("Series", flag);
                intent.putExtra("Level", level);
                intent.putExtra("Player_1_Name", player_1_name);
                intent.putExtra("Player_2_Name", "Comp");

                startActivity(intent);
                finish();
            }
        }
        else if(v == startGame4x4)
        {
            if(mode == 1) {
                SharedPreferences.Editor editor = pref.edit();
                player_1_name = player1.getText().toString();
                player_2_name = player2.getText().toString();

                if (player_1_name.equals("")) {
                    player_1_name = " Player 1";
                } else {
                    editor.putString(PlayerOne, player_1_name);
                    editor.commit();
                }

                if (player_2_name.equals("")) {
                    player_2_name = " Player 2";
                }

                Intent intent = new Intent(GameMode.this, Grid4x4.class);
                intent.putExtra("Series", flag);
                intent.putExtra("TurnMode", turn_mode);
                intent.putExtra("Player_1_Name", player_1_name);
                intent.putExtra("Player_2_Name", player_2_name);

                startActivity(intent);
                finish();
            }
        }
        else if (v == about)
        {
            popup();
        }
        else if(v == vsPlayer)
        {
            mode = 1;

            computer.setVisibility(View.GONE);
            multiplayer.setVisibility(View.VISIBLE);

            vsPlayer.setBackgroundColor(Color.parseColor("#27beff"));
            vsPlayerText.setTextColor(Color.parseColor("#FFFFFF"));

            vsComputer.setBackgroundColor(Color.parseColor("#FFFFFF"));
            vsComputerText.setTextColor(Color.parseColor("#27beff"));

            startGame4x4.setVisibility(View.VISIBLE);
        }
        else if(v == vsComputer)
        {
            mode = 2;

            multiplayer.setVisibility(View.GONE);
            computer.setVisibility(View.VISIBLE);

            vsComputer.setBackgroundColor(Color.parseColor("#27beff"));
            vsComputerText.setTextColor(Color.parseColor("#FFFFFF"));

            vsPlayer.setBackgroundColor(Color.parseColor("#FFFFFF"));
            vsPlayerText.setTextColor(Color.parseColor("#27beff"));

            startGame4x4.setVisibility(View.GONE);
        }
        else if( v== player_2_choose)
        {
            if(txt_player_1_choose.getText().toString().equals("X")) {
                txt_player_1_choose.setText("O");
                txt_player_2_choose.setText("X");
                turn_mode = 0;
            }else
            {
                txt_player_1_choose.setText("X");
                txt_player_2_choose.setText("O");
                turn_mode = 1;
            }
        }
        else if(v == player_1_choose)
        {
            if(txt_player_2_choose.getText().toString().equals("X")) {
                txt_player_1_choose.setText("X");
                txt_player_2_choose.setText("O");
                turn_mode = 1;
            }else
            {
                txt_player_1_choose.setText("O");
                txt_player_2_choose.setText("X");
                turn_mode = 0;
            }
        }
        else if(v == expert)
        {
            expert.setBackgroundColor(Color.parseColor("#27beff"));
            txt_expert.setTextColor(Color.parseColor("#FFFFFF"));

            novice.setBackgroundColor(Color.parseColor("#FFFFFF"));
            txt_novice.setTextColor(Color.parseColor("#27beff"));

            level = 1;
        }
        else if(v == novice)
        {
            novice.setBackgroundColor(Color.parseColor("#27beff"));
            txt_novice.setTextColor(Color.parseColor("#FFFFFF"));

            expert.setBackgroundColor(Color.parseColor("#FFFFFF"));
            txt_expert.setTextColor(Color.parseColor("#27beff"));

            level = 0;
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
