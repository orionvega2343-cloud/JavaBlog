package com.blog.app.domain;

public interface UserRepository {
    User createUser(User user);
    User getUserById(Long id);
    User getUserByEmail(String email);
    void updateUser(User user);
    void deleteUser(Long id);
}
