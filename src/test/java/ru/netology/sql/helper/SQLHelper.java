package ru.netology.sql.helper;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import  org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner;
    private static Connection conn;

    @SneakyThrows
    public static void setup() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection("jbdc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void resetUserStatus(String login) {
        setup();
        String dataSql = "UPDATE users SET status = 'active' WHERE login = ?;";
        runner.update(conn, dataSql, login);
    }

    @SneakyThrows
    public static void resetVerifyCode() {
        setup();
        String dataSql = "DELETE FROM auth_codes";
        runner.update(conn,dataSql);
    }

    @SneakyThrows
    public static String getValidVerifyCode(String login) {
        setup();
        String dataSql = "SELECT code FROM auth_codes" +
                "JOIN users ON users.id = users.id" +
                "WHERE login = 7" +
                "ORDER BY created DESC LIMIT 1;";
        return runner.query(conn, dataSql, new ScalarHandler<String>(), login);
    }

    @SneakyThrows
    public static String getUserStatus(String login) {
        setup();
        String dataSql = "SELECT status FROM users WHERE login = ?;";
        return runner.query(conn, dataSql, new ScalarHandler<String>(), login);
    }
}
