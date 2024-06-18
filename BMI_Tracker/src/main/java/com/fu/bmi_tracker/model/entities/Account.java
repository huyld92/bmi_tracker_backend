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
import org.hibernate.annotations.SQLRestriction;

/**
 *
 * @author Duc Huy
 */
@Entity
@SQLRestriction(value = "IsActive = 1")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "[Account]")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID", nullable = false)
    private Integer accountID;

    @Column(name = "FullName", nullable = false)
    private String fullName;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "PhoneNumber", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "AccountPhoto", nullable = true)
    private String accountPhoto;

    @Column(name = "Gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Column(name = "DateOfBirth", nullable = false)
    private LocalDate birthday;

    @Column(name = "IsVerified", nullable = false)
    private Boolean isVerified;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @Column(name = "CreationDate", nullable = false)
    private LocalDate creationDate;

    @Column(name = "DeviceToken", nullable = true)
    private String deviceToken;

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

        this.accountPhoto = accountRequest.getAccountPhoto();

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

    public Account(Integer accountID) {
        this.accountID = accountID;
    }

}
