package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private TextView tipAmount;
    private TextView totalTip;
    private EditText inputPeople;
    private TextView perPerson;
    private TextView overage;
    private double money;
    private Double percent;
    private Double total;
    private Integer noOfpeople;
    private Double overagePerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.totalBillOut);
        tipAmount = findViewById(R.id.tipAmountOut);
        totalTip = findViewById(R.id.totalTipOut);
        inputPeople = findViewById(R.id.noPeopleOut);
        perPerson = findViewById(R.id.perPersonOut);
        overage = findViewById(R.id.overageOut);

    }

    public void clickGroup(View v){
        if (inputValue.getText().toString().trim().isEmpty()) {
            ((RadioButton)v).setChecked(false);
            return;
        }
        money = Double.parseDouble((inputValue.getText().toString()));

        if(v.getId() == R.id.button12){
            percent = 0.12;
        }
        else if(v.getId() == R.id.button15){
            percent = 0.15;
        }
        else if(v.getId() == R.id.button18){
            percent = 0.18;
        }
        else if(v.getId() == R.id.button20){
            percent = 0.20;
        }
        Double tip = money*percent;
        total = money+tip;


        tipAmount.setText(String.format("%.2f", tip));
        totalTip.setText(String.format("%.2f", total));

    }

    /*
        To calculate Overage
        In your example, 141.97 * 0.15 = 21.2955 (should be displayed as $21.30 using formatted string)

        Then 141.97 + 21.2955 = 163.2655 (should be displayed as $163.27 - save this, as you need it later)

        Then 163.2655 / 5 = 32.6531 (should be displayed as $32.65)

        Here, you must multiply the rounded price per person 32.65 by the number of people (5) = 163.25
        and subtract from that, the originally calculated (rounded) total with tip (163.27) to get the overage of 0.02

     */

    public void goPress(View v) {
        try {
            clickGroup(v);
            Double totalPeople = 0.0;

            noOfpeople = Integer.parseInt(inputPeople.getText().toString());
            totalPeople = Double.parseDouble(totalTip.getText().toString()) / noOfpeople;
            //Then 163.2655 / 5 = 32.6531 (should be displayed as $32.65)
            Double roundedTotalPeople = Double.parseDouble(String.format("%.2f", totalPeople));
            //roundedtotalPeople = 32.65
            Double roundedTotalTip = Double.parseDouble(String.format("%.2f", total));
            //roundedTotalTip = 163.27
            overagePerson = (roundedTotalPeople * noOfpeople) - roundedTotalTip;
            perPerson.setText(String.format("%.2f", totalPeople));
            totalTip.setText(String.format("%.2f", total));
            //totaltip = 163.2655
            overage.setText(String.format("%.2f", overagePerson));

            overage.setText(String.format("%.2f", overagePerson));
        } catch (Exception e) {

        }
    }

    public void clearPressed(View v){
        tipAmount.setText("");
        totalTip.setText("");
        perPerson.setText("");
        overage.setText("");
        inputValue.setText("");
        inputPeople.setText("");

        int[] radiobutton_ids = {R.id.button12, R.id.button15, R.id.button18, R.id.button20};
        for(int id : radiobutton_ids){
            RadioButton rb = findViewById(id);
            rb.setChecked(false);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("TIPAMOUNT", tipAmount.getText().toString());
        outState.putString("TOTALTIP", totalTip.getText().toString());
        outState.putString("PERPERSON", perPerson.getText().toString());
        outState.putString("OVERAGE", overage.getText().toString());
        outState.putString("INPUTVALUE", inputValue.getText().toString());
        outState.putString("INPUTPEOPLE", inputPeople.getText().toString());


        //CALL SUPER LAST
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //CALL SUPER FIRST
        super.onRestoreInstanceState(savedInstanceState);

        tipAmount.setText(savedInstanceState.getString("TIPAMOUNT"));
        totalTip.setText((savedInstanceState.getString("TOTALTIP")));
        perPerson.setText(savedInstanceState.getString("PERPERSON"));
        overage.setText(savedInstanceState.getString("OVERAGE"));
        inputValue.setText(savedInstanceState.getString("INPUTVALUE"));
        inputPeople.setText(savedInstanceState.getString("INPUTPEOPLE"));

    }
}