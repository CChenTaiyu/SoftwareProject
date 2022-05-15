package com.example.myapplication.Dao;

import com.example.myapplication.entity.User;
import com.example.myapplication.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public boolean login(String username,String password){

        String sql = "select * from user where username = ? and password = ?";

        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,username);
            pst.setString(2,password);

            if(pst.executeQuery().next()){

                return true;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }

        return false;
    }

    public boolean register(User user){

        String sql = "insert into user(username,password,phone,gender,height,weight) values (?,?,?,?,?,?)";

        Connection  con = JDBCUtils.getConn();

        try {
            PreparedStatement pst=con.prepareStatement(sql);

            pst.setString(1,user.getUsername());
            pst.setString(2,user.getPassword());
            pst.setString(3,user.getPhone());
            pst.setString(4,user.getGender());
            System.out.println(user.getGender());
            pst.setString(5,user.getHeight().toString());
            pst.setString(6,user.getWeight().toString());

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

    public User findUser(String username){
        String sql = "select * from user where username = ?";

        Connection  con = JDBCUtils.getConn();
        User user = null;
        try {
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){

                int id = rs.getInt(1);
                username = rs.getString(2);
                String password  = rs.getString(3);
                String phone = rs.getString(4);
                user = new User(id,username,password,phone,"n");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(con);
        }

        return user;
    }
}
