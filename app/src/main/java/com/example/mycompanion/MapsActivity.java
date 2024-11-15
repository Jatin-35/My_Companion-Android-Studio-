package com.example.mycompanion;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MapsActivity extends Activity {
    Button callbtn,msgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Cursor cursor=new DBHelper(getApplicationContext()).readalldata();
        String phoneNumber = null;
        String name  = null;
        while(cursor.moveToNext())
        {

            name = cursor.getString(0);
            phoneNumber =cursor.getString(3);

        }
        callbtn = findViewById(R.id.buttonCall);
        msgbtn = findViewById(R.id.buttonMessage);
        String finalName = name;
        String finalPhoneNumber = phoneNumber;
        msgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = finalName +" is in danger. Please help them at this location";
                sendMessage(finalPhoneNumber, message);
            }
        });
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + finalPhoneNumber));
                try{
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
            }
        });

    }

    private void sendMessage(String phoneNumber, String message) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", message);
        try {
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            finish();
        }
    }
}
