package com.example.demouser.model.entity.capability;

import com.example.demouser.model.domain.capability.UserCapabilityName;
import com.example.demouser.model.entity.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "user_capability")
public class UserCapability {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "user_capability_id_seq")
    @SequenceGenerator(name = "user_capability_id_seq", sequenceName = "user_capability_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Enumerated(STRING)
    @Column(name = "name", nullable = false)
    private UserCapabilityName userCapabilityName;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private User user;

    public UserCapability() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCapabilityName getUserCapabilityName() {
        return userCapabilityName;
    }

    public void setUserCapabilityName(UserCapabilityName userCapabilityName) {
        this.userCapabilityName = userCapabilityName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCapability that = (UserCapability) o;
        return Objects.equals(id, that.id) &&
                userCapabilityName == that.userCapabilityName &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userCapabilityName, user);
    }

    @Override
    public String toString() {
        return "UserCapability{" +
                "id=" + id +
                ", userCapabilityName=" + userCapabilityName +
                ", user=" + user +
                '}';
    }
}
