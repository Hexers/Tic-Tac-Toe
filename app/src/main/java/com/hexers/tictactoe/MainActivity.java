package com.hexers.tictactoe;

import java.text.NumberFormat;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private TextView gamemessagesLabel;
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
    private String billAmountString = "";
    private float tipPercent = .15f;
    private int turn = 0;

    // define rounding constants
    private final int ROUND_NONE = 0;
    private final int ROUND_TIP = 1;
    private final int ROUND_TOTAL = 2;

    // set up preferences
    private SharedPreferences prefs;
    private boolean rememberTipPercent = true;
    private int rounding = ROUND_NONE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets

        namePlayerOneTextView = (TextView) findViewById(R.id.namePlayerOneTextView);
        namePlayerTwoTextView = (TextView) findViewById(R.id.namePlayerTwoTextView);
        gamemessagesLabel = (TextView) findViewById(R.id.gamemessagesLabel);
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
        editor.putString("billAmountString", billAmountString);
        editor.putFloat("tipPercent", tipPercent);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get preferences
        rememberTipPercent = prefs.getBoolean("pref_remember_percent", true);
        rounding = Integer.parseInt(prefs.getString("pref_rounding", "0"));

        // get name for player one
        String playerOneName = prefs.getString("player_one_name", "");
        namePlayerOneTextView.setText(playerOneName);

        // get name for player two
        String playerTwoName = prefs.getString("player_two_name", "");
        namePlayerTwoTextView.setText(playerTwoName);

        // get the instance variables
        billAmountString = prefs.getString("billAmountString", "");
        if (rememberTipPercent) {
            tipPercent = prefs.getFloat("tipPercent", 0.15f);
        } else {
            tipPercent = 0.15f;
        }

        // set the bill amount on its widget
        // billAmountEditText.setText(billAmountString);

        // calculate and display
        calculateAndDisplay();
    }



    public void calculateAndDisplay() {
        // get the bill amount
        // billAmountString = billAmountEditText.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        }
        else {
            billAmount = Float.parseFloat(billAmountString);
        }

        // calculate tip and total
        float tipAmount = 0;
        float totalAmount = 0;
        float tipPercentToDisplay = 0;
        if (rounding == ROUND_NONE) {
            tipAmount = billAmount * tipPercent;
            totalAmount = billAmount + tipAmount;
            tipPercentToDisplay = tipPercent;
        }
        else if (rounding == ROUND_TIP) {
            tipAmount = StrictMath.round(billAmount * tipPercent);
            totalAmount = billAmount + tipAmount;
            tipPercentToDisplay = tipAmount / billAmount;
        }
        else if (rounding == ROUND_TOTAL) {
            float tipNotRounded = billAmount * tipPercent;
            totalAmount = StrictMath.round(billAmount + tipNotRounded);
            tipAmount = totalAmount - billAmount;
            tipPercentToDisplay = tipAmount / billAmount;
        }

        // display the other results with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        //tipTextView.setText(currency.format(tipAmount));
        //totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        //percentTextView.setText(percent.format(tipPercentToDisplay));
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
            calculateAndDisplay();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        countTurns++;



        String moveX = "X"; // Sets text on Button to X
        String moveO = "O"; // Sets text on Button to O
        switch (v.getId()) {
            case R.id.tileOneButton:
                tileOneButton.setActivated(true);
                tileOneButton.setText(moveX);
                tileOneButton.setBackgroundColor(Color.parseColor("#7598ff"));
                tileOneButton.setTextSize(50f);
                break;

            case R.id.tileTwoButton:
                tileTwoButton.setActivated(true);
                tileTwoButton.setText(moveO);
                tileTwoButton.setBackgroundColor(Color.parseColor("#ff5c5c"));
                tileTwoButton.setTextSize(50f);
                break;

            case R.id.tileThreeButton:
                tileThreeButton.setActivated(true);
                tileThreeButton.setText("X");
                break;

            case R.id.tileFourButton:
                tileFourButton.setActivated(true);
                tileFourButton.setText("X");
                break;

            case R.id.tileFiveButton:
                tileFiveButton.setActivated(true);
                tileFiveButton.setText("X");
                break;

            case R.id.tileSixButton:
                tileSixButton.setActivated(true);
                tileSixButton.setText("X");
                break;

            case R.id.tileSevenButton:
                tileSevenButton.setActivated(true);
                tileSevenButton.setText("X");
                break;

            case R.id.tileEightButton:
                tileEightButton.setActivated(true);
                tileEightButton.setText("X");
                break;

            case R.id.tileNineButton:
                tileNineButton.setActivated(true);
                tileNineButton.setText("X");
                break;

            case R.id.newGameButton:
                // Reset tiles back to blank
                tileOneButton.setText("");
                tileTwoButton.setText("");
                tileThreeButton.setText("");
                tileFourButton.setText("");
                tileFiveButton.setText("");
                tileSixButton.setText("");
                tileSevenButton.setText("");
                tileEightButton.setText("");
                tileNineButton.setText("");

                gamemessagesLabel.setText("");
                newGameButton.setText("New Game");

                // Reset color to #cccccc
                tileOneButton.setBackgroundColor(Color.parseColor("#cccccc"));
                tileTwoButton.setBackgroundColor(Color.parseColor("#cccccc"));
                tileThreeButton.setBackgroundColor(Color.parseColor("#cccccc"));
                tileFourButton.setBackgroundColor(Color.parseColor("#cccccc"));
                tileFiveButton.setBackgroundColor(Color.parseColor("#cccccc"));
                tileSixButton.setBackgroundColor(Color.parseColor("#cccccc"));
                tileSevenButton.setBackgroundColor(Color.parseColor("#cccccc"));
                tileEightButton.setBackgroundColor(Color.parseColor("#cccccc"));
                tileNineButton.setBackgroundColor(Color.parseColor("#cccccc"));

                tileOneButton.setActivated(false);
                tileTwoButton.setActivated(false);
                tileThreeButton.setActivated(false);
                tileFourButton.setActivated(false);
                tileFiveButton.setActivated(false);
                tileSixButton.setActivated(false);
                tileSevenButton.setActivated(false);
                tileEightButton.setActivated(false);
                tileNineButton.setActivated(false);

                break;
        }

        if (countTurns % 2 == 0 )
        {
            letter = "X";
        }
        else
        {
            letter = "O";
        }

        for ( int i = 0; i <= 7; i++)
        {

        }

        // Horizontal Winning Buttons
        if (tileOneButton.isActivated() && tileTwoButton.isActivated() && tileThreeButton.isActivated())
        {
            gamemessagesLabel.setText("Winner!");
        }

        if (tileFourButton.isActivated() && tileFiveButton.isActivated() && tileSixButton.isActivated())
        {
            gamemessagesLabel.setText("Winner!");
        }

        if (tileSevenButton.isActivated() && tileEightButton.isActivated() && tileNineButton.isActivated())
        {
            gamemessagesLabel.setText("Winner!");
        }

        // Vertical Winning Buttons
        if (tileOneButton.isActivated() && tileFourButton.isActivated() && tileSevenButton.isActivated())
        {
            gamemessagesLabel.setText("Winner!");
        }

        if (tileTwoButton.isActivated() && tileFiveButton.isActivated() && tileEightButton.isActivated())
        {
            gamemessagesLabel.setText("Winner!");
        }

        if (tileThreeButton.isActivated() && tileSixButton.isActivated() && tileNineButton.isActivated())
        {
            gamemessagesLabel.setText("Winner!");
        }

        // Diagonal Winning Buttons
        if (tileOneButton.isActivated() && tileFiveButton.isActivated() && tileNineButton.isActivated())
        {
            gamemessagesLabel.setText("Winner!");
        }

        if (tileThreeButton.isActivated() && tileFiveButton.isActivated() && tileSevenButton.isActivated())
        {
            gamemessagesLabel.setText("Winner!");
        }

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
