package com.rekik.lostandfound.repository;

import com.rekik.lostandfound.model.AppUser;
import com.rekik.lostandfound.model.LostItem;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;

public interface LostItemRepo extends CrudRepository<LostItem, Long> {
    HashSet<LostItem> findLostItemByLusersIn(HashSet <AppUser> lusers);
    HashSet<LostItem> findLostItemByLusers(AppUser appUser);

    HashSet<LostItem> findByStatus(boolean status);
    //Iterable<Book> findAllByBorrowed(boolean checkBorrowed);

    HashSet<LostItem> findByCategoryCatNameIgnoreCaseContaining(String category);
    HashSet<LostItem> findByCategoryCatNameIgnoreCase(String Category);
    HashSet<LostItem> findByCategoryCatNameIgnoreCaseAndLusers(String category, AppUser appUser);

    /*List<LostItem> findByCategoryNameIgnoreCaseContaining(String category);

    List<LostItem> findByCategoryNameIgnoreCase(String category);

    List<LostItem> findByCategoryNameIgnoreCaseAndLusers(String category, AppUser appUser);*/

}
