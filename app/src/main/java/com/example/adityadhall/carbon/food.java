package com.example.adityadhall.carbon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class food extends Activity {

    private float food_fp=0;
    Button next_res;
    RadioButton non_veg;
    RadioButton part_veg;
    RadioButton veg;
    RadioButton vegan;
    SharedPreferences sharedPreference;
    private float final_daily=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        next_res=(Button)findViewById(R.id.next_result);
        non_veg=(RadioButton)findViewById(R.id.non_veg_rd);
        part_veg=(RadioButton)findViewById(R.id.part_veg_rd);
        veg=(RadioButton)findViewById(R.id.veg_rd);
        vegan=(RadioButton)findViewById(R.id.vegan_rd);
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void onClicknext_res(View view) {
        if(non_veg.isChecked())
        {
            food_fp=8200;
        }
        else if(part_veg.isChecked())
        {
            food_fp=6210;
        }
        else if(veg.isChecked())
        {
            food_fp=4224;
        }
        else if (vegan.isChecked())
        {
            food_fp=3726;
        }
        final_daily=food_fp+sharedPreference.getFloat("carb_fp",-1);
        SharedPreferences.Editor editor= sharedPreference.edit();
        editor.putFloat("carb_fp",final_daily);
        editor.commit();
        Intent i=new Intent(this,result.class);
        startActivity(i);
    }
}
