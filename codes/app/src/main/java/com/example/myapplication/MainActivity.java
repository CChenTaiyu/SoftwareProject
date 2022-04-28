package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Dao.UserDao;

public class MainActivity extends AppCompatActivity {

    private String un;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reg(View view){

        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));

    }


    public void home(View view){
        EditText EditTextname = findViewById(R.id.username);
        EditText EditTextpassword = findViewById(R.id.password);
        un = EditTextname.getText().toString();

        new Thread(){
            @Override
            public void run() {

                UserDao userDao = new UserDao();

                boolean aa = userDao.login(EditTextname.getText().toString(),EditTextpassword.getText().toString());
                int msg = 0;
                if(aa){
                    msg = 1;
                }

                hand1.sendEmptyMessage(msg);


            }
        }.start();


    }
    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 1)
            {
                Intent intent = new Intent(getApplicationContext(),home.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",un);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
            }
        }
    };
}