package com.dcorn.api.utils.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
@AllArgsConstructor
public class ClaimDetail {

    private String id;

    private String email;

    private String fullname;

    private Collection<GrantedAuthority> authority;
}
