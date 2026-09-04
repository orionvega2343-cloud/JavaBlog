package com.blog.app.db;
import config.DbConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Db
{
    // connDb - подключение Бд к проекту, создаем URL подключения, передаем геттеры,
    // создаем hikariConfig для чтения строки подключения и открытия пула соединений
    public static HikariDataSource connDb(DbConfig dbConfig)
    {
        String connStr = "jdbc:postgresql://" + dbConfig.getDbHost() + ":" + dbConfig.getDbPort() + "/" + dbConfig.getDbName() + "?sslmode=" + dbConfig.getDbSslMode();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(connStr);
        hikariConfig.setUsername(dbConfig.getDbUser());
        hikariConfig.setPassword(dbConfig.getDbPassword());
        return new HikariDataSource(hikariConfig);
    }

    // healthCheck - проверка подключения к БД,
    // если catch ловит ошибку, он вернет false
    public static boolean healthCheck(HikariDataSource data)
    {
        try (Connection conn = data.getConnection()) {
           return conn.isValid(5);
        } catch (SQLException e) {
           return false;
        }
    }


}

