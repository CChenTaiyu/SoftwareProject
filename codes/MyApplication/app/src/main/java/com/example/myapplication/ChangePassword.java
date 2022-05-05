package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChangePassword extends AppCompatActivity {

    int id;
    String username;
    EditText ppassword;
    TextView a;
    TextView b;
    EditText npassword1;
    EditText npassword2;
    Button button = null;
    String password;
    String password2;
    String p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("Name");
        ppassword = findViewById(R.id.ppassword);
        a = findViewById(R.id.text1);
        b = findViewById(R.id.text2);
        npassword1 = findViewById(R.id.edit1);
        npassword2 = findViewById(R.id.edit2);
        button = findViewById(R.id.button7);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        npassword1.setVisibility(View.INVISIBLE);
        npassword2.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        new Thread(run).start();
    }
    public void previous(View view)
    {
        password2 = ppassword.getText().toString();
        if (password2.equals(password))
        {
            a.setVisibility(View.VISIBLE);
            b.setVisibility(View.VISIBLE);
            npassword1.setVisibility(View.VISIBLE);
            npassword2.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_LONG).show();
        }
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            String sql = "select * from user where username = ?";
            Connection con = JDBCUtils.getConn();
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                rs.first();
                password = rs.getString(3);
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    };

    public void change2(View view)
    {
        String p1 = npassword1.getText().toString();
        String p2 = npassword2.getText().toString();
        if (p1.equals(p2))
        {
            p = p1;
            new Thread(run0).start();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "The two passwords are inconsistent!", Toast.LENGTH_LONG).show();
        }
    }

    Runnable run0 = new Runnable() {
        @Override
        public void run() {
            String sql = "select * from user where username = ?";
            String sql2 = "UPDATE user SET password = ? WHERE (id = ?);";
            Connection con = JDBCUtils.getConn();
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                rs.first();
                id = rs.getInt(1);
                PreparedStatement pst2=con.prepareStatement(sql2);
                pst2.setString(1,p);
                pst2.setInt(2,id);
                pst2.executeUpdate();
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Modified successfully!", Toast.LENGTH_LONG).show();
                Looper.loop();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    };

    public void time_home(View view) {
        Intent intent = new Intent(getApplicationContext(), Setting.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}