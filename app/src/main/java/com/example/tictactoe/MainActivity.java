package com.example.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] buttons = new Button[9]; //Tablica przechowujaca wszystkie przyciski
    private boolean playerTurn; //True - tura gracza 1, False - tura gracza 2
    private TextView player1; //Tekst o turze i punktach gracza 1
    private TextView player2; //Tekst o turze i punktach gracza 2
    private Integer pointsPlayer1; //Przechowuje ilosc pkt gracza 1
    private Integer pointsPlayer2; //Przechowuje ilosc pkt gracza 2
    private Integer clickedButtons; //Przechowuje ile przyciskow zostalo kliknietych by nie mozna bylo zaznaczyc wiecej niz 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = (TextView) findViewById(R.id.tv_Player1); //Pobranie informacji o TextView dla gracza 1
        player2 = (TextView) findViewById(R.id.tv_Player2); //Pobranie informacji o TextView dla gracza 2

        playerTurn = true; //Ustawienie tury na gracza 1
        changeTextColor(); //Zmiana koloru tekstow
        pointsPlayer1 = 0; //Ustawienie pkt dla gracza 1
        pointsPlayer2 = 0; //Ustawienie pkt dla gracza 1
        clickedButtons = 0; //Wyzerowanie kliknietych przyciskow

        player1.append(" " + pointsPlayer1.toString()); //Dodanie to tekstu ilosci pkt dla gracza 1
        player2.append(" " + pointsPlayer2.toString()); //Dodanie to tekstu ilosci pkt dla gracza 1

       for (int i = 0; i < 9; i++) { //Petla ktora leci po kazdym przycisku
            String buttonID = "btn_" + (i + 1); //Przyciski nazywaja sie btn_n, gdzie n to liczba 1-9 dlatego jest 0 + 1
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); //Pobranie ID przycisku

            buttons[i] = findViewById(resID); //Zapisanie informacji na dana pozycje do tabeli o przycisku uzywajac pobranego ID
            buttons[i].setOnClickListener(this); //Ustawienie wywolania funkcji dla danego przycisku w momencie klikniecia
        } //for

        Button reset = (Button) findViewById(R.id.btn_Reset); //Pobranie inforamcji o przycisku reset
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Ustawienie wywolania funkcji podczas klikniecia w przycisk reset
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

    private void resetBoard() { //Ustawienie kazdego przycisku na brak tekstu
        changeTextColor();
        clickedButtons = 0;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
        } //for
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) { //Kiedy przycisk ma juz jakis tekst to nic sie nie dzieje
            return;
        } //if

        if (playerTurn) { //Jesli tura gracza 1
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.RED);
            clickedButtons++;
        } else { //Jesli tura gracza 2
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.BLUE);
            clickedButtons++;
        } //if

        if (checkWin()) { //Sprawdzenie wygranej
            if (playerTurn) {
                pointsPlayer1++;
                player1.setText("Player 1: " + pointsPlayer1.toString());
            } else {
                pointsPlayer2++;
                player2.setText("Player 2: " + pointsPlayer2.toString());
            } //if

            resetBoard();
        } //if

        if (clickedButtons == 9) { //Jesli nikt nie wygral, ale kliknieto wszystkie pola
            resetBoard();
        } //if

        playerTurn = !playerTurn; //Zmiana gracza (z wartosci true na false i odwrotnie) - po wygranej danego gracza rozpoczyna automatycznie kolejny gracz
        changeTextColor(); //Zmiana koloru tekstu
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

    private boolean checkWin() { //Sprawdzenie wygranej
        final Integer[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}}; //Mozliwe zapelnienie pol w przypadku wygranej

        for (int i = 0; i < 8; i++) { //Petla wykonuje sie dla kazdej mozliwej wygranej
            if (buttons[winPositions[i][0]].getText().toString().equals(buttons[winPositions[i][1]].getText().toString())
                    && buttons[winPositions[i][1]].getText().toString().equals(buttons[winPositions[i][2]].getText().toString())
                    && !buttons[winPositions[i][0]].getText().toString().equals("")) {
                return true; //Zwraca true jesli wygrano
            } //if
        } //for

        /*
        Funkcja dziala tak, ze petla leci po kazdej mozliwosci z "winPositions" i tam gdzie jest "winPositions[i][0]" to zaczynajac od 0 bedzie pobierany numer z {} w kazdej mozliwosci tzn.
        zaczynajac od pierwszej mozliwosci tj. {0, 1, 2} bedzie wygladac to tak:
        Pobranie tekstu przycisku buttons[0] i sprawdzenie czy jest rowny (equals) przyciskowi drugiemu buttons[1], bo taki jest w "winPositions[0][1]". Potem jest to samo dla przyciskow 1 i 2.
        Na koncu sprawdza czy przycisk buttons[0] nie jest rowny pustemu tekstowi by kazdy przycisk byl wypelniony.
        */

        return false; //Zwraca false jesli nie ma wygranej
    } //checkWin
}
