package com.blog.app.handlers;

import com.blog.app.domain.User;
import com.blog.app.domain.UserService;
import com.blog.app.dto.UpdateUserRequest;
import com.blog.app.dto.UserLoginRequest;
import com.blog.app.dto.UserRequest;
import com.blog.app.dto.UserResponse;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserHandlerImpl implements com.blog.app.domain.UserHandler {
    private UserService userService;
    private ObjectMapper objectMapper = new ObjectMapper();
    public UserHandlerImpl(UserService userService)
    {
        this.userService = userService;
    }

    public void handle(HttpExchange exchange) throws IOException
    {
        String method = exchange.getRequestMethod();
      switch (method) {
         case "POST":
             String source = exchange.getRequestURI().getPath();
             if (source.equals("/users"))
             {
                register(exchange);
             } else if (source.equals("/users/login"))
             {
                login(exchange);
             }
             break;

          case "GET":
              getUserById(exchange);
              break;

          case "PUT":
              updateUser(exchange);
              break;

          case "DELETE":
              deleteUser(exchange);
              break;
      }
    }

    private void register(HttpExchange exchange)  throws IOException
    {
        InputStream is = exchange.getRequestBody();
        UserRequest ur = objectMapper.readValue(is, UserRequest.class);
        User user = new User();
        user.setName(ur.getName());
        user.setEmail(ur.getEmail());
        User createdUser = userService.register(user, ur.getPassword());
        UserResponse response = new UserResponse();
        response.setId(createdUser.getId());
        response.setName(createdUser.getName());
        response.setEmail(createdUser.getEmail());
        response.setCreatedAt(createdUser.getCreatedAt());
        String json = objectMapper.writeValueAsString(response);
        byte[] bytes = json.getBytes();
        exchange.sendResponseHeaders(201, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private void login(HttpExchange exchange) throws IOException
    {
        InputStream is = exchange.getRequestBody();
        UserLoginRequest loginReq =  objectMapper.readValue(is, UserLoginRequest.class);
        User login = userService.login(loginReq.getEmail(), loginReq.getPassword());
        UserResponse response = new UserResponse();
        response.setId(login.getId());
        response.setName(login.getName());
        response.setEmail(login.getEmail());
        response.setCreatedAt(login.getCreatedAt());
        String json = objectMapper.writeValueAsString(response);
        byte[] bytes = json.getBytes();
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private void getUserById(HttpExchange exchange) throws IOException
    {
        String url = exchange.getRequestURI().getPath();
        String[] split = url.split("/");
        String lastElement = split[split.length - 1];
        Long id = Long.parseLong(lastElement);
        User getUser = userService.getUserById(id);
        UserResponse responseUser = new UserResponse();
        responseUser.setId(getUser.getId());
        responseUser.setName(getUser.getName());
        responseUser.setEmail(getUser.getEmail());
        responseUser.setCreatedAt(getUser.getCreatedAt());
        String toJson = objectMapper.writeValueAsString(responseUser);
        byte[] userBytes = toJson.getBytes();
        exchange.sendResponseHeaders(200, userBytes.length);
        OutputStream getOs = exchange.getResponseBody();
        getOs.write(userBytes);
        getOs.close();
    }

    private void updateUser(HttpExchange exchange) throws IOException
    {
        String updateUrl = exchange.getRequestURI().getPath();
        String[] splited = updateUrl.split("/");
        Long pId = Long.parseLong(splited[splited.length - 1]);
        InputStream inputStream = exchange.getRequestBody();
        UpdateUserRequest putReq = objectMapper.readValue(inputStream, UpdateUserRequest.class);
        User u = new User();
        u.setId(pId);
        u.setName(putReq.getName());
        userService.updateUser(u);
        exchange.sendResponseHeaders(200, -1);
        exchange.close();
    }

    private void deleteUser(HttpExchange exchange) throws IOException
    {
        String deleteUrl = exchange.getRequestURI().getPath();
        String[] deleteSplit = deleteUrl.split("/");
        Long deleteId = Long.parseLong(deleteSplit[deleteSplit.length - 1]);
        userService.deleteUser(deleteId);
        exchange.sendResponseHeaders(200, -1);
        exchange.close();
    }

}
