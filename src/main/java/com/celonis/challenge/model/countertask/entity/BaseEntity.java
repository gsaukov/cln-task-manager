package com.celonis.challenge.model.countertask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static org.springframework.util.StringUtils.isEmpty;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private String id;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "LAST_UPDATED_AT")
    private LocalDateTime lastUpdated;

    @Column(name = "LAST_UPDATED_BY")
    private String updatedBy;

    protected BaseEntity() {
        if(id == null) {
            id = uuid();
        }
    }

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        throw new IllegalArgumentException("Method 'equals' must be overridden from downstream implementation.");
    }

    @Override
    public int hashCode() {
        throw new IllegalArgumentException("Method 'hashCode' must be overridden from downstream implementation.");
    }

    @PrePersist
    private void prePersist() {
        String username = username();

        createdBy = username;
        updatedBy = username;

        LocalDateTime now = now();

        createdAt = now;
        lastUpdated = now;
    }

    @PreUpdate
    private void preUpdate() {
        updatedBy = username();

        lastUpdated = now();
    }

    protected String username() {
//        Authentication authentication = getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            return defaultIfEmpty(authentication.getName(), "anonymous");
//        }

        return "anonymous";
    }

    protected String defaultIfEmpty(String actualValue, String defaultValue) {
        return isEmpty(actualValue) ? defaultValue : actualValue;
    }

    protected static String uuid() {
        return randomUUID().toString().replace("-", "").toUpperCase();
    }

}

