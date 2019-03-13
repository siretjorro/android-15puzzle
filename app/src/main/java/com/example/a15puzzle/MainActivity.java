package com.example.a15puzzle;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String MOVE_COUNTER = "move_counter";
    private static final String TIME = "time";
    private static final String GAME = "game";

    private TextView mTextViewScore;
    private Chronometer mChronometerTime;
    private int noOfMoves;
    private Game game;

    long timeWhenStopped = 0;

    public final int[][] BUTTON_IDS = {{R.id.button00, R.id.button01, R.id.button02, R.id.button03}, {R.id.button10, R.id.button11, R.id.button12, R.id.button13}, {R.id.button20, R.id.button21, R.id.button22, R.id.button23}, {R.id.button30, R.id.button31, R.id.button32, R.id.button33}};

    public static Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewScore = findViewById(R.id.textViewScore);
        mChronometerTime = findViewById(R.id.chronometerTime);

        if (savedInstanceState != null) {
            noOfMoves = (Integer) savedInstanceState.getInt(MOVE_COUNTER);
            game = (Game) savedInstanceState.getSerializable(GAME);
            timeWhenStopped = savedInstanceState.getLong(TIME);
        } else {
            game = new Game();
            game.startGame();
        }

        buttons = new Button[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = (Button) findViewById(BUTTON_IDS[i][j]);
                buttons[i][j].setOnClickListener(this);
            }
        }

        showBoard();
    }

    public void onClick(View v) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (v.getId() == BUTTON_IDS[i][j]) {
                    buttonClick(i, j);
                }
            }
        }
    }

    public void buttonClick(int row, int column) {  
        if (game.move(row, column)) {
            noOfMoves++;
        }

        showBoard();

        if (game.isWon()) {
            Toast.makeText(MainActivity.this,
                    "You won! Number of moves: " + Integer.toString(noOfMoves),
                    Toast.LENGTH_LONG).show();
            newGame();
        }

    }

    public void newGame() {
        game = new Game();
        game.startGame();

        noOfMoves = 0;
        mChronometerTime.start();

        showBoard();

        mTextViewScore.setText(Integer.toString(noOfMoves));
    }

    public void showBoard() {
        int[] emptyCoordinates = game.getEmptyCoordinates();
        int emptyX = emptyCoordinates[0];
        int emptyY = emptyCoordinates[1];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText(game.getGameBoardValue(i,j));
                buttons[i][j].setVisibility(View.VISIBLE);
            }
        }

        buttons[emptyX][emptyY].setVisibility(View.INVISIBLE);
        mTextViewScore.setText(Integer.toString(noOfMoves));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        timeWhenStopped = mChronometerTime.getBase() - SystemClock.elapsedRealtime();
        mChronometerTime.stop();
        super.onSaveInstanceState(outState);
        outState.putInt(MOVE_COUNTER, noOfMoves);
        outState.putLong(TIME, timeWhenStopped);
        outState.putSerializable(GAME, game);
    }

    @Override
    protected void onPause() {
        timeWhenStopped = mChronometerTime.getBase() - SystemClock.elapsedRealtime();
        mChronometerTime.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mChronometerTime.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        mChronometerTime.start();
        super.onResume();
    }


}
