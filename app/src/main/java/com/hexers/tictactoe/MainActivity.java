package com.hexers.tictactoe;

import java.text.NumberFormat;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.style.BackgroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.w3c.dom.Text;

public class MainActivity extends Activity
        implements OnEditorActionListener, OnClickListener {

    // define variables for the widgets
    private TextView namePlayerOneTextView;
    private TextView namePlayerTwoTextView;
    private TextView gameMessagesLabel;
    private Button tileOneButton;
    private Button tileTwoButton;
    private Button tileThreeButton;
    private Button tileFourButton;
    private Button tileFiveButton;
    private Button tileSixButton;
    private Button tileSevenButton;
    private Button tileEightButton;
    private Button tileNineButton;
    private Button newGameButton;

    // define instance variables that should be saved
    private String tileOneText = "";
    private String tileTwoText = "";
    private String tileThreeText = "";
    private String tileFourText = "";
    private String tileFiveText = "";
    private String tileSixText = "";
    private String tileSevenText = "";
    private String tileEightText = "";
    private String tileNineText = "";

    int playerOneMoveCount;
    int playerTwoMoveCount;
    int totalMoves;
    int totalTurns;

    // set up preferences
    private SharedPreferences prefs;

    String currentMove;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        namePlayerOneTextView = (TextView) findViewById(R.id.namePlayerOneTextView);
        namePlayerTwoTextView = (TextView) findViewById(R.id.namePlayerTwoTextView);
        gameMessagesLabel = (TextView) findViewById(R.id.gameMessagesLabel);
        tileOneButton = (Button) findViewById(R.id.tileOneButton);
        tileTwoButton = (Button) findViewById(R.id.tileTwoButton);
        tileThreeButton = (Button) findViewById(R.id.tileThreeButton);
        tileFourButton = (Button) findViewById(R.id.tileFourButton);
        tileFiveButton = (Button) findViewById(R.id.tileFiveButton);
        tileSixButton = (Button) findViewById(R.id.tileSixButton);
        tileSevenButton = (Button) findViewById(R.id.tileSevenButton);
        tileEightButton = (Button) findViewById(R.id.tileEightButton);
        tileNineButton = (Button) findViewById(R.id.tileNineButton);
        newGameButton = (Button) findViewById(R.id.newGameButton);

        // set the listeners
        tileOneButton.setOnClickListener(this);
        tileTwoButton.setOnClickListener(this);
        tileThreeButton.setOnClickListener(this);
        tileFourButton.setOnClickListener(this);
        tileFiveButton.setOnClickListener(this);
        tileSixButton.setOnClickListener(this);
        tileSevenButton.setOnClickListener(this);
        tileEightButton.setOnClickListener(this);
        tileNineButton.setOnClickListener(this);
        newGameButton.setOnClickListener(this);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = prefs.edit();
        //editor.putString("billAmountString", billAmountString);
        //editor.putFloat("tipPercent", tipPercent);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get name for player one
        String playerOneName = prefs.getString("player_one_name", "");
        namePlayerOneTextView.setText(playerOneName);

        // get name for player two
        String playerTwoName = prefs.getString("player_two_name", "");
        namePlayerTwoTextView.setText(playerTwoName);

    }

    public void disableButtons()
    {
        tileOneButton.setClickable(false);
        tileTwoButton.setClickable(false);
        tileThreeButton.setClickable(false);
        tileFourButton.setClickable(false);
        tileFiveButton.setClickable(false);
        tileSixButton.setClickable(false);
        tileSevenButton.setClickable(false);
        tileEightButton.setClickable(false);
        tileNineButton.setClickable(false);
    }
    public void playerSwitch()
    {
        if (currentMove == "X")
        {
            currentMove = "O";
        }
        else
        {
            currentMove = "X";
        }
    }

    public void playerTurnMessage()
    {
        if (currentMove == "X")
        {
            gameMessagesLabel.setText("Player O's Turn"); // Opposite of currentMove
        }
        else
        {
            gameMessagesLabel.setText("Player X's Turn"); // Opposite of currentMove
        }
    }

    public void chooseWinners()
    {

        tileOneText = tileOneButton.getText().toString();
        tileTwoText = tileTwoButton.getText().toString();
        tileThreeText = tileThreeButton.getText().toString();
        tileFourText = tileFourButton.getText().toString();
        tileFiveText = tileFiveButton.getText().toString();
        tileSixText = tileSixButton.getText().toString();
        tileSevenText = tileSevenButton.getText().toString();
        tileEightText = tileEightButton.getText().toString();
        tileNineText = tileNineButton.getText().toString();

        String playerOneName = prefs.getString("player_one_name", "");
        String playerTwoName = prefs.getString("player_two_name", "");

        // Horizontal Winning Buttons
        if (tileOneButton.isActivated() && tileTwoButton.isActivated() && tileThreeButton.isActivated())
        {
            if (tileOneText == "X" && tileTwoText == "X" && tileThreeText == "X")
            {
                gameMessagesLabel.setText(playerOneName + " is the Winner!");
                disableButtons();
            }
            else if (tileOneText == "O" && tileTwoText == "O" && tileThreeText == "O")
            {
                gameMessagesLabel.setText(playerTwoName + " is the Winner!");
                disableButtons();
            }

        }

        if (tileFourButton.isActivated() && tileFiveButton.isActivated() && tileSixButton.isActivated())
        {
            if (tileFourText == "X" && tileFiveText == "X" && tileSixText == "X")
            {
                gameMessagesLabel.setText(playerOneName + " is the Winner!");
                disableButtons();
            }
            else if (tileFourText == "O" && tileFiveText == "O" && tileSixText == "O")
            {
                gameMessagesLabel.setText(playerTwoName + " is the Winner!");
                disableButtons();
            }
        }

        if (tileSevenButton.isActivated() && tileEightButton.isActivated() && tileNineButton.isActivated())
        {
            if (tileSevenText == "X" && tileEightText == "X" && tileNineText == "X")
            {
                gameMessagesLabel.setText(playerOneName + " is the Winner!");
                disableButtons();
            }
            else if (tileSevenText == "O" && tileEightText == "O" && tileNineText == "O")
            {
                gameMessagesLabel.setText(playerTwoName + " is the Winner!");
                disableButtons();
            }
        }

        // Vertical Winning Buttons
        if (tileOneButton.isActivated() && tileFourButton.isActivated() && tileSevenButton.isActivated())
        {
            if (tileOneText == "X" && tileFourText == "X" && tileSevenText == "X")
            {
                gameMessagesLabel.setText(playerOneName + " is the Winner!");
                disableButtons();
            }
            else if (tileOneText == "O" && tileFourText == "O" && tileSevenText == "O")
            {
                gameMessagesLabel.setText(playerTwoName + " is the Winner!");
                disableButtons();
            }
        }

        if (tileTwoButton.isActivated() && tileFiveButton.isActivated() && tileEightButton.isActivated())
        {
            if (tileTwoText == "X" && tileFiveText == "X" && tileEightText == "X")
            {
                gameMessagesLabel.setText(playerOneName + " is the Winner!");
                disableButtons();
            }
            else if (tileTwoText == "O" && tileFiveText == "O" && tileEightText == "O")
            {
                gameMessagesLabel.setText(playerTwoName + " is the Winner!");
                disableButtons();
            }
        }

        if (tileThreeButton.isActivated() && tileSixButton.isActivated() && tileNineButton.isActivated())
        {
            if (tileThreeText == "X" && tileSixText == "X" && tileNineText == "X")
            {
                gameMessagesLabel.setText(playerOneName + " is the Winner!");
                disableButtons();
            }
            else if (tileThreeText == "O" && tileSixText == "O" && tileNineText == "O")
            {
                gameMessagesLabel.setText(playerTwoName + " is the Winner!");
                disableButtons();
            }
        }

        // Diagonal Winning Buttons
        if (tileOneButton.isActivated() && tileFiveButton.isActivated() && tileNineButton.isActivated())
        {
            if (tileOneText == "X" && tileFiveText == "X" && tileNineText == "X")
            {
                gameMessagesLabel.setText(playerOneName + " is the Winner!");
                disableButtons();
            }
            else if (tileOneText == "O" && tileFiveText == "O" && tileNineText == "O")
            {
                gameMessagesLabel.setText(playerTwoName + " is the Winner!");
                disableButtons();
            }
        }

        if (tileThreeButton.isActivated() && tileFiveButton.isActivated() && tileSevenButton.isActivated())
        {
            if (tileThreeText == "X" && tileFiveText == "X" && tileSevenText == "X")
            {
                gameMessagesLabel.setText(playerOneName + " is the Winner!");
                disableButtons();
            }
            else if (tileThreeText == "O" && tileFiveText == "O" && tileSevenText == "O")
            {
                gameMessagesLabel.setText(playerTwoName + " is the Winner!");
                disableButtons();
            }
        }

        // tie
        if (tileOneButton.isActivated() && tileTwoButton.isActivated() && tileThreeButton.isActivated()
            && tileFourButton.isActivated() && tileFiveButton.isActivated() && tileSixButton.isActivated()
            && tileSevenButton.isActivated() && tileEightButton.isActivated() && tileNineButton.isActivated())
        {
            totalMoves = playerOneMoveCount + playerTwoMoveCount;
            if (totalMoves >= 9)
            {
                gameMessagesLabel.setText("Game is a draw!");
                disableButtons();
            }
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int keyCode = -1;
        if (event != null) {
            keyCode = event.getKeyCode();
        }
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED ||
                keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                keyCode == KeyEvent.KEYCODE_ENTER) {
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        totalTurns = 0;
            switch (v.getId()) {

                case R.id.tileOneButton:
                    if (totalTurns % 2 == 1) {
                        playerOneMoveCount++;
                        tileOneButton.setActivated(true);
                        tileOneButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileOneButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileOneButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileOneButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileOneButton.setClickable(false);
                        }
                        tileOneButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();

                    }
                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerTwoMoveCount++;
                        tileOneButton.setActivated(true);
                        tileOneButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileOneButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileOneButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileOneButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileOneButton.setClickable(false);
                        }
                        tileOneButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    break;

                case R.id.tileTwoButton:
                    if (totalTurns % 2 == 1)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileTwoButton.setActivated(true);
                        tileTwoButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileTwoButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileTwoButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileTwoButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileTwoButton.setClickable(false);
                        }
                        tileTwoButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerTwoMoveCount++;
                        tileTwoButton.setActivated(true);
                        tileTwoButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileTwoButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileTwoButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileTwoButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileTwoButton.setClickable(false);
                        }
                        tileTwoButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();

                    }
                    break;

                case R.id.tileThreeButton:
                    if (totalTurns % 2 == 1)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileThreeButton.setActivated(true);
                        tileThreeButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileThreeButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileThreeButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileThreeButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileThreeButton.setClickable(false);
                        }
                        tileThreeButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerTwoMoveCount++;
                        tileThreeButton.setActivated(true);
                        tileThreeButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileThreeButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileThreeButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileThreeButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileThreeButton.setClickable(false);
                        }
                        tileThreeButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    break;

                case R.id.tileFourButton:
                    if (totalTurns % 2 == 1)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileFourButton.setActivated(true);
                        tileFourButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileFourButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileFourButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileFourButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileFourButton.setClickable(false);
                        }
                        tileFourButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileFourButton.setActivated(true);
                        tileFourButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileFourButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileFourButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileFourButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileFourButton.setClickable(false);
                        }
                        tileFourButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    break;

                case R.id.tileFiveButton:
                    if (totalTurns % 2 == 1)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileFiveButton.setActivated(true);
                        tileFiveButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileFiveButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileFiveButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileFiveButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileFiveButton.setClickable(false);
                        }
                        tileFiveButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerTwoMoveCount++;
                        tileFiveButton.setActivated(true);
                        tileFiveButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileFiveButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileFiveButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileFiveButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileFiveButton.setClickable(false);
                        }
                        tileFiveButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    break;

                case R.id.tileSixButton:
                    if (totalTurns % 2 == 1)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileSixButton.setActivated(true);
                        tileSixButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileSixButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileSixButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileSixButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileSixButton.setClickable(false);
                        }
                        tileSixButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }

                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerTwoMoveCount++;
                        tileSixButton.setActivated(true);
                        tileSixButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileSixButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileSixButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileSixButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileSixButton.setClickable(false);
                        }
                        tileSixButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    break;

                case R.id.tileSevenButton:
                    if (totalTurns % 2 == 1)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileSevenButton.setActivated(true);
                        tileSevenButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileSevenButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileSevenButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileSevenButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileSevenButton.setClickable(false);
                        }
                        tileSevenButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerTwoMoveCount++;
                        tileSevenButton.setActivated(true);
                        tileSevenButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileSevenButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileSevenButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileSevenButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileSevenButton.setClickable(false);
                        }
                        tileSevenButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    break;

                case R.id.tileEightButton:
                    if (totalTurns % 2 == 1)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileEightButton.setActivated(true);
                        tileEightButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileEightButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileEightButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileEightButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileEightButton.setClickable(false);
                        }
                        tileEightButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerTwoMoveCount++;
                        tileEightButton.setActivated(true);
                        tileEightButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileEightButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileEightButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileEightButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileEightButton.setClickable(false);
                        }
                        tileEightButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    break;

                case R.id.tileNineButton:
                    if (totalTurns % 2 == 1)
                    {
                        totalTurns++;
                        playerOneMoveCount++;
                        tileNineButton.setActivated(true);
                        tileNineButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileNineButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileNineButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileNineButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileNineButton.setClickable(false);
                        }
                        tileNineButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    else if (totalTurns % 2 == 0)
                    {
                        totalTurns++;
                        playerTwoMoveCount++;
                        tileNineButton.setActivated(true);
                        tileNineButton.setText(currentMove);
                        if (currentMove == "X")
                        {
                            tileNineButton.setBackgroundColor(Color.parseColor("#7598ff"));
                            tileNineButton.setClickable(false);
                        }
                        else if (currentMove == "O")
                        {
                            tileNineButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                            tileNineButton.setClickable(false);
                        }
                        tileNineButton.setTextSize(50f);
                        playerTurnMessage();
                        playerSwitch();
                    }
                    break;

                case R.id.newGameButton:
                    // Reloads MainActivity class
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        chooseWinners();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                // Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
