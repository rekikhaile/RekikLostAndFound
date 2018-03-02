package com.rekik.lostandfound.repository;

import com.rekik.lostandfound.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepo extends CrudRepository<AppUser, Long> {
    AppUser findAppUserByUsername(String username);
    Iterable <AppUser> findAllByUsernameIsContaining(String searchstring);

}
