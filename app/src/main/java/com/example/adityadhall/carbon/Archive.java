package com.example.adityadhall.carbon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.nio.BufferUnderflowException;

public class Archive extends AppCompatActivity {

    private DatePicker datePicker;
    private Button search;
    private TextView res_view;
    MyDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive2);
        datePicker=(DatePicker)findViewById(R.id.datePicker);
        search=(Button)findViewById(R.id.srch_db);
        res_view=(TextView)findViewById(R.id.view_rec);
        dbHandler = new MyDBHandler( this, null, null, 1 );
    }

    public void onClicksearch(View view) {
        String date=String.valueOf(datePicker.getDayOfMonth())+String.valueOf(datePicker.getMonth()+1)+String.valueOf(datePicker.getYear());
        int int_date=Integer.valueOf(date);
        Float res=dbHandler.ret_val(int_date);

        res_view.setText(String.valueOf(res));
    }
}
