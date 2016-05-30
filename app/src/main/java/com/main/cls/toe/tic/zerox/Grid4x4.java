package com.main.cls.toe.tic.zerox;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.main.cls.toe.tic.zerox.R;

import java.util.Scanner;

/**
 * Created by Team 3 - ZeroX Game
 */
public class Grid4x4 extends AppCompatActivity implements View.OnClickListener {

    LinearLayout buttons[][];
    static TextView text[][];
    public static final int ROWS = 5, COLS = 5; 
    public static int[][] board = new int[ROWS][COLS]; 
    public static int[][] winner = new int[ROWS][COLS];
    public static int currentState;  
    public static int currentPlayer; 
    public static int currentRow, currentCol;
    
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int NOUGHT = 2;

    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int CROSS_WON = 2;
    public static final int NOUGHT_WON = 3;
    public static Scanner in = new Scanner(System.in);
    boolean validInput = false;
    int rowMove, colMove;
    TextView txt_draw_score, txt_player_turn, txt_player1_score, txt_player2_score, txt_player1_name, txt_player2_name;
    Handler handler;
    Context context;
    Intent intent;
    int Series, CrossWon, NoughtWon, DrawGame, Counter;
    String player_1_name, player_2_name;


    /**
     * Main page displays on the creation of grid screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid4);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Counter = Series;
                CrossWon = 0;
                NoughtWon = 0;
                DrawGame = 0;

                txt_player1_score.setText(String.valueOf(CrossWon));
                txt_player2_score.setText(String.valueOf(NoughtWon));
                txt_draw_score.setText(String.valueOf(DrawGame));
                txt_player_turn.setText("Player Turn : " + player_1_name);
                txt_player_turn.setTextColor(Color.parseColor("#FF9E27"));

                initGame();
            }
        });

        context = this;
        handler = new Handler();
        buttons = new LinearLayout[5][5];
        text = new TextView[5][5];
        intent = getIntent();

        player_1_name = intent.getStringExtra("Player_1_Name");
        player_2_name = intent.getStringExtra("Player_2_Name");
        Series = intent.getIntExtra("Series", 0);
        Counter = Series;

        initialize();

        txt_player1_name.setText(player_1_name);
        txt_player2_name.setText(player_2_name);
        txt_player_turn.setText("Player Turn : " + player_1_name);
        txt_player_turn.setTextColor(Color.parseColor("#FF9E27"));


        for (int row = 1; row < ROWS; ++row) {
            for (int col = 1; col < COLS; ++col) {
                buttons[row][col].setOnClickListener(this);
            }
        }

        initGame();

    }

    /**
     * Update the game board on the basis of user input and verify the game result.
     * @param seed
     * @param currentRow
     * @param currentCol
     */
    public void updateGame(int seed, int currentRow, int currentCol) {

        if (seed == CROSS) {
            text[currentRow][currentCol].setText("X");
            text[currentRow][currentCol].setTextColor(Color.parseColor("#FF9E27"));
            txt_player_turn.setTextColor(Color.parseColor("#F42B6F"));
        } else {
            text[currentRow][currentCol].setText("O");
            text[currentRow][currentCol].setTextColor(Color.parseColor("#F42B6F"));
            txt_player_turn.setTextColor(Color.parseColor("#FF9E27"));
        }

        if (hasWon(seed, currentRow, currentCol))
        {
            currentState = (seed == CROSS) ? CROSS_WON : NOUGHT_WON;

            for (int row = 1; row < ROWS; ++row) {
                for (int col = 1; col < COLS; ++col) {
                    if(winner[row][col] == 1) {
                        text[row][col].setTextColor(Color.parseColor("#27beff"));
                    }
                }
            }

            if(currentState == CROSS_WON)
            {
                CrossWon++;
                txt_player_turn.setText(player_1_name + " Wins !");
            }
            else if(currentState == NOUGHT_WON)
            {
                NoughtWon++;
                txt_player_turn.setText(player_2_name + " Wins !");
            }


            handler.postDelayed(new Runnable() {
                public void run() {
                    Counter--;
                    checkSeries();
                }
            }, 1 * 2000);

        } else if (isDraw()) {

            DrawGame++;
            txt_player_turn.setText(" Game Drawn !");
            txt_draw_score.setText(String.valueOf(DrawGame));

            handler.postDelayed(new Runnable() {
                public void run() {
                    currentState = DRAW;
                    Counter--;
                    checkSeries();

                }
            }, 1 * 2000);
        }
    }

    /**
     * Controls the series of games played by the user.
     */
    public void checkSeries()
    {
        System.out.println("XWon : " + CrossWon);
        System.out.println("OWon : " + NoughtWon);
        System.out.println("Draw : " + DrawGame);
        System.out.println("Counter : " + Counter);

        txt_player1_score.setText(String.valueOf(CrossWon));
        txt_player2_score.setText(String.valueOf(NoughtWon));
        txt_draw_score.setText(String.valueOf(DrawGame));

        if(Counter > 0)
        {
            initGame();
            txt_player_turn.setText("Player Turn : " + player_1_name);
        }
        else
        {
            if(CrossWon > NoughtWon)
            {
                currentState = CROSS_WON;
            }
            else if( NoughtWon > CrossWon)
            {
                currentState = NOUGHT_WON;
            }
            else
            {
                currentState = DRAW;
            }
            popup(false);
        }
    }

    /**
     * Verify if the game is drawn.
     * @return
     */
    public boolean isDraw() {
        for (int row = 1; row < ROWS; ++row) {
            for (int col = 1; col < COLS; ++col) {
                if (board[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the user wins the game
     * @param seed
     * @param currentRow
     * @param currentCol
     * @return
     */
    public boolean hasWon(int seed, int currentRow, int currentCol) {

        boolean response = false;

        if(board[currentRow][1] == seed         // 4-in-the-row
                && board[currentRow][2] == seed && board[currentRow][3] == seed && board[currentRow][4] == seed)
        {
            winner[currentRow][1] = 1;
            winner[currentRow][2] = 1;
            winner[currentRow][3] = 1;
            winner[currentRow][4] = 1;
            response = true;
        }else if(board[1][currentCol] == seed      // 4-in-the-column
                && board[2][currentCol] == seed && board[3][currentCol] == seed && board[4][currentCol] == seed)
        {
            winner[1][currentCol] = 1;
            winner[2][currentCol] = 1;
            winner[3][currentCol] = 1;
            winner[4][currentCol] = 1;
            response = true;
        }else if(currentRow == currentCol            // 4-in-the-diagonal
                && board[1][1] == seed
                && board[2][2] == seed
                && board[3][3] == seed
                && board[4][4] == seed)
        {
            winner[1][1] = 1;
            winner[2][2] = 1;
            winner[3][3] = 1;
            winner[4][4] = 1;
            response = true;
        }else if(currentRow + currentCol == 5  // 4-in-the-opposite-diagonal
                && board[4][1] == seed
                && board[3][2] == seed
                && board[2][3] == seed
                && board[1][4] == seed)
        {
            winner[4][1] = 1;
            winner[3][2] = 1;
            winner[2][3] = 1;
            winner[1][4] = 1;
            response = true;
        }
        return response;
    }

    /**
     * Initialize buttons and text on game board
     */
    public void initialize()
    {
        buttons[1][1] = (LinearLayout) findViewById(R.id.g4layout1);
        buttons[1][2] = (LinearLayout) findViewById(R.id.g4layout2);
        buttons[1][3] = (LinearLayout) findViewById(R.id.g4layout3);
        buttons[1][4] = (LinearLayout) findViewById(R.id.g4layout4);

        buttons[2][1] = (LinearLayout) findViewById(R.id.g4layout5);
        buttons[2][2] = (LinearLayout) findViewById(R.id.g4layout6);
        buttons[2][3] = (LinearLayout) findViewById(R.id.g4layout7);
        buttons[2][4] = (LinearLayout) findViewById(R.id.g4layout8);

        buttons[3][1] = (LinearLayout) findViewById(R.id.g4layout9);
        buttons[3][2] = (LinearLayout) findViewById(R.id.g4layout10);
        buttons[3][3] = (LinearLayout) findViewById(R.id.g4layout11);
        buttons[3][4] = (LinearLayout) findViewById(R.id.g4layout12);

        buttons[4][1] = (LinearLayout) findViewById(R.id.g4layout13);
        buttons[4][2] = (LinearLayout) findViewById(R.id.g4layout14);
        buttons[4][3] = (LinearLayout) findViewById(R.id.g4layout15);
        buttons[4][4] = (LinearLayout) findViewById(R.id.g4layout16);


        text[1][1] = (TextView) findViewById(R.id.g4text1);
        text[1][2] = (TextView) findViewById(R.id.g4text2);
        text[1][3] = (TextView) findViewById(R.id.g4text3);
        text[1][4] = (TextView) findViewById(R.id.g4text4);

        text[2][1] = (TextView) findViewById(R.id.g4text5);
        text[2][2] = (TextView) findViewById(R.id.g4text6);
        text[2][3] = (TextView) findViewById(R.id.g4text7);
        text[2][4] = (TextView) findViewById(R.id.g4text8);

        text[3][1] = (TextView) findViewById(R.id.g4text9);
        text[3][2] = (TextView) findViewById(R.id.g4text10);
        text[3][3] = (TextView) findViewById(R.id.g4text11);
        text[3][4] = (TextView) findViewById(R.id.g4text12);

        text[4][1] = (TextView) findViewById(R.id.g4text13);
        text[4][2] = (TextView) findViewById(R.id.g4text14);
        text[4][3] = (TextView) findViewById(R.id.g4text15);
        text[4][4] = (TextView) findViewById(R.id.g4text16);

        txt_player1_name = (TextView) findViewById(R.id.txt_player1_name);
        txt_player2_name = (TextView) findViewById(R.id.txt_player2_name);
        txt_player1_score = (TextView) findViewById(R.id.txt_player1_score);
        txt_player2_score = (TextView) findViewById(R.id.txt_player2_score);
        txt_draw_score = (TextView) findViewById(R.id.txt_draw_score);
        txt_player_turn = (TextView) findViewById(R.id.txt_player_turn);
    }

    /**
     * Initialize game
     */
    public void initGame() {
        for (int row = 1; row < ROWS; ++row) {
            for (int col = 1; col < COLS; ++col) {
                board[row][col] = EMPTY;  // all cells empty
                winner[row][col] = EMPTY;
                text[row][col].setText("-");
                text[row][col].setTextColor(Color.parseColor("#666666"));
            }
        }
        currentState = PLAYING; // ready to play
        currentPlayer = CROSS;  // cross plays first
    }

    /**
     * Mark user moves on the game board.
     * @param seed
     */
    public void playerMove(int seed) {

        if (rowMove >= 0 && rowMove < ROWS && colMove >= 0 && colMove < COLS && board[rowMove][colMove] == EMPTY) {
            currentRow = rowMove;
            currentCol = colMove;
            board[currentRow][currentCol] = seed;  // update game-board content
            validInput = true;  // input okay, exit loop
        } else {
            validInput = false;

            Toast.makeText(context, "InCorrect Move !!",
                    Toast.LENGTH_SHORT).show();

        }
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the current state and specifies the current turn on board
     * @param v
     */
    public void onClick(View v) {

        if(v == buttons[1][1])
        {
            rowMove = 1;
            colMove = 1;
        }
        else if(v == buttons[1][2])
        {
            rowMove = 1;
            colMove = 2;
        }else if(v == buttons[1][3])
        {
            rowMove = 1;
            colMove = 3;
        }else if(v == buttons[1][4])
        {
            rowMove = 1;
            colMove = 4;
        }
        else if(v == buttons[2][1])
        {
            rowMove = 2;
            colMove = 1;
        }
        else if(v == buttons[2][2])
        {
            rowMove = 2;
            colMove = 2;
        }else if(v == buttons[2][3])
        {
            rowMove = 2;
            colMove = 3;
        } else if(v == buttons[2][4])
        {
            rowMove = 2;
            colMove = 4;
        }
        else if(v == buttons[3][1])
        {
            rowMove = 3;
            colMove = 1;
        }
        else if(v == buttons[3][2])
        {
            rowMove = 3;
            colMove = 2;
        }
        else if(v == buttons[3][3])
        {
            rowMove = 3;
            colMove = 3;
        }
        else if(v == buttons[3][4])
        {
            rowMove = 3;
            colMove = 4;
        }
        else if(v == buttons[4][1])
        {
            rowMove = 4;
            colMove = 1;
        }
        else if(v == buttons[4][2])
        {
            rowMove = 4;
            colMove = 2;
        }
        else if(v == buttons[4][3])
        {
            rowMove = 4;
            colMove = 3;
        }
        else if(v == buttons[4][4])
        {
            rowMove = 4;
            colMove = 4;
        }


        if(currentState == PLAYING ) {
            playerMove(currentPlayer);

            if (validInput) {
                updateGame(currentPlayer, currentRow, currentCol);
                currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
                if(currentState == PLAYING) {
                    if (currentPlayer == CROSS)
                        txt_player_turn.setText("Player Turn : " + player_1_name);
                    else
                        txt_player_turn.setText("Player Turn : " + player_2_name);
                }
            }
        }
    }

    /**
     * Displays the popup to pause, exit or move to home screen.
     */
    @Override
    public void onBackPressed() {

        popup(true);
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

            // dialog.setTitle(dialogHeader);

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

                    Intent intent = new Intent(Grid4x4.this, GameMode.class);
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
                    CrossWon = 0;
                    NoughtWon = 0;
                    DrawGame = 0;

                    txt_player1_score.setText(String.valueOf(CrossWon));
                    txt_player2_score.setText(String.valueOf(NoughtWon));
                    txt_draw_score.setText(String.valueOf(DrawGame));
                    txt_player_turn.setText("Player Turn : " + player_1_name);
                    txt_player_turn.setTextColor(Color.parseColor("#FF9E27"));


                    initGame();

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
