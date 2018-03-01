package com.rekik.lostandfound.repository;

import com.rekik.lostandfound.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Long>{
    Category findCategoryByCatName(String catName);
}
