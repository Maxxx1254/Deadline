package ru.netology.sql.helper;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner;
    private static Connection conn;

    @SneakyThrows
    public static void setUp() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }


    @SneakyThrows
    public static void resetUserStatus(String login) {
        setUp();
        String dataSql = "UPDATE users SET status = 'active' WHERE login = ?;";
        runner.update(conn, dataSql, login);
    }

    @SneakyThrows
    public static void resetVerifyCode() {
        setUp();
        String dataSql = "DELETE FROM auth_codes";
        runner.update(conn, dataSql);
    }

    @SneakyThrows
    public static String getVerifyCodeByLogin(String login, String sqlLimit) {
        setUp();
        var sqlQuery = "SELECT code FROM auth_codes " +
                "JOIN users ON user_id = users.id " +
                "WHERE login IN (?) " +
                "ORDER BY created DESC LIMIT " + sqlLimit + ";";
        return runner.query(conn, sqlQuery, new ScalarHandler<>(), login);
    }

    @SneakyThrows
    public void getUserStatus(String login) {
        setUp();
        String dataSql = "SELECT status FROM users WHERE login = ?;";
        runner.query(conn, dataSql, new ScalarHandler<String>(), login);
    }

    @SneakyThrows
    public void cleanTableCards() {
        String dataSqlCards = "TRUNCATE TABLE cards;";
        runner.update(conn, dataSqlCards);
    }

    @SneakyThrows
    public void cleanTableAuthCodes() {
        String dataSqlAuthCodes = "TRUNCATE TABLE auth_codes;";
        runner.update(dataSqlAuthCodes);
    }

    @SneakyThrows
    public void cleanTableCardTransactions() {
        String dataSqlCardTransactions = "TRUNCATE TABLE card_transactions;";
        runner.update(dataSqlCardTransactions);
    }

    @SneakyThrows
    public void cleanTableUsers() {
        String dataSqlUsers = "TRUNCATE TABLE users;";
        runner.update(dataSqlUsers);
    }
}
