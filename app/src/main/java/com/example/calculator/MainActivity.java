package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, solution;
    MaterialButton btnC, btnBracOpen, btnBracClose;
    MaterialButton btnAdd, btnSub, btnMul, btnDiv, btnEqual;
    MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    MaterialButton btnDot, btnDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        solution = findViewById(R.id.solution);

        assignId(btnC, R.id.btn_c);
        assignId(btnBracOpen, R.id.btn_open_brac);
        assignId(btnBracClose, R.id.btn_close_brac);
        assignId(btnAdd, R.id.btn_add);
        assignId(btnSub, R.id.btn_sub);
        assignId(btnMul, R.id.btn_mul);
        assignId(btnDiv, R.id.btn_div);
        assignId(btnEqual, R.id.btn_equal);
        assignId(btnDot, R.id.btn_dot);
        assignId(btnDel, R.id.btn_del);
        assignId(btn0, R.id.btn_0);
        assignId(btn1, R.id.btn_1);
        assignId(btn2, R.id.btn_2);
        assignId(btn3, R.id.btn_3);
        assignId(btn4, R.id.btn_4);
        assignId(btn5, R.id.btn_5);
        assignId(btn6, R.id.btn_6);
        assignId(btn7, R.id.btn_7);
        assignId(btn8, R.id.btn_8);
        assignId(btn9, R.id.btn_9);

    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        MaterialButton button = (MaterialButton) view;
        String btnText = button.getText().toString();
        String  data = solution.getText().toString();

        if(btnText.equals("C")){
            solution.setText("");
            result.setText("0");
            return;
        }

        if(btnText.equals("=")){
            solution.setText(result.getText());
            return;
        }

        if(btnText.equals("del") && data.length()>1){
            data = data.substring(0,data.length()-1);
        }else if(btnText.equals("del") && data.length() == 1 && !data.equals("0")) {
            data = "0";
        }
        else if(btnText.equals("del") && data.equals("0")){
            data = "0";
        }
        else {
            data = data + btnText;
        }

        solution.setText(data);

        String finalResult = getResult(data);

        if(!finalResult.equals("Error")){
            result.setText(finalResult);
        }

    }

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable  scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data, "Javascript", 1, null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        }
        catch (Exception e){
            return "Error";
        }

    }
}