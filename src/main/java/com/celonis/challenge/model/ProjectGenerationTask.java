package com.celonis.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
@Table(name = "PROJECT_GENERATION_TASK")
public class ProjectGenerationTask {

    @Id
    //https://stackoverflow.com/questions/18205574/difference-between-generatedvalue-and-genericgenerator
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @JsonIgnore
    @Column(name = "CO2_EMISSION")
    private String storageLocation;

    public String getId() {
        org.hibernate.id.UUIDGenerator ul;
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
