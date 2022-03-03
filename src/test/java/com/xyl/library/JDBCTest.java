package com.xyl.library;

import java.sql.*;

/**
 * @author by zuozuo
 */
public class JDBCTest {
    public static void main(String[] args) throws Exception{
        insert();
        //select();
        //update();
        //delete();

    }
    //获取连接对象
    private static Connection getConn(){
        //String driver="com.mysql.jdbc.Driver";
        String driver="com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/javatest";
        String username="root";
        String password="longfire";
        Connection conn = null;
        try{
            //classLoader,加载对应驱动
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    //插入操作
    private static int insert(){
        Connection conn=getConn();
        int i = 0;
        String sql="insert into tb_user(name,sex,email,birthday)values(?,?,?,?)";
        PreparedStatement pstmt;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"itcast");
            pstmt.setString(2,"男");
            pstmt.setString(3,"itcast@126.com");
            pstmt.setString(4,"2000-01-01");
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return i;
    }
    //删除操作
    private static int delete(){
        Connection conn=getConn();
        int i = 0;
        String sql="delete from tb_user where name=?";
        PreparedStatement pstmt;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"itcast");
            i = pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return i;
    }
    //更新操作
    private static int update(){
        Connection conn = getConn();
        int i = 0;
        String sql = "update tb_user setname=? where name=?";
        PreparedStatement pstmt;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"itcast");
            pstmt.setString(2,"abc");
            i = pstmt.executeUpdate();
            System.out.println("resutl:"+i);
            pstmt.close();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return i;
    }
    //查询操作
    private static void select() throws SQLException {
        Connection conn = getConn();
        String sql="select * from tb_user";
        PreparedStatement pstmt;
        try{
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");//通过列名获取指定字段的值
                String name=rs.getString("name");
                String sex=rs.getString("sex");
                String email=rs.getString("email");
                Date birthday=rs.getDate("birthday");
                System.out.println(id+" | "+name+" | "+sex+"| "+email+" | " + birthday);
            }
            pstmt.close();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
