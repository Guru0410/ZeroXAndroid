package com.main.cls.toe.tic.zerox;

    import android.app.Dialog;
    import android.content.Context;
    import android.content.Intent;
    import android.graphics.Color;
    import android.os.Handler;
    import android.support.design.widget.FloatingActionButton;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.view.Window;
    import android.widget.Button;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

public class ComputerMode extends AppCompatActivity {

        private AIGame mGame;
        private LinearLayout mBoardButton[];
        public static TextView mBoardText[];
//        private TextView mInfoTextView;
//        private TextView mHumanCount;
//        private TextView mTieCount;
//        private TextView mAndroidCount;
        TextView txt_draw_score, txt_player_turn, txt_player1_score, txt_player2_score, txt_player1_name, txt_player2_name;

        private boolean mHumanFirst = true;
        private boolean mGameOver = false;
        Intent intent;
        int Series, HumanWon, ComputerWon, DrawGame, Counter, currentState, level;
        String player_1_name, player_2_name;
        Context context;
        Handler handler;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.computer_mode);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Counter = Series;
                    HumanWon = 0;
                    ComputerWon = 0;
                    DrawGame = 0;
                    mHumanFirst = true;
                    mGameOver = false;

                    txt_player1_score.setText(String.valueOf(HumanWon));
                    txt_player2_score.setText(String.valueOf(ComputerWon));
                    txt_draw_score.setText(String.valueOf(DrawGame));

                    mGame = new AIGame();
                    startNewGame();
                }
            });

            context = this;
            handler = new Handler();

            mBoardButton = new LinearLayout[mGame.getBoardSize()];
            mBoardText = new TextView[mGame.getBoardSize()];

            initialize();
            intent = getIntent();

            player_1_name = intent.getStringExtra("Player_1_Name");
            player_2_name = intent.getStringExtra("Player_2_Name");
            Series = intent.getIntExtra("Series", 0);
            level = intent.getIntExtra("Level", 1);
            Counter = Series;

            txt_player1_name.setText(player_1_name);
            txt_player2_name.setText(player_2_name);
            txt_player_turn.setText("");
            txt_player_turn.setTextColor(Color.parseColor("#27beff"));

            mGame = new AIGame();
            startNewGame();
        }

        private void initialize()
        {
            mBoardButton[0] = (LinearLayout) findViewById(R.id.layout1);
            mBoardButton[1] = (LinearLayout) findViewById(R.id.layout2);
            mBoardButton[2] = (LinearLayout) findViewById(R.id.layout3);

            mBoardButton[3] = (LinearLayout) findViewById(R.id.layout4);
            mBoardButton[4] = (LinearLayout) findViewById(R.id.layout5);
            mBoardButton[5] = (LinearLayout) findViewById(R.id.layout6);

            mBoardButton[6] = (LinearLayout) findViewById(R.id.layout7);
            mBoardButton[7] = (LinearLayout) findViewById(R.id.layout8);
            mBoardButton[8] = (LinearLayout) findViewById(R.id.layout9);

            mBoardText[0] = (TextView) findViewById(R.id.text1);
            mBoardText[1] = (TextView) findViewById(R.id.text2);
            mBoardText[2] = (TextView) findViewById(R.id.text3);

            mBoardText[3] = (TextView) findViewById(R.id.text4);
            mBoardText[4] = (TextView) findViewById(R.id.text5);
            mBoardText[5] = (TextView) findViewById(R.id.text6);

            mBoardText[6] = (TextView) findViewById(R.id.text7);
            mBoardText[7] = (TextView) findViewById(R.id.text8);
            mBoardText[8] = (TextView) findViewById(R.id.text9);

            txt_player1_name = (TextView) findViewById(R.id.txt_player1_name);
            txt_player2_name = (TextView) findViewById(R.id.txt_player2_name);
            txt_player1_score = (TextView) findViewById(R.id.txt_player1_score);
            txt_player2_score = (TextView) findViewById(R.id.txt_player2_score);
            txt_draw_score = (TextView) findViewById(R.id.txt_draw_score);
            txt_player_turn = (TextView) findViewById(R.id.txt_player_turn);
        }
        private void startNewGame()
        {
            txt_player_turn.setText("");
            mGame.clearBoard();

            for(int i=0; i<mBoardButton.length; i++)
            {
                mBoardText[i].setText("-");
                mBoardText[i].setTextColor(Color.parseColor("#666666"));
                mBoardButton[i].setEnabled(true);
                mBoardButton[i].setOnClickListener(new ButtonClickListener(i));
            }

            if(mHumanFirst)
            {
//                txt_player_turn.setText("Player Turn : " + player_1_name);
                mHumanFirst = false;
            }
            else
            {
//                txt_player_turn.setText("Player Turn : " + player_2_name);
                int move = mGame.getComputerMove();
                setMove(mGame.ANDROID_PLAYER,move);
                mHumanFirst = true;
            }
        }

        private void setMove(char player, int location)
        {
            mGame.setMove(player, location);
            mBoardButton[location].setEnabled(false);
            mBoardText[location].setText(String.valueOf(player));

            if(player == mGame.HUMAN_PLAYER)
            {
                mBoardText[location].setTextColor(Color.parseColor("#FF9E27"));
//                txt_player_turn.setTextColor(Color.parseColor("#F42B6F"));
            }
            else
            {
                mBoardText[location].setTextColor(Color.parseColor("#F42B6F"));
//                txt_player_turn.setTextColor(Color.parseColor("#FF9E27"));
            }
        }

        public void checkSeries()
        {
            System.out.println("Human : " + HumanWon);
            System.out.println("Comp : " + ComputerWon);
            System.out.println("Draw : " + DrawGame);
            System.out.println("Counter : " + Counter);

            txt_player1_score.setText(String.valueOf(HumanWon));
            txt_player2_score.setText(String.valueOf(ComputerWon));
            txt_draw_score.setText(String.valueOf(DrawGame));

            if(Counter > 0)
            {
                mHumanFirst = true;
                mGameOver = false;
                mGame = new AIGame();
                startNewGame();
//                txt_player_turn.setText("Player Turn : " + player_1_name);
            }
            else
            {
                if(HumanWon > ComputerWon)
                {
                    currentState = 2;
                }
                else if( ComputerWon > HumanWon)
                {
                    currentState = 3;
                }
                else
                {
                    currentState = 1;
                }
                popup(false);
            }
        }


        private class ButtonClickListener implements View.OnClickListener
        {
            int location;
            public ButtonClickListener(int location)
            {
                this.location = location;
            }
            public void onClick(View view)
            {
                if(!mGameOver)
                {
                    if(mBoardButton[location].isEnabled())
                    {
                        setMove(mGame.HUMAN_PLAYER,location);
                    }

                    int winner = mGame.checkForWinner();

                    if(winner == 0)
                    {
                        int move;
                        if(level == 0)
                            move = mGame.randomMove();
                        else
                            move = mGame.getComputerMove();

                        setMove(mGame.ANDROID_PLAYER,move);
                        winner = mGame.checkForWinner();
                    }
                    if(winner == 0)
                    {
                    }
                    else if (winner == 1)
                    {
                        txt_player_turn.setText(" Game Drawn !");

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                DrawGame++;
                                mGameOver = true;
                                Counter--;
                                checkSeries();
                            }
                        }, 1 * 2000);
                    }
                    else if (winner == 2)
                    {
                        txt_player_turn.setText(R.string.result_human_wins);
                        txt_player_turn.setText(player_1_name + " Wins !");

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                HumanWon++;
                                mGameOver = true;
                                Counter--;
                                checkSeries();
                            }
                        }, 1 * 2000);
                    }
                    else
                    {
                        txt_player_turn.setText(player_2_name + " Wins !");

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                ComputerWon++;
                                mGameOver = true;
                                Counter--;
                                checkSeries();
                            }
                        }, 1 * 2000);
                    }
                }
            }
        }

    /**
     * Pop up display after the game is finished or onBack Press Event
     * @param backPressed
     */
    public void popup(boolean backPressed) {

        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            TextView txtMsg1 = (TextView) dialog.findViewById(R.id.txtMsg1);
            TextView txtMsg2 = (TextView) dialog.findViewById(R.id.txtMsg2);

            TextView btnHome = (TextView) dialog.findViewById(R.id.btnHome);
            TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
            TextView btnPlayAgain = (TextView) dialog.findViewById(R.id.btnPlayAgain);
            TextView btnResume = (TextView) dialog.findViewById(R.id.btnResume);

            if(backPressed)
            {
                txtMsg1.setVisibility(View.INVISIBLE);
                txtMsg2.setText("Game PAUSED !");
                btnResume.setVisibility(View.VISIBLE);
                btnPlayAgain.setVisibility(View.GONE);
            }
            else
            {
                txtMsg1.setVisibility(View.VISIBLE);
                btnResume.setVisibility(View.GONE);
                btnPlayAgain.setVisibility(View.VISIBLE);
            }

            switch(currentState)
            {
                case 1: txtMsg2.setText("Game DRAWN !");
                    break;

                case 2: txtMsg2.setText(player_1_name + " Won the Game !");
                    break;

                case 3: txtMsg2.setText(player_2_name + " Won the Game !");
                    break;
            }


            btnCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                    finish();

                }
            });

            btnHome.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();

                    Intent intent = new Intent(ComputerMode.this, GameMode.class);
                    startActivity(intent);
                    finish();
                }
            });

            btnPlayAgain.setOnClickListener((new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();

                    Counter = Series;
                    HumanWon = 0;
                    ComputerWon = 0;
                    DrawGame = 0;
                    mHumanFirst = true;
                    mGameOver = false;

                    txt_player1_score.setText(String.valueOf(HumanWon));
                    txt_player2_score.setText(String.valueOf(ComputerWon));
                    txt_draw_score.setText(String.valueOf(DrawGame));
//                    txt_player_turn.setText("Player Turn : " + player_1_name);
//                    txt_player_turn.setTextColor(Color.parseColor("#FF9E27"));

                    mGame = new AIGame();
                    startNewGame();

                }
            }));

            btnResume.setOnClickListener((new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            }));

            dialog.show();
        } catch (Exception e) {

            Toast.makeText(context, "Application Encountered some problem !!",
                    Toast.LENGTH_LONG).show();
        }
    }

}
