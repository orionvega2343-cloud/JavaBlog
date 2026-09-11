package com.blog.app.domain;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class User {
    private Long id;
    private String name;
    private String email;
    private String passwordHash;
    private OffsetDateTime createdAt;

    //Геттеры
    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public OffsetDateTime getCreatedAt()
    {
        return createdAt;
    }

    //Сеттеры
    public void setId(Long id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    public void setCreatedAt(OffsetDateTime createdAt)
    {
        this.createdAt =createdAt;
    }

}


