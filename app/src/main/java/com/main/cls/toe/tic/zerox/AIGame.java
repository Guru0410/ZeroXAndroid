package com.main.cls.toe.tic.zerox;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Team 3.
 * This class contains the logic for artificial intelligence in our game.
 */
public class AIGame {
    private char mBoard[];
    private final static int BOARD_SIZE=9;

    public static final char HUMAN_TURN = 'X';
    public static final char ANDROID_TURN = '0';
    public static final char EMPTY_SPACE =' ';

    private Random mRand;

    public static int getBoardSize()
    {
        return BOARD_SIZE;
    }

    /**
     * Constructor of the class
     */
    public AIGame()
    {
        mBoard = new char[BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++)
        {
            mBoard[i] = EMPTY_SPACE;
            mRand = new Random();
        }
    }

    /**
     * Clears the board
     */
    public void clearBoard()
    {
        for (int i = 0; i<BOARD_SIZE; i++)
        {
            mBoard[i] = EMPTY_SPACE;
        }
    }

    /**
     * Selects the board cell.
     * @param player
     * @param location
     */
    public void setMove(char player, int location)
    {
        mBoard[location] = player;
    }

    /**
     * Main AI logic
     * @return
     */

    public int getComputerMove()
    {
        int move;

        for(int i = 0; i< getBoardSize(); i++)
        {
            if(mBoard[i] != HUMAN_TURN && mBoard[i] != ANDROID_TURN)
            {
                char curr = mBoard[i];
                mBoard[i] = ANDROID_TURN;
                if(checkForWinner() == 3)
                {
                    setMove(ANDROID_TURN,i);
                    return i;
                }
                else
                {
                    mBoard[i] = curr;
                }
            }
        }
        for(int i = 0; i< getBoardSize(); i++)
        {
            if(mBoard[i] != HUMAN_TURN && mBoard[i] != ANDROID_TURN)
            {
                char curr = mBoard[i];
                mBoard[i] = HUMAN_TURN;
                if(checkForWinner() == 2)
                {
                    setMove(ANDROID_TURN,i);
                    return i;
                }
                else
                {
                    mBoard[i] = curr;
                }
            }
        }
        do
        {
            move = mRand.nextInt(getBoardSize());
        } while (mBoard[move] == HUMAN_TURN || mBoard[move] == ANDROID_TURN );

        setMove(ANDROID_TURN,move);
        return move;
    }

    /**
     * Novice Level Functionality
     * @return
     */
    public int randomMove()
    {
        boolean move_flag = false;
        int move = -1;

        while(!move_flag) {
            move = mRand.nextInt(getBoardSize());

            for (int i = 0; i < getBoardSize(); i++) {
                if (move == i) {
                    if (mBoard[i] != HUMAN_TURN && mBoard[i] != ANDROID_TURN) {

                        move_flag = true;
                        break;
                    }
                }
            }
        }

        return move;
    }

    /**
     * Returns the final Winner
     * @return
     */
    public int checkForWinner()
    {
        for(int i = 0; i<=6; i +=3)
        {
            if(mBoard[i]== HUMAN_TURN && mBoard[i+1] == HUMAN_TURN && mBoard[i+2]== HUMAN_TURN)
            {
                return 2;
            }
            if(mBoard[i]== ANDROID_TURN && mBoard[i+1] == ANDROID_TURN && mBoard[i+2]== ANDROID_TURN)
            {
                return 3;
            }

        }

        for(int i = 0; i<=2; i ++)
        {
            if(mBoard[i]== HUMAN_TURN && mBoard[i+3] == HUMAN_TURN && mBoard[i+6]== HUMAN_TURN)
            {
                return 2;
            }
            if(mBoard[i]== ANDROID_TURN && mBoard[i+3] == ANDROID_TURN && mBoard[i+6]== ANDROID_TURN)
            {
                return 3;
            }

        }

        if(mBoard[0]== HUMAN_TURN && mBoard[4] == HUMAN_TURN && mBoard[8]== HUMAN_TURN )
        {
            return 2;
        }

        if( mBoard[2]== HUMAN_TURN && mBoard[4] == HUMAN_TURN && mBoard[6]== HUMAN_TURN)
        {
            return 2;
        }

        if(mBoard[0]== ANDROID_TURN && mBoard[4] == ANDROID_TURN && mBoard[8]== ANDROID_TURN) {
            return 3;
        }

        if(mBoard[2]== ANDROID_TURN && mBoard[4] == ANDROID_TURN && mBoard[6]== ANDROID_TURN)
        {
            return 3;
        }
        for(int i = 0; i < getBoardSize(); i++)
        {
            if(mBoard[i] != HUMAN_TURN && mBoard[i] != ANDROID_TURN)
            {
                return 0;
            }
        }

        return 1;
    }
}
