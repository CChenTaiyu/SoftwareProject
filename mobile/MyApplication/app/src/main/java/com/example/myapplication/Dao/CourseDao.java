package com.example.myapplication.Dao;

import com.example.myapplication.entity.Course;
import com.example.myapplication.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CourseDao {
    List<Course> courselist;
    public List<Course> findcourse(String username){
        String sql = "select * from user_course where username = ?";
        String sql1 = "select * from course where id = ?";
        Connection  con1 = JDBCUtils.getConn();

        courselist = new ArrayList<>();
        List<Integer> ls = new ArrayList<>();
        try {
            PreparedStatement pst=con1.prepareStatement(sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                int x = 0;
                String y = "No course!";
                Date z = new Date();
                Course course = new Course(x, y, z, 0, 0, 0, "0", 0, "asd");
                courselist.add(course);
                System.out.println(courselist);
            }
            else {
                rs.previous();
                while (rs.next()) {
                    int id = rs.getInt(3);
                    ls.add(id);
                }
                Iterator i = ls.iterator();
                System.out.println(ls);
                while(i.hasNext()) {
                    PreparedStatement pst1 = con1.prepareStatement(sql1);
                    pst1.setString(1, i.next().toString());
                    ResultSet rs1 = pst1.executeQuery();
                    rs1.first();
                    int id1 = rs1.getInt(1);
                    String coursename = rs1.getString(2);
                    Date date = rs1.getTimestamp(3);
                    int capacity = rs1.getInt(4);
                    int applicants = rs1.getInt(5);
                    int count = rs1.getInt(6);
                    String category = rs1.getString(7);
                    int duration = rs1.getInt(8);
                    String description = rs1.getString(9);
                    Course course = new Course(id1, coursename, date, capacity, applicants, count, category, duration, description);
                    courselist.add(course);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con1);
        }
        System.out.println(courselist);
        return courselist;
    }
}
