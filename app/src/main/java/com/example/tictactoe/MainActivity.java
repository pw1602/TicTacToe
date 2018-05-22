package com.example.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] buttons = new Button[9];
    private boolean playerTurn;
    private TextView player1;
    private TextView player2;
    private Integer pointsPlayer1;
    private Integer pointsPlayer2;
    private Integer clickedButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = (TextView) findViewById(R.id.tv_Player1);
        player2 = (TextView) findViewById(R.id.tv_Player2);

        playerTurn = true;
        changeTextColor();
        pointsPlayer1 = 0;
        pointsPlayer2 = 0;
        clickedButtons = 0;

        player1.append(" " + pointsPlayer1.toString());
        player2.append(" " + pointsPlayer2.toString());

       for (int i = 0; i < 9; i++) {
            String buttonID = "btn_" + (i + 1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        } //for

        Button reset = (Button) findViewById(R.id.btn_Reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerTurn = true;
                changeTextColor();
                pointsPlayer1 = 0;
                pointsPlayer2 = 0;
                player1.setText("Player 1: " + pointsPlayer1.toString());
                player2.setText("Player 2: " + pointsPlayer2.toString());
                clickedButtons = 0;

                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setText("");
                } //for
            } //onClick
        });
    } //onCreate

    private void resetBoard() {
        changeTextColor();
        clickedButtons = 0;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
        } //for
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        } //if

        if (playerTurn) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.RED);
            clickedButtons++;
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.BLUE);
            clickedButtons++;
        } //if

        if (checkWin()) {
            if (playerTurn) {
                pointsPlayer1++;
                player1.setText("Player 1: " + pointsPlayer1.toString());
            } else {
                pointsPlayer2++;
                player2.setText("Player 2: " + pointsPlayer2.toString());
            } //if

            resetBoard();
        } //if

        if (clickedButtons == 9) {
            resetBoard();
        } //if

        playerTurn = !playerTurn;
        changeTextColor();
    } //onClick

    private void changeTextColor() {
        if (playerTurn) {
            player1.setTextColor(Color.RED);
            player2.setTextColor(Color.BLACK);
        } else {
            player1.setTextColor(Color.BLACK);
            player2.setTextColor(Color.BLUE);
        } //if
    } //changeTextColor

    private boolean checkWin() {
        final Integer[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        for (int i = 0; i < 8; i++) {
            if (buttons[winPositions[i][0]].getText().toString().equals(buttons[winPositions[i][1]].getText().toString())
                    && buttons[winPositions[i][1]].getText().toString().equals(buttons[winPositions[i][2]].getText().toString())
                    && !buttons[winPositions[i][0]].getText().toString().equals("")) {
                return true;
            } //if
        } //for

        return false;
    } //checkWin
}
