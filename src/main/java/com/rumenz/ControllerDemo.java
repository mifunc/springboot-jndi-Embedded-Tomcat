package com.rumenz;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@RestController
public class ControllerDemo {


    @Autowired
    HttpServletRequest req;

    @GetMapping("/")
    public String index() throws NamingException, SQLException {
        Context context = new InitialContext();
        //根据资源名称搜索
        Context ctx = new InitialContext();
        DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbcMydb");
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
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
        return "index";
    }
}
