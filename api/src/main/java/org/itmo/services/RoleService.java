package org.itmo.services;

import lombok.RequiredArgsConstructor;
import org.itmo.dao.RoleDao;
import org.itmo.models.Role;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleDao roleDao;

    public Role getByName(String name) {
        return roleDao.getByName(name);
    }
}
