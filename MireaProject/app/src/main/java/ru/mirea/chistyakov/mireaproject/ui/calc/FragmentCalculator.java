package ru.mirea.chistyakov.mireaproject.ui.calc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.mirea.chistyakov.mireaproject.R;

public class FragmentCalculator extends Fragment implements View.OnClickListener {

    EditText num1;
    EditText num2;
    Button btnSum;
    Button btnDiff;
    Button btnMult;
    Button btnDiv;
    TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);
        result = root.findViewById(R.id.textView3);
        num1 = (EditText) root.findViewById(R.id.editTextTextNum1);
        num2 = (EditText) root.findViewById(R.id.editTextTextNum2);
        btnSum = (Button) root.findViewById(R.id.button);
        btnDiff = (Button) root.findViewById(R.id.button2);
        btnMult = (Button) root.findViewById(R.id.button3);
        btnDiv = (Button) root.findViewById(R.id.button4);

        btnSum.setOnClickListener(this);
        btnDiff.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        float n1 = Float.parseFloat(num1.getText().toString());
        float n2 = Float.parseFloat(num2.getText().toString());

        switch (v.getId()) {
            case R.id.button:
                result.setText(String.valueOf(n1 + n2));
                break;
            case R.id.button2:
                result.setText(String.valueOf(n1 - n2));
                break;
            case R.id.button3:
                result.setText(String.valueOf(n1 * n2));
                break;
            case R.id.button4:
                if (n2 == 0) {
                    result.setText("Нельзя делить на 0");
                    break;
                } else {
                    result.setText(String.valueOf(n1 / n2));
                    break;
                }
            default:
                break;
        }
    }
}