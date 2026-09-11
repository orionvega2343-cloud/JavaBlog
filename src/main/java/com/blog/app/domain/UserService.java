package com.blog.app.domain;

public interface UserService
{
    User register(User user, String rawPassword);
    User login (String email, String password);
    User getUserById(Long id);
    void updateUser(User user);
    void deleteUser(Long id);
}
