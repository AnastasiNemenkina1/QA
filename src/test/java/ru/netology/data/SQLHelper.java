package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/app?useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USER = "app";
    private static final String DEFAULT_PASS = "pass";

    private static String getUrl() {
        String url = System.getProperty("dblr1", DEFAULT_URL);
        if (!url.contains("useSSL=")) {
            url += (url.contains("?") ? "&" : "?") + "useSSL=false";
        }
        if (!url.contains("serverTimezone=")) {
            url += "&serverTimezone=UTC";
        }
        return url;
    }

    private static String getUser() {
        return System.getProperty("dbuser", DEFAULT_USER);
    }

    private static String getPassword() {
        return System.getProperty("dbpassword", DEFAULT_PASS);
    }

    @SneakyThrows
    public static Connection getConn() {
        // Регистрируем драйвер (для MySQL Connector/J 8.0+ это не обязательно, но для надежности)
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(getUrl(), getUser(), getPassword());
    }

    @SneakyThrows
    public static String getStatusForPayment() {
        String statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return getResult(statusSQL);
    }

    @SneakyThrows
    public static String getStatusForCredit() {
        String statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return getResult(statusSQL);
    }

    @SneakyThrows
    private static String getResult(String query) {
        try (var conn = getConn()) {
            return runner.query(conn, query, new ScalarHandler<>());
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var conn = getConn()) {
            runner.execute(conn, "DELETE FROM credit_request_entity");
            runner.execute(conn, "DELETE FROM order_entity");
            runner.execute(conn, "DELETE FROM payment_entity");
        }
    }

    public static void testConnection() {
        try (var conn = getConn()) {
            if (!conn.isValid(1000)) {
                throw new RuntimeException("Database connection is invalid");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }
}