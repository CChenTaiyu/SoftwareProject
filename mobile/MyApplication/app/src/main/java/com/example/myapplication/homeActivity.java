package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;

import com.example.myapplication.Dao.CourseDao;
import com.example.myapplication.Dao.jump;
import com.example.myapplication.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity {
    List<Course> courselist = new ArrayList<>();
    private String username;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new Thread(run).start();
        while (courselist.size()==0){
            continue;
        }
        LinearLayout ll=(LinearLayout) findViewById(R.id.event);
        for(Course p:courselist) {
            LinearLayout.LayoutParams ms=new LinearLayout.LayoutParams(650,320);
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.VERTICAL);
            l.setLayoutParams(ms);
            TextView tv=new TextView(this);
            tv.setText(p.toString());
            LinearLayout.LayoutParams ptv=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            ptv.setMargins(50,0,0,0);
            tv.setTextSize(18);
            tv.setWidth(600);
            tv.setHeight(150);
            tv.setLayoutParams(ptv);
            Button b=new Button(this);
            b.setText("Check");
            LinearLayout.LayoutParams layoutParamsb=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsb.setMargins(100,0,0,0);
            b.setLayoutParams(layoutParamsb);
            b.setOnClickListener(view -> {
                Intent intent1 = new Intent(getApplicationContext(),checkCourse.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Name",username);
                bundle1.putInt("Course",p.get_id());
                intent1.putExtras(bundle1);
                startActivity(intent1);
            });
            l.addView(tv);
            l.addView(b);
            l.setBackground(getResources().getDrawable(R.drawable.textview_border));
            ll.addView(l);
        }
    }

    Runnable run  = new Runnable() {
        @Override
        public void run() {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            username = bundle.getString("Name");
            CourseDao cd = new CourseDao();
            courselist = cd.findcourse(username);
        }
    };

    public void time_home(View view) {
        Intent intent = new Intent(getApplicationContext(), home.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}