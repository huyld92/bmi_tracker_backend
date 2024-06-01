/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fu.bmi_tracker.model.enums.EGender;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Duc Huy
 */
@Data
@NoArgsConstructor
public class CustomAccountDetailsImpl implements UserDetails {

    private Integer id;

    private String email;

    @JsonIgnore
    private String password;

    private EGender gender;

    private String fullName;

    private String phoneNumber;

    private LocalDate birthday;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomAccountDetailsImpl(Integer id, String email, String password, EGender gender, String fullName, String phoneNumber, LocalDate birthday, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.authorities = authorities;
    }

    public static CustomAccountDetailsImpl build(Account account) {

        List<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
        
        return new CustomAccountDetailsImpl(
                account.getAccountID(),
                account.getEmail(),
                account.getPassword(),
                account.getGender(),
                account.getFullName(),
                account.getPhoneNumber(),
                account.getBirthday(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomAccountDetailsImpl user = (CustomAccountDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
