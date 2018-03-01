package com.rekik.lostandfound.repository;

import com.rekik.lostandfound.model.AppUser;
import com.rekik.lostandfound.model.LostItem;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;

public interface LostItemRepo extends CrudRepository<LostItem, Long> {
    HashSet<LostItem> findLostItemByLusersIn(HashSet <AppUser> lusers);
    HashSet<LostItem> findLostItemByLusers(AppUser appUser);
}
