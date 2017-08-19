package com.example.adityadhall.carbon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vuforia.ImageTarget;

public class info extends Activity {

    Button back;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        back =(Button) findViewById(R.id.back_image);
        status =(TextView) findViewById(R.id.textView2);

    }

    public void onClickback_image(View view) {
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
