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

    public static final char HUMAN_PLAYER = 'X';
    public static final char ANDROID_PLAYER = '0';
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


    public void setMove(char player, int location)
    {
        mBoard[location] = player;
    }

    public int getComputerMove()
    {
        int move;

        for(int i = 0; i< getBoardSize(); i++)
        {
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER)
            {
                char curr = mBoard[i];
                mBoard[i] = ANDROID_PLAYER;
                if(checkForWinner() == 3)
                {
                    setMove(ANDROID_PLAYER,i);
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
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER)
            {
                char curr = mBoard[i];
                mBoard[i] = HUMAN_PLAYER;
                if(checkForWinner() == 2)
                {
                    setMove(ANDROID_PLAYER,i);
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
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == ANDROID_PLAYER );

        setMove(ANDROID_PLAYER,move);
        return move;
    }

    public int randomMove()
    {
        boolean move_flag = false;
        int move = -1;

        while(!move_flag) {
            move = mRand.nextInt(getBoardSize());

            for (int i = 0; i < getBoardSize(); i++) {
                if (move == i) {
                    if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER) {

                        move_flag = true;
                        break;
                    }
                }
            }
        }

        return move;
    }

    public int checkForWinner()
    {
        for(int i = 0; i<=6; i +=3)
        {
            if(mBoard[i]== HUMAN_PLAYER && mBoard[i+1] == HUMAN_PLAYER && mBoard[i+2]== HUMAN_PLAYER)
            {
                return 2;
            }
            if(mBoard[i]== ANDROID_PLAYER && mBoard[i+1] == ANDROID_PLAYER && mBoard[i+2]== ANDROID_PLAYER)
            {
                return 3;
            }

        }

        for(int i = 0; i<=2; i ++)
        {
            if(mBoard[i]== HUMAN_PLAYER && mBoard[i+3] == HUMAN_PLAYER && mBoard[i+6]== HUMAN_PLAYER)
            {
                return 2;
            }
            if(mBoard[i]== ANDROID_PLAYER && mBoard[i+3] == ANDROID_PLAYER && mBoard[i+6]== ANDROID_PLAYER)
            {
                return 3;
            }

        }

        if(mBoard[0]== HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8]== HUMAN_PLAYER )
        {
            return 2;
        }

        if( mBoard[2]== HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6]== HUMAN_PLAYER)
        {
            return 2;
        }

        if(mBoard[0]== ANDROID_PLAYER && mBoard[4] == ANDROID_PLAYER && mBoard[8]== ANDROID_PLAYER) {
            return 3;
        }

        if(mBoard[2]== ANDROID_PLAYER && mBoard[4] == ANDROID_PLAYER && mBoard[6]== ANDROID_PLAYER)
        {
            return 3;
        }
        for(int i = 0; i < getBoardSize(); i++)
        {
            if(mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDROID_PLAYER)
            {
                return 0;
            }
        }

        return 1;
    }
}
