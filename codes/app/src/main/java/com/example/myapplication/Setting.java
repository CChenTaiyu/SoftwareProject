package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity {
    Button button = null;
    String username;
    List<String> userlist= new ArrayList<>();
    List<Integer> data= new ArrayList<>();
    int id;
    RadioGroup rg;
    RadioButton nan,nv,unknown;
    EditText he, we;
    TextView ge;
    String radio="unknown";
    int height;
    int weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(run0).start();
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        new Thread(run0).start();
        username = bundle.getString("Name");
        while (data.size()==0){
            continue;
        }
        rg=findViewById(R.id.rg);
        nan=findViewById(R.id.nan);
        nv=findViewById(R.id.nv);
        unknown=findViewById(R.id.unknown);
        he=findViewById(R.id.height);
        we=findViewById(R.id.weight);
        ge=findViewById(R.id.gender);
        String height0 = String.valueOf(data.get(0));
        String weight0 = String.valueOf(data.get(1));
        he.setText(height0);
        we.setText(weight0);
        if (userlist.get(0).equals("m"))
        {
            nan.setChecked(true);
        }
        if (userlist.get(0).equals("f"))
        {
            nv.setChecked(true);
        }
        if (userlist.get(0).equals("unknown"))
        {
            unknown.setChecked(true);
        }
        ge.setText("Gender:");

        button = findViewById(R.id.cpassword);
        button.setOnClickListener(v -> {
            Intent intent2 = new Intent();
            intent2.setClass(Setting.this,ChangePassword.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("Name",username);
            intent2.putExtras(bundle2);
            startActivity(intent2);
        });
    }
    public void change(View view)
    {
        if (nan.isChecked()){
            radio = "m";
        }
        if (nv.isChecked()){
            radio = "f";
        }
        if (unknown.isChecked()){
            radio = "unknown";
        }
        height=Integer.parseInt(he.getText().toString());
        weight=Integer.parseInt(we.getText().toString());
        new Thread(run).start();
    }

    Runnable run0 = new Runnable() {
        @Override
        public void run() {
            String sql = "select * from user where username = ?";
            Connection con = JDBCUtils.getConn();
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                rs.first();
                userlist.add(rs.getString(5));
                data.add(rs.getInt(6));
                data.add(rs.getInt(7));
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    };

    Runnable run  = new Runnable() {
        @Override
        public void run() {
            String sql = "select * from user where username = ?";
            String sql2 = "UPDATE user SET gender = ? WHERE (id = ?);";
            String sql3 = "UPDATE user SET height = ? WHERE (id = ?);";
            String sql4 = "UPDATE user SET weight = ? WHERE (id = ?);";
            Connection con = JDBCUtils.getConn();
            try {
                PreparedStatement pst=con.prepareStatement(sql);
                pst.setString(1,username);
                ResultSet rs = pst.executeQuery();
                rs.first();
                id = rs.getInt(1);
                PreparedStatement pst2=con.prepareStatement(sql2);
                pst2.setString(1,radio);
                pst2.setInt(2,id);
                pst2.executeUpdate();
                PreparedStatement pst3=con.prepareStatement(sql3);
                pst3.setInt(1,height);
                pst3.setInt(2,id);
                pst3.executeUpdate();
                PreparedStatement pst4=con.prepareStatement(sql4);
                pst4.setInt(1,weight);
                pst4.setInt(2,id);
                pst4.executeUpdate();
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_LONG).show();
                Looper.loop();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    };

    public void time_home(View view) {
        Intent intent = new Intent(getApplicationContext(), UserCenter.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}