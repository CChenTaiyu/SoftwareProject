package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Dao.FeedbackDao;
import com.example.myapplication.Dao.jump;
import com.example.myapplication.entity.Feedback;

import android.view.View;
import android.widget.Toast;

public class feedbackActivity extends AppCompatActivity {
    EditText content = null;
    String content2 = null;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        content = findViewById(R.id.feedback);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("Name");
    }

    public void suggesstion(View view) {
        new Thread(run).start();
        content2 = content.getText().toString();
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            System.out.println("cnmcnm");
            FeedbackDao feedbackDao = new FeedbackDao();
            Feedback feedback = new Feedback();
            if (content2.length() > 100) {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "The length exceeds the limit.(100 words)", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            else {
                feedback.set_content(content2);
                feedbackDao.suggesstion(feedback);
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Successfully! Thank you for your feedback", Toast.LENGTH_LONG).show();
                Looper.loop();
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
