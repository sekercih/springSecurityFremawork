package com.security.configuration;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.security.configuration.KisiPermission.*;

import java.util.Set;
import java.util.stream.Collectors;

public enum KisiRole {

    USER(Sets.newHashSet(USER_READ)),
    ADMIN(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE));
    private Set<KisiPermission> kisiPermission;

    public Set<KisiPermission> getKisiPermission() {
        return kisiPermission;
    }

    KisiRole(Set<KisiPermission> kisiPermission) {
        this.kisiPermission = kisiPermission;
    }


    public Set<SimpleGrantedAuthority> otoriteleriAl() {
        Set<SimpleGrantedAuthority> permission = getKisiPermission().stream().
                map(x -> new SimpleGrantedAuthority(x.getPermission())).
                collect(Collectors.toSet());
        permission.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permission;

    }
}