package com.gongjun.J20201116;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 19:05 2020/11/16
 */
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class access {
    public static void main(String[] args) {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:nbos";//databaseName就是刚刚添加的数据源名称
            Connection con = DriverManager.getConnection(url, "", "");//没有用户名和密码的时候直接为空
            Statement sta = con.createStatement();
            ResultSet rst = sta.executeQuery("select * from bus");//demoTable为access数据库中的一个表名
            if(rst.next()){
                System.out.println(rst.getString("Detail"));
                //解决乱码问题
//				System.out.println(new String(rst.getBytes("name"), "gbk"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } /*catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/

    }
}

