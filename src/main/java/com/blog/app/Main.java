package com.blog.app;

import com.blog.app.db.Db;
import com.blog.app.domain.UserService;
import com.blog.app.handlers.UserHandlerImpl;
import com.blog.app.repository.UserRepo;
import com.blog.app.service.UserServiceImpl;
import com.sun.net.httpserver.HttpServer;
import com.zaxxer.hikari.HikariDataSource;
import config.AppConfig;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

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
        UserRepo userRepo = new UserRepo(dataSourse);
        UserService userService = new UserServiceImpl(userRepo);
        UserHandlerImpl userHandler = new UserHandlerImpl(userService);

        try
        {   HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
            server.createContext("/users", userHandler);
            server.start();
        } catch (IOException i)
        {
            throw new RuntimeException("failed to build server", i);
        }
        System.out.println("Running server");
    }
}

