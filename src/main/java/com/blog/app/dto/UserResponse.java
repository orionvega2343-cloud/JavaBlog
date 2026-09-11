package com.blog.app.dto;

import java.time.OffsetDateTime;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private OffsetDateTime createdAt;

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

    public OffsetDateTime getCreatedAt()
    {
        return createdAt;
    }

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

    public void setCreatedAt(OffsetDateTime createdAt)
    {
        this.createdAt = createdAt;
    }
}
