package np.com.bikramtuladhar.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = findViewById(R.id.tlCaculator);
        TextView expression = findViewById(R.id.expression);
        TextView result = findViewById(R.id.result);

        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View row = tableLayout.getChildAt(i);
            if (row instanceof TableRow) {
                TableRow tableRow = (TableRow) row;
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View view = tableRow.getChildAt(j);
                    if (view instanceof Button) {
                        Button button = (Button) view;

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String buttonText = ((Button) v).getText().toString();

                                if (buttonText.equals("CE") || buttonText.equals("C")) {
                                    expression.setText("");
                                    result.setText("");
                                    return;
                                }

                                if (buttonText.equals("Delete")) {
                                    String text = expression.getText().toString();
                                    if (text.length() > 0) {
                                        expression.setText(text.substring(0, text.length() - 1));
                                    }
                                    return;
                                }

                                if (buttonText.equals("=")) {
                                    ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
                                    double calResult = 0;

                                    try {
                                        calResult = (double) engine.eval(expression.getText().toString());
                                        result.setText(String.valueOf(calResult));
                                    } catch (Exception e) {
                                        result.setText("ERR");
                                    }

                                    return;
                                }

                                String exp = expression.getText() + buttonText;

                                expression.setText(exp);
                            }
                        });
                    }
                }
            }
        }
    }
}