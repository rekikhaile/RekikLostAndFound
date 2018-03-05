package com.rekik.lostandfound.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String image;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String password;

    private String firstName;

    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    //This needs to be instantiated in the construtor so you can use it to add and remove individual roles
    private Set<AppRole> roles;

    @ManyToMany(mappedBy = "lusers")
    private Set<LostItem> losts;

    /*@ManyToMany(mappedBy = "lusers")
    private Set<LostItem> losts;*/

    //own method
    public void addRole(AppRole role)
    {
        this.roles.add(role);
    }


    public AppUser() {
        this.roles = new HashSet<>();
        this.losts = new HashSet<>();// check
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppRole> appRoles) {
        this.roles = appRoles;
    }

    public Set<LostItem> getLosts() {
        return losts;
    }

    public void setLosts(Set<LostItem> losts) {
        this.losts = losts;
    }
}
