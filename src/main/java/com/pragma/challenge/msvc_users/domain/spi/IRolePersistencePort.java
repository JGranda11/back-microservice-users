package com.pragma.challenge.msvc_users.domain.spi;

import com.pragma.challenge.msvc_users.domain.model.Role;
import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;

public interface IRolePersistencePort {
    Role findByName(RoleName role);
}
