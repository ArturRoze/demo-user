package com.example.demouser.model.entity.user;

import com.example.demouser.model.domain.capability.UserCapabilityName;
import com.example.demouser.model.domain.user.UserRole;
import com.example.demouser.model.entity.capability.UserCapability;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "app_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "app_user_id_seq")
    @SequenceGenerator(name = "app_user_id_seq", sequenceName = "app_user_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(value = STRING)
    @Column(name = "user_role", updatable = false)
    private UserRole userRole;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "user", fetch = LAZY)
    private List<UserCapability> userCapabilities = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<UserCapability> getUserCapabilities() {
        return userCapabilities;
    }

    public void setUserCapabilities(List<UserCapability> userCapabilities) {
        this.userCapabilities = userCapabilities;
    }

    public Set<UserCapabilityName> getCapabilityNames() {
        return getUserCapabilities().stream()
                .map(UserCapability::getUserCapabilityName)
                .collect(toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, userRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
