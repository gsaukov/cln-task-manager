package com.celonis.challenge.model.projectgenerationtask;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
@Table(name = "PROJECT_GENERATION_TASK")
public class ProjectGenerationTask {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "NAME")
    private String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @JsonIgnore
    @Column(name = "STORAGE_LOCATION")
    private String storageLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

}
