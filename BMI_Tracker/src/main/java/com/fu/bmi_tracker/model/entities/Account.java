/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.model.enums.EGender;
import com.fu.bmi_tracker.payload.request.CreateAccountRequest;
import com.fu.bmi_tracker.payload.request.UpdateAccountRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "[Account]")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private Integer accountID;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Password")
    private String password;

    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Column(name = "DateOfBirth")
    private LocalDate birthday;

    @Column(name = "IsVerified")
    private Boolean isVerified;

    @Column(name = "IsActive")
    private Boolean isActive;

    @Column(name = "CreationDate")
    private LocalDate creationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "RoleAccount",
            joinColumns = @JoinColumn(name = "AccountID"),
            inverseJoinColumns = @JoinColumn(name = "RoleID"))
    private Set<Role> roles;

    public Account(String fullName, String email, String phoneNumber, String password, EGender gender,
            LocalDate birthday, Boolean isActive, Set<Role> roles
    ) {
        this.roles = roles;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.isActive = isActive;
        this.creationDate = LocalDate.now(ZoneId.of("GMT+7"));
        this.isVerified = false;
    }

    public Account(CreateAccountRequest createAccountRequest) {
        this.roles = new HashSet<>();
        this.fullName = createAccountRequest.getFullName();
        this.email = createAccountRequest.getEmail();
        this.phoneNumber = createAccountRequest.getPhoneNumber();
        this.gender = createAccountRequest.getGender();
        this.birthday = createAccountRequest.getBirthday();
        this.isActive = true;
        this.creationDate = LocalDate.now(ZoneId.of("GMT+7"));
        this.isVerified = true;
    }

    public void updateAccount(UpdateAccountRequest accountRequest) {
        if (accountRequest.getFullName() != null) {
            this.fullName = accountRequest.getFullName();
        }
        if (accountRequest.getPhoneNumber() != null) {
            this.phoneNumber = accountRequest.getPhoneNumber();
        }
        if (accountRequest.getGender() != null) {
            this.gender = accountRequest.getGender();
        }
        if (accountRequest.getBirthday() != null) {
            this.birthday = accountRequest.getBirthday();
        }
        if (accountRequest.getRoles() != null) {
            this.roles = accountRequest.getRoles();
        }
        if (accountRequest.getIsActive() != null) {
            this.isActive = accountRequest.getIsActive();
        }

    }

}
