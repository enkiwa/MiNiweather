package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MysqlUtil {
    private static String cityname;

    private static String w11;
    private static String w22;
    private static String w33;
    private static String w44;
    private static String w55;
    private static String w66;
    private static String w77;

    private static String t11;
    private static String t22;
    private static String t33;
    private static String t44;
    private static String t55;
    private static String t66;
    private static String t77;



    public static void creatnet(){
        final String DRIVER ="com.mysql.jdbc.Driver";
        final String URL ="jdbc:mysql://8.140.101.185:3306/db_aliyun";
        final String USERNMAE ="myapp";
        final String PASSWORD ="myapp";

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        String temp = "谢谢你";

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL,USERNMAE,PASSWORD);
            String sql = "select * from db_aliyun.svendays where city = ?";
            //使用prepareStatement保证安全性
            st = con.prepareStatement(sql);
            //通过修改city选择城市
            st.setString(1,cityname);
            rs = st.executeQuery();
            while (rs.next()){
                t11 = rs.getString("t1");
                t22 = rs.getString("t2");
                t33 = rs.getString("t3");
                t44 = rs.getString("t4");
                t55 = rs.getString("t5");
                t66 = rs.getString("t6");
                t77 = rs.getString("t7");
                w11 = rs.getString("w1");
                w22 = rs.getString("w2");
                w33 = rs.getString("w3");
                w44 = rs.getString("w4");
                w55 = rs.getString("w5");
                w66 = rs.getString("w6");
                w77 = rs.getString("w7");
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(rs!=null){rs.close();}
                if(st!=null){st.close();}
                if(con!=null){con.close();}

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setCityName(String c){
        cityname = c;
    }

    public static String gett1(){
        return t11;
    }
    public static String gett2(){
        return t22;
    }
    public static String gett3(){
        return t33;
    }
    public static String gett4(){
        return t44;
    }
    public static String gett5(){
        return t55;
    }
    public static String gett6(){
        return t66;
    }
    public static String gett7(){
        return t77;
    }
    public static String getw1(){
        return w11;
    }
    public static String getw2(){
        return w22;
    }
    public static String getw3(){
        return w33;
    }
    public static String getw4(){
        return w44;
    }
    public static String getw5(){
        return w55;
    }
    public static String getw6(){
        return w66;
    }
    public static String getw7(){
        return w77;
    }
}
