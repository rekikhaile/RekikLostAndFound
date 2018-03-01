package com.rekik.lostandfound.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class LostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String name;

    private String desc;

    private String image;

    private String status;

    @ManyToMany
    Set<AppUser> lusers;

    @ManyToOne
    private Category category;


    //own method
   public void addUsertoLost(AppUser aAppUser)
    {
        this.lusers.add(aAppUser);
    }


    public LostItem() {
        this.lusers = new HashSet<>();
        this.status = "lost";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<AppUser> getLusers() {
        return lusers;
    }

    public void setLusers(Set<AppUser> lusers) {
        this.lusers = lusers;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
