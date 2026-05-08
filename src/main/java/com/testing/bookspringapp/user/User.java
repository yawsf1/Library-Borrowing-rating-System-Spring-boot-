package com.testing.bookspringapp.user;

import com.testing.bookspringapp.BaseEntity;
import com.testing.bookspringapp.bookRating.BookRating;
import com.testing.bookspringapp.bookBorrowing.BookBorrowing;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "_user")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @OneToMany(mappedBy = "user")
    private List<BookRating> userBooks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BookBorrowing> bookBorrowings = new ArrayList<>();


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

    public enum Role {
        USER,
        ADMIN
    }


    public boolean canManageRating(BookRating rating) {
        return this.role == Role.ADMIN || this.id.equals(rating.getUser().getId());
    }
    public boolean canManageBorrowing(BookBorrowing borrowing){
        return this.role == Role.ADMIN || this.id.equals(borrowing.getUser().getId());
    }

}
