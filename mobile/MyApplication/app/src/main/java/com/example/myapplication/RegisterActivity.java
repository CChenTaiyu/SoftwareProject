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
import com.example.myapplication.entity.User;

public class RegisterActivity extends AppCompatActivity {

    EditText username = null;
    EditText password = null;
    EditText phone = null;
    boolean a = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
    }

    public void back_to_login(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void register(View view){

        String cusername = username.getText().toString();
        String cpassword = password.getText().toString();
        String cphone = phone.getText().toString();

        int lusername = cusername.length();
        int lpassword = cpassword.length();
        int lphone = cphone.length();

        User user = new User();

        user.setUsername(cusername);
        user.setPassword(cpassword);
        user.setPhone(cphone);
        user.setGender("n");

        for (int i = cphone.length();--i>=0;){
            if (!Character.isDigit(cphone.charAt(i))){
                a = false;
            }
        }

        new Thread(){
            @Override
            public void run() {

                int msg = 0;

                UserDao userDao = new UserDao();

                User uu = userDao.findUser(user.getUsername());

                if(uu != null){
                    msg = 1;
                }
                else{
                    if (lusername==0||lusername>30||lpassword==0||lpassword>30||lphone==0||lphone>15){
                        msg = 3;
                    }
                    else{
                        if(!a){
                            msg = 4;
                        }
                        else{
                            boolean flag = userDao.register(user);
                            if(flag){
                                msg = 2;
                            }
                        }
                    }
                }
                hand.sendEmptyMessage(msg);

            }
        }.start();


    }
    @SuppressLint("HandlerLeak")
    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0)
            {
                Toast.makeText(getApplicationContext(),"Login has failed",Toast.LENGTH_LONG).show();

            }
            if(msg.what == 1)
            {
                Toast.makeText(getApplicationContext(),"This account already exists.",Toast.LENGTH_LONG).show();

            }
            if(msg.what == 3)
            {
                Toast.makeText(getApplicationContext(),"The input length does not meet the requirements",Toast.LENGTH_LONG).show();

            }
            if(msg.what == 4)
            {
                Toast.makeText(getApplicationContext(),"Wrong phone number input",Toast.LENGTH_LONG).show();

            }
            if(msg.what == 2)
            {
                //startActivity(new Intent(getApplication(),MainActivity.class));

                Intent intent = new Intent();
                //将想要传递的数据用putExtra封装在intent中
                intent.putExtra("a","註冊");
                setResult(RESULT_CANCELED,intent);
                finish();
            }

        }
    };
}
