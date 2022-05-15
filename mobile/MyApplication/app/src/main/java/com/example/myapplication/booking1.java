package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class booking1 extends AppCompatActivity {
    String username, type;
    List<Course> courselist1= new ArrayList<>();
    volatile int sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking1);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("Name");
        type = bundle.getString("type");
        sign = 0;
        new Thread(run).start();

        while (sign==0){
        }
        LinearLayout ll= findViewById(R.id.courselist);
        for(Course p:courselist1) {
            //Layout
            LinearLayout l = new LinearLayout(this);
            l.setBackground(getResources().getDrawable(R.drawable.textview_border));
            //Text params
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,500);
            layoutParams.setMargins(25,0,0,0);
            //TextView
            TextView tv=new TextView(this);
            //Set params
            tv.setLayoutParams(layoutParams);
            tv.setText("   " + p.toString() + "\n " + "\n " + p.get_applicants() + "/" + p.get_capacity() + "  " + p.get_count() + "£");
            tv.setTextSize(20);
            tv.setWidth(600);
            tv.setHeight(400);
            //ImageView
            ImageView im = new ImageView(this);
            im.setImageResource(R.drawable.train);
            //Image params
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(200,500);
            parms.setMargins(50,25,0,0);
            im.setLayoutParams(parms);
            //Button params
            LinearLayout.LayoutParams layoutParamsb=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsb.setMargins(0,50,0,0);
            //ButtonView
            Button b = new Button(this);
            b.setLayoutParams(layoutParamsb);
            b.setText("Join");
            b.setOnClickListener(view -> {
                Intent intent1 = new Intent(getApplicationContext(),CoursePage.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Name",username);
                bundle1.putInt("Course",p.get_id());
                intent1.putExtras(bundle1);
                startActivity(intent1);
            });
            //Add them to layout
            l.addView(im);
            l.addView(tv);
            l.addView(b);
            ll.addView(l);
        }
    }
    public void time_home(View view) {
        Intent intent = new Intent(getApplicationContext(), home.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    Runnable run  = new Runnable() {
        @Override
        public void run() {
            String sql;
            Connection con = JDBCUtils.getConn();
            PreparedStatement pst;
            try {
                if(type.equals("all")){
                    sql = "SELECT * FROM nnr.course;";
                    pst=con.prepareStatement(sql);
                }
                else{
                    sql = "SELECT * FROM nnr.course where category = ?;";
                    pst=con.prepareStatement(sql);
                    pst.setString(1,type);
                }
                ResultSet rs = pst.executeQuery();
                while(rs.next()){
                    int id1 = rs.getInt(1);
                    String coursename = rs.getString(2);
                    Date date = rs.getTimestamp(3);
                    int capacity = rs.getInt(4);
                    int applicants = rs.getInt(5);
                    int count = rs.getInt(6);
                    String category = rs.getString(7);
                    int duration = rs.getInt(8);
                    String description = rs.getString(9);
                    Course course = new Course(id1, coursename, date, capacity, applicants, count, category, duration, description);
                    courselist1.add(course);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            sign = 1;
        }
    };
    public void sall(View view){
        Intent intent = new Intent(getApplicationContext(),Booking.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        bundle.putString("type","all");
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void sa(View view){
        Intent intent = new Intent(getApplicationContext(),Booking.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        bundle.putString("type","a");
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void sb(View view){
        Intent intent = new Intent(getApplicationContext(),Booking.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        bundle.putString("type","b");
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void sc(View view){
        Intent intent = new Intent(getApplicationContext(),Booking.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        bundle.putString("type","c");
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void sd(View view){
        Intent intent = new Intent(getApplicationContext(),Booking.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        bundle.putString("type","d");
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void se(View view){
        Intent intent = new Intent(getApplicationContext(),Booking.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        bundle.putString("type","e");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}