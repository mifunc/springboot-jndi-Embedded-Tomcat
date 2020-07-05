package com.rumenz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class MysqlTest implements ApplicationRunner {
    public static DataSource dataSource = null;

   static {
       Context context = null;
       try {
           context = new InitialContext();
       } catch (NamingException e) {
           e.printStackTrace();
       }
       //根据资源名称搜索
       try {
           dataSource = (DataSource)context.lookup("java:/comp/env/jdbcMydb");
       } catch (NamingException e) {
           e.printStackTrace();
       }
       System.out.println("static-----");
   }




    public void run(ApplicationArguments args) throws Exception {
        System.out.println("run---------");
        Statement stmt = null;
        try {

            Connection conn = dataSource.getConnection();
            //查询
            stmt = conn.createStatement();
            String sql = "SELECT id,name FROM qq limit 1";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                System.out.print("id: " + id);
                System.out.println(", name: " + name);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
    }




}
