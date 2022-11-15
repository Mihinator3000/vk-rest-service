package org.itmo.dao;

import org.itmo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Role getByName(String name);
}
