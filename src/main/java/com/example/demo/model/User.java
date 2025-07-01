package com.example.demo.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "tasks", "ownedProjects", "memberProjects" })
@ToString(exclude = { "tasks", "ownedProjects", "memberProjects" })
public class User extends BaseEntity implements UserDetails {

    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private String avatarUrl;
    private boolean enabled;
    private boolean credentialsNonExpired = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Project> ownedProjects = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Project> memberProjects = new HashSet<>();

    @OneToMany(mappedBy = "uploadedBy")
    private Set<TaskAttachment> uploads = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // or implement your own logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // or implement your own logic
    }

    @Override
    public boolean isEnabled() {
        return this.enabled; // uses your existing enabled field
    }

    @Override
    public String getPassword() {
        return this.password; // uses your existing password field
    }

    @Override
    public String getUsername() {
        return this.username; // uses your existing username field
    }
}
