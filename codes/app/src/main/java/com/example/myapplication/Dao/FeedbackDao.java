package com.example.myapplication.Dao;

import com.example.myapplication.entity.Feedback;
import com.example.myapplication.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackDao {

    public boolean suggesstion(Feedback feedback){

        String sql = "insert into feedback(content) values (?)";

        Connection con = JDBCUtils.getConn();


        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,feedback.get_content());

            int value = pst.executeUpdate();

            if(value>0){
                return true;
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }
        return false;
    }
}