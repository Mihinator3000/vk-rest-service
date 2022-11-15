package org.itmo.dao;

import org.itmo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User getByUsername(String username);
}
