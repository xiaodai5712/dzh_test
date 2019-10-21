package dzh.test.android.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import dzh.test.R;

public class EighthActivity extends AppCompatActivity implements View.OnClickListener
{

    RadioButton mRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighth);
        mRadioButton = findViewById(R.id.radioButton);
        mRadioButton.setOnClickListener(this);
        mRadioButton.getText().toString();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.radioButton:
                Toast.makeText(this,"你选择了" + mRadioButton.getText().toString(),Toast.LENGTH_SHORT ).show();
                break;
        }
    }
}
