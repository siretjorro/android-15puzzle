package com.example.a15puzzle;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

public class Game implements Serializable {
    private String[] winningSolution = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",""};
    private String[] boardToShuffle = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",""};
    private String[][] gameBoard;
    private boolean successfulMove;

    public Game() {
        this.gameBoard = new String[4][4];
    }

    public void startGame() {
        Collections.shuffle(Arrays.asList(boardToShuffle));
        int k = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gameBoard[i][j] = boardToShuffle[k];
                k++;
            }
        }
    }

    public void move(int clickedRow, int clickedCol) {
        String toBeMoved = gameBoard[clickedRow][clickedCol];
        int[] emptyCell = getEmptyCoordinates();
        int emptyCellRowNo = emptyCell[0];
        int emptyCellColNo = emptyCell[1];

        if ((Math.abs(clickedRow-emptyCellRowNo) == 1 && Math.abs(clickedCol-emptyCellColNo) == 0) || (Math.abs(clickedRow-emptyCellRowNo) == 0 && Math.abs(clickedCol-emptyCellColNo) == 1)) {
            gameBoard[clickedRow][clickedCol] = "";
            gameBoard[emptyCellRowNo][emptyCellColNo] = toBeMoved;
            successfulMove = true;
        } else {
            successfulMove = false;
        }
    }

    public String getGameBoardValue(int row, int column) {
        return gameBoard[row][column];
    }

    public int[] getEmptyCoordinates() {
        int[] coordinates = new int[2];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[i][j] == "") {
                    coordinates[0] = i;
                    coordinates[1] = j;
                }
            }
        }

        return coordinates;
    }

    public boolean getSuccess() {
        return successfulMove;
    }

    public boolean isWon() {
        int k = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!gameBoard[i][j].equals(winningSolution[k])) {
                    return false;
                }

                k++;
            }
        }

        return true;
    }
}
