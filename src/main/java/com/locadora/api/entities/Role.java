package com.locadora.api.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_CLIENT;

  public String getAuthority() {
    return name();
  }

}
