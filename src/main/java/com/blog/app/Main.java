package com.blog.app;

import com.blog.app.db.Db;
import com.zaxxer.hikari.HikariDataSource;
import config.AppConfig;
import config.DbConfig;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class Main
{
    public static void main(String[] args)
    {
        Yaml yaml = new Yaml(new Constructor(AppConfig.class, new LoaderOptions()));
        InputStream inputStream = Db.class.getClassLoader().getResourceAsStream("config.yml");
        AppConfig config = yaml.load(inputStream);

        HikariDataSource dataSourse = Db.connDb(config.getDb());
        boolean isHealthy = Db.healthCheck(dataSourse);
        if(!isHealthy)
        {
            System.out.print("failed connection to database");
        }

    }
}

