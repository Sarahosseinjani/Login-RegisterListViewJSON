package com.example.loginregisterlistviewjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//comment are using sqlite database and then you neeed DatabaseHelper class , now we use room database
public class MainActivity extends AppCompatActivity {

    private TextView tvRegister;
    private EditText etLoginGmail,etLoginPassword;
    private Button loginButton;

   // private SQLiteDatabase db;
   // private SQLiteOpenHelper openHelper;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // openHelper = new DatabaseHelper(this);
        // db = openHelper.getReadableDatabase();
        tvRegister = findViewById(R.id.tvRegister);
        etLoginGmail = findViewById(R.id.etLogGmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        loginButton = findViewById(R.id.btnLogin);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginGmail.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter your Email and Password to login", Toast.LENGTH_SHORT).show();
                } else {
                    //check if this account exists
//                    cursor = db.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_2 + "=? AND " + DatabaseHelper.COL_3 + "=?", new String[]{email, password});
//                    if (cursor != null) {
//                        if (cursor.getCount() > 0) {
//                            //check internet connection
//                            // get from https://www.tutlane.com/tutorial/android/android-internet-connection-status-with-examples#:~:text=In%20android%2C%20we%20can%20determine,connection%20is%20available%20or%20not.
//                            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//                            NetworkInfo nInfo = cm.getActiveNetworkInfo();
//                            boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
//                            if(connected) {
//                                startActivity(new Intent(MainActivity.this, ListActivity.class));
//                                Toast.makeText(getApplicationContext(), "Login sucess", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(getApplicationContext(), "No Internet Connection" , Toast.LENGTH_SHORT).show();
//                            }
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
//                        }
//                    }

                    //*******************************************************

                    //Perform Query
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(email, password);
                            if(userEntity == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                            //check internet connection
                            // get from https://www.tutlane.com/tutorial/android/android-internet-connection-status-with-examples#:~:text=In%20android%2C%20we%20can%20determine,connection%20is%20available%20or%20not.
                            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo nInfo = cm.getActiveNetworkInfo();
                            boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(connected) {
                                        startActivity(new Intent(MainActivity.this, ListActivity.class));
                                        Toast.makeText(getApplicationContext(), "Login sucess", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "No Internet Connection" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            }
                        }
                    }).start();
                }
            }
        });



        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you don't have any account go to register page
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });

    }

}