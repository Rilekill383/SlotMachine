package com.example.android.slotmachine;

        import android.support.v7.app.AppCompatActivity;
        import android.widget.TextView;
        import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    private TextView TextViewPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextViewPoints = (TextView) findViewById(R.id.TextViewPoints);
        TextViewPoints.setText(getIntent().getIntExtra("current_Ps", 3)+ "");

        if (savedInstanceState !=null) {
            TextViewPoints.setText(savedInstanceState.getString("asdfgh"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)  {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("asdfgh", TextViewPoints.getText().toString());

    }
}
