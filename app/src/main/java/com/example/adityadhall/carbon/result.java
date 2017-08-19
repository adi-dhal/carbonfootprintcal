package com.example.adityadhall.carbon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.StreamHandler;

public class result extends AppCompatActivity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    TextView final_result_view;
    Button share_facebook;
    Button back;
    SharedPreferences sharedPreferences;
    SharedPreferences specificPrefernces;
    MyDBHandler dbHandler;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;
    Float final_result;
    public static String elec_key="elec_bill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_result);
        final_result_view=(TextView) findViewById(R.id.result_view);
        back=(Button)findViewById(R.id.back_home);
        share_facebook=(Button)findViewById(R.id.share_fb);
        shareDialog=new ShareDialog(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      //  specificPrefernces= getSharedPreferences("elec_bill", Context.MODE_PRIVATE);
        final_result=sharedPreferences.getFloat("carb_fp",-1);
        final_result=includegen(final_result);
        final_result_view.setText(String.valueOf(final_result));
        dbHandler = new MyDBHandler( this, null, null, 1 );
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dMyyyy");
        Date=simpleDateFormat.format(calendar.getTime());
    }

    private Float includegen(Float inter) {
        int elec_bill=Integer.valueOf(sharedPreferences.getString(Generic_Activity.elec_key,null));
        int gas_bill=Integer.valueOf(sharedPreferences.getString(Generic_Activity.gas_key,null));
        int h_mem=Integer.valueOf(sharedPreferences.getString(Generic_Activity.mem_key,null));
        boolean rec_paper=sharedPreferences.getBoolean(Generic_Activity.pap_key,true);
        boolean rec_tin=sharedPreferences.getBoolean(Generic_Activity.tin_key,true);
        inter=inter+((elec_bill/h_mem)*105*453);
        inter=inter+((gas_bill/h_mem)*105*453);
        if(!rec_paper)
            inter=inter+(184*453);
        if(!rec_tin)
            inter=inter+(166*453);
        return inter;

    }

    private void facebookSDKInitialize() {
        callbackManager=CallbackManager.Factory.create();
    }

    public void onClickback(View view) {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putFloat("carb_fp", (float) 0.0);
        editor.commit();
        long val=dbHandler.rec_val(Integer.valueOf(Date),final_result);
        Toast.makeText(this, String.valueOf(val), Toast.LENGTH_SHORT).show();
  //      Intent i=new Intent(this,MainActivity.class);
   //     startActivity(i);
    }

    public void onClickshare(View view) {
        String res_post="#GoGreen"+String.valueOf(final_result)+"co2 saved";
        if (ShareDialog.canShow(ShareLinkContent.class))
        {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://www.youtube.com"))
                    .setShareHashtag(new ShareHashtag.Builder()
                            .setHashtag(res_post)
                            .build())
        .build();
            shareDialog.show(content);
        }
    }
}
