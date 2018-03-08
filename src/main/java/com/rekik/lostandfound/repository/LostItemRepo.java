package com.rekik.lostandfound.repository;

import com.rekik.lostandfound.model.AppUser;
import com.rekik.lostandfound.model.Category;
import com.rekik.lostandfound.model.LostItem;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;

public interface LostItemRepo extends CrudRepository<LostItem, Long> {
    HashSet<LostItem> findLostItemByLusersIn(HashSet <AppUser> lusers);
    HashSet<LostItem> findLostItemByLusers(AppUser appUser);

    Iterable<LostItem> findAllByCategoryCatNameContainingIgnoreCase(String search);

    HashSet<LostItem> findByStatus(boolean status);


    Iterable <LostItem> findAllByCategory(Category c);

    HashSet<LostItem> findByCategoryCatNameIgnoreCaseContaining(String category);
    HashSet<LostItem> findByCategoryCatNameIgnoreCase(String Category);
    HashSet<LostItem> findByCategoryCatNameIgnoreCaseAndLusers(String category, AppUser appUser);

    /*List<LostItem> findByCategoryNameIgnoreCaseContaining(String category);

    List<LostItem> findByCategoryNameIgnoreCase(String category);

    List<LostItem> findByCategoryNameIgnoreCaseAndLusers(String category, AppUser appUser);*/

   /* package me.afua.week6;

import org.springframework.data.repository.CrudRepository;

    public interface LostItemRepository extends CrudRepository<LostItem,Long> {
        //Find all items that are either lost or found
        Iterable <LostItem> findAllByLost(boolean isLost);
        Iterable <LostItem> findAllByOwnersContaining(AppUser user);

        Iterable <LostItem> findAllByOwnersContains(AppUser user);
        Iterable <LostItem> findAllByOwnersContainsAndLost(AppUser user,boolean lost);
        Iterable <LostItem> findAllByItemCategoryEqualsAndTitleContainingIgnoreCase(Category c,String description);
        Iterable <LostItem> findAllByItemCategoryEqualsAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(Category c,String title,String description);
        Iterable <LostItem> findAllByItemCategory(Category c);
        Iterable <LostItem> findAllByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
    }*/

}
