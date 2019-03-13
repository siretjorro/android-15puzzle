package com.example.a15puzzle;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

public class Game implements Serializable {
    private String[] winningSolution = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",""};
    private String[] boardToShuffle = winningSolution.clone();
    private String[][] gameBoard;

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

    public boolean move(int clickedRow, int clickedCol) {
        int[] emptyCell = getEmptyCoordinates();
        int emptyCellRowNo = emptyCell[0];
        int emptyCellColNo = emptyCell[1];
        boolean isXAxis = clickedRow-emptyCellRowNo == 0;
        boolean isYAxis = clickedCol-emptyCellColNo == 0;

        if (!isXAxis && !isYAxis) {
            return false;
        }

        if (isXAxis) {
            if (clickedCol < emptyCellColNo) {
                for (int i = emptyCellColNo; i > clickedCol; i--) {
                    gameBoard[clickedRow][i] = gameBoard[clickedRow][i-1];
                }
            } else if (clickedCol > emptyCellColNo) {
                for (int i = emptyCellColNo; i < clickedCol; i++) {
                    gameBoard[clickedRow][i] = gameBoard[clickedRow][i+1];
                }
            }
        } else if (isYAxis) {
            if (clickedRow < emptyCellRowNo) {
                for (int i = emptyCellRowNo; i > clickedRow; i--) {
                    gameBoard[i][clickedCol] = gameBoard[i-1][clickedCol];
                }
            } else if (clickedRow > emptyCellRowNo) {
                for (int i = emptyCellRowNo; i < clickedRow; i++) {
                    gameBoard[i][clickedCol] = gameBoard[i+1][clickedCol];
                }
            }
        }

        gameBoard[clickedRow][clickedCol] = "";
        return true;
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
