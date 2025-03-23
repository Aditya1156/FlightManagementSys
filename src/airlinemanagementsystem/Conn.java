//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package airlinemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conn {
    Connection c;
    Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.c = DriverManager.getConnection("jdbc:mysql:///airlinemanagementsystem", "root", "123456");
            this.s = this.c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
