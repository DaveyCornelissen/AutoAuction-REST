package com.dcorn.api.utils.security;

import com.dcorn.api.role.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomPrincipal extends User {
    @Getter
    @Setter
    private ClaimDetail claimDetail;

    private CustomPrincipal(String username, String password, Collection<GrantedAuthority> authorities, com.dcorn.api.user.User user) {
        super(username, password, authorities);
        this.claimDetail = convertToClaim(user);
    }

    public static CustomPrincipal create(com.dcorn.api.user.User user) {
        return new CustomPrincipal(user.getEmail(), user.getPassword(), convertToCollection(user.getRoles()), user);
    }

    /**
     * Convert the Account class and return a ClaimDetail class.
     *
     * @return ClaimDetail
     * @author dcorn
     */
    private ClaimDetail convertToClaim(com.dcorn.api.user.User user) {
        return new ClaimDetail(user.getId().toString(), user.getEmail(), user.getFirstName(), super.getAuthorities());
    }


    /**
     * Convert the List<Role> to Collection<GrandAuthority>
     * @return Collection<GrandAuthority>
     * @author dcorn
     */
    private static Collection<GrantedAuthority> convertToCollection(List<Role> roles) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roles)
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getType().toString()));
        return grantedAuthorities;
    }
}
