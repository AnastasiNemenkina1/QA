package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    // Добавляем параметры по умолчанию
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DEFAULT_USER = "app";
    private static final String DEFAULT_PASS = "pass";

    private static String getUrl() {
        return System.getProperty("db.url", DEFAULT_URL);
    }

    private static String getUser() {
        return System.getProperty("db.user", DEFAULT_USER);
    }

    private static String getPassword() {
        return System.getProperty("db.pass", DEFAULT_PASS);
    }

    @SneakyThrows
    public static Connection getConn() {
        // Регистрируем драйвер
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

    // Добавляем метод для проверки подключения
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