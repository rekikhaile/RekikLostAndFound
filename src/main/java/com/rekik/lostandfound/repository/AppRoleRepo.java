package com.rekik.lostandfound.repository;

import com.rekik.lostandfound.model.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepo extends CrudRepository<AppRole, Long> {
    AppRole findAppRoleByRoleName(String roleName);
}
