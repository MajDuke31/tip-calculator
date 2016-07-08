package com.example.student.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.text.Editable; //Interface allowing you to change the content and markup of text in a GUI.
import android.text.TextWatcher; //respond to events when the user interacts with an EditText component
import android.widget.EditText; //widget and layout for EditText component
import android.widget.SeekBar; // widget and layout for SeekBar component
import android.widget.SeekBar.OnSeekBarChangeListener; //respond to user moving the SeekBar’s thumb
import android.widget.TextView; //widget and layout for TextView component

public class TipCalculator extends AppCompatActivity {
    //constants used when saving/restoring state
    private static final String BILL_TOTAL = "BILL_TOTAL";
    private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

    private double currentBillTotal; //bill amount entered by the user
    private int currentCustomPercent; //tip % set with the SeekBar
    private EditText tip10EditText; //displays 10% tip
    private EditText total10EditText; //displays the total with 10% tip
    private EditText tip15EditText; //displays 15% tip
    private EditText total15EditText; //displays the total with 15% tip
    private EditText tip20EditText; //displays 20% tip
    private EditText total20EditText; //displays the total with 20% tip
    private EditText billEditText; //accepts user input for bill total
    private TextView customTipTextView; //displays custom tip percentage
    private EditText tipCustomEditText; //displays custom tip amount
    private EditText totalCustomEditText; //displays total with custom tip

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        // check if app just started or being restored from memory
        if (savedInstanceState == null)
        {
            // initialize some fields
            currentBillTotal = 0;
            currentCustomPercent = 18;
        } else // being restored from memory
        {
            // initialize fields from saved values
            currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
            currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
        }

        // get references to all the items on the GUI
        tip10EditText = (EditText) findViewById(R.id.tipTenEditText);
        total10EditText = (EditText) findViewById(R.id.totalTenEditText);
        tip15EditText = (EditText) findViewById(R.id.tipFifteenEditText);
        total15EditText = (EditText) findViewById(R.id.totalFifteenEditText);
        tip20EditText = (EditText) findViewById(R.id.tipTwentyEditText);
        total20EditText = (EditText) findViewById(R.id.totalTwentyEditText);

        customTipTextView = (TextView) findViewById(R.id.customTipTextView);

        tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
        totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

        billEditText = (EditText) findViewById(R.id.billEditText);
        // handle when the bill total changes
        billEditText.addTextChangedListener(billEditTextWatcher);

        SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tip_calculator, menu);
        return true;
    }

    // update the 10, 15 and 20% editTexts
    private void updateStandard() {
        // calculate total
        double tenPercentTip = currentBillTotal * 0.1;
        double tenPercentTotal = currentBillTotal + tenPercentTip;
        // set the corresponding editText value
        tip10EditText.setText(String.format("%.02f", tenPercentTip));
        total10EditText.setText(String.format("%.02f", tenPercentTotal));

        // calculate total
        double fifteenPercentTip = currentBillTotal * 0.15;
        double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;
        // set the corresponding editText value
        tip15EditText.setText(String.format("%.02f", fifteenPercentTip));
        total15EditText.setText(String.format("%.02f", fifteenPercentTotal));

        // calculate total
        double twentyPercentTip = currentBillTotal * 0.2;
        double twentyPercentTotal = currentBillTotal + twentyPercentTip;
        // set the corresponding editText value
        tip20EditText.setText(String.format("%.02f", twentyPercentTip));
        total20EditText.setText(String.format("%.02f", twentyPercentTotal));
    }

    // update custom total and tip
    private void updateCustom() {
        // match the position of the seekbar in the text view
        customTipTextView.setText(currentCustomPercent + "%");
        // calculate tip and total
        double customTipAmount = currentBillTotal *
                currentCustomPercent * 0.01;
        double customTotalAmount = currentBillTotal + customTipAmount;
        // set the corresponding editText value
        tipCustomEditText.setText(String.format("%.02f", customTipAmount));
        totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(BILL_TOTAL, currentBillTotal);
        outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
    }

    private OnSeekBarChangeListener customSeekBarListener =
            new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    currentCustomPercent = seekBar.getProgress();
                    updateCustom();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    private TextWatcher billEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // convert the editText to a double
            // 's' contains a copy of the text
            try {
                currentBillTotal = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                currentBillTotal = 0.0;
            }
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
