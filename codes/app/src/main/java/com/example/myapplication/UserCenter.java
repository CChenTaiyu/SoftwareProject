package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCenter extends AppCompatActivity {
    Button button,button2,button3,button4 = null;
    ImageButton button5 = null;
    String username;
    List<String> userlist= new ArrayList<>();
    List<String> ut= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        Intent intent1 = getIntent();
        Bundle bundle1 = intent1.getExtras();
        username = bundle1.getString("Name");
        new Thread(run).start();
        while (userlist.size()==0 || ut.size()==0){
            continue;
        }
        System.out.println(userlist.get(0));
        LinearLayout ll= findViewById(R.id.tv_user);
        TextView tv=new TextView(this);
        tv.setText(username);
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#8B008B"));
        ll.addView(tv);
        LinearLayout lll= findViewById(R.id.tv_sex);
        TextView tv_gender=new TextView(this);
        if(userlist.get(0).equals("m")){
            tv_gender.setText("Male");
        }
        else{
            tv_gender.setText("Female");
        }
        tv_gender.setTextSize(20);
        tv_gender.setTextColor(Color.parseColor("#8B008B"));
        lll.addView(tv_gender);
        System.out.println(ut);
        TextView body = findViewById(R.id.body_statistics);
        body.setText("Body data:\nHeight: "+userlist.get(1)+"cm\nWeight: "+userlist.get(2)+"kg");
        TextView info = findViewById(R.id.time_total);
        info.setText("Training date:\nTime: "+ut.get(0)+" min \nCalorie: "+ut.get(1)+" kcal");

        button = findViewById(R.id.logout);
        button2 = findViewById(R.id.menu);
        button3 = findViewById(R.id.setting);
        button4 = findViewById(R.id.support);
        button5 = findViewById(R.id.shopping1);

        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(UserCenter.this,RegisterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Name",username);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        button2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(UserCenter.this,home.class);
            Bundle bundle = new Bundle();
            bundle.putString("Name",username);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        button3.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(UserCenter.this,Setting.class);
            Bundle bundle = new Bundle();
            bundle.putString("Name",username);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        button4.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(UserCenter.this,feedbackActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Name",username);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        button5.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(UserCenter.this,shopping.class);
            Bundle bundle = new Bundle();
            bundle.putString("Name",username);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    Runnable run  = new Runnable() {
        @Override
        public void run() {
            String sql = "select * from user where username = ?";
            String sql1 = "SELECT sum(duration), sum(calorie)\n" +
                    "FROM user_course\n" +
                    "INNER JOIN course\n" +
                    "ON user_course.courseid=course.idcourse\n" +
                    "where username= ?;";
            Connection con = JDBCUtils.getConn();
            try {
                PreparedStatement pst=con.prepareStatement(sql);
                pst.setString(1,username);
                ResultSet rs = pst.executeQuery();
                rs.first();
                userlist.add(rs.getString(5));
                userlist.add(rs.getString(6));
                userlist.add(rs.getString(7));
                PreparedStatement pst1=con.prepareStatement(sql1);
                pst1.setString(1,username);
                ResultSet rs1 = pst1.executeQuery();
                rs1.first();
                ut.add(rs1.getString(1));
                ut.add(rs1.getString(2));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    };
}