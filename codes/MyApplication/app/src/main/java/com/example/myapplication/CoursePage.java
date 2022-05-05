package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CoursePage extends AppCompatActivity {
    String username;
    int cid, sign;
    Date z = new Date();
    Date curDate =  new Date(System.currentTimeMillis());
    Course cou = new Course(0, "a", z, 0, 0,0, "a", 0, "asd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("Name");
        cid = bundle.getInt("Course");
        sign = 0;
        new Thread(run).start();
        while (cou.get_description().equals("asd")){
            continue;
        }
        TextView t1 = findViewById(R.id.coursename);
        TextView t2 = findViewById(R.id.coursedate);
        TextView t3 = findViewById(R.id.coursenumber);
        TextView t4 = findViewById(R.id.coursedes);
        t1.setText(cou.get_name());
        TextPaint paint = t1.getPaint();
        paint.setFakeBoldText(true);
        String format = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        t2.setText(formatter.format(cou.get_date()));
        t3.setText(cou.get_applicants()+"/"+cou.get_capacity()+"\n"+cou.get_duration()+"min"+"\n"+cou.get_count()+"£");
        t4.setText(cou.get_description());
    }
    Runnable run  = new Runnable() {
        @Override
        public void run() {
            String sql = "SELECT * FROM nnr.course where id = ?;";
            Connection con = JDBCUtils.getConn();
            try {
                PreparedStatement pst=con.prepareStatement(sql);
                pst.setInt(1, cid);
                ResultSet rs = pst.executeQuery();
                rs.first();
                int id1 = rs.getInt(1);
                String coursename = rs.getString(2);
                Date date = rs.getTimestamp(3);
                int capacity = rs.getInt(4);
                int applicnats = rs.getInt(5);
                int count = rs.getInt(6);
                String category = rs.getString(7);
                int duration = rs.getInt(8);
                String description = rs.getString(9);
                cou.set_id(id1);
                cou.set_name(coursename);
                cou.set_date(date);
                cou.set_capacity(capacity);
                cou.set_applicants(applicnats);
                cou.set_count(count);
                cou.set_capetory(category);
                cou.set_duration(duration);
                cou.set_description(description);
                sign = 1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    };

    public void time_home(View view) {
        Intent intent = new Intent(getApplicationContext(),Booking.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        bundle.putString("type", "all");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void book(View view) {
        new Thread(run1).start();
    }

    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            String sql = "INSERT INTO `nnr`.`user_course` (`username`, `courseid`, `course_name`) VALUES (?, ?, ?);";
            String sql1 = "SELECT * FROM nnr.user_course where username = ? and courseid = ?;";
            String sql2 = "UPDATE course SET applicants = applicants + 1 WHERE (id = ?);";
            String sql3 = "SELECT * FROM course where courseid = ?;";
            Connection con = JDBCUtils.getConn();
            PreparedStatement pst2, pst, pst3, pst1;
            try {
                if ((cou.get_date().getTime()-curDate.getTime())/(60*60*1000)>=1){
                    pst = con.prepareStatement(sql1);
                    pst.setString(1,username);
                    pst.setInt(2,cid);
                    ResultSet rs = pst.executeQuery();
                    rs.first();
                    if(rs.getRow()==0) {
                        if(cou.get_applicants()==cou.get_capacity()){
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "The course is full!", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                        else{
                            pst2 = con.prepareStatement(sql);
                            pst2.setString(1, username);
                            pst2.setInt(2, cid);
                            pst2.setString(3, cou.get_name());
                            pst2.executeUpdate();
                            pst3 = con.prepareStatement(sql2);
                            pst3.setInt(1, cid);
                            pst3.executeUpdate();
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "Book successfully!", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                    else{
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "No double booking!", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }
                else{
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "Course reservation is closed!", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    };
}