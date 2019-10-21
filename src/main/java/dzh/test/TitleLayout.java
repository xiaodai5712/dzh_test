package dzh.test;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TitleLayout extends LinearLayout implements View.OnClickListener
{
    public TitleLayout(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.my_title,this);
        ImageButton mBack = findViewById(R.id.id_my_back);
        mBack.setOnClickListener(this);

        Button button = findViewById(R.id.id_my_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_my_button:
            {
                Toast.makeText(getContext(), "you clicked the button", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.id_my_back:
            {
                ((Activity)getContext()).finish();
                break;
            }
        }

    }
}
