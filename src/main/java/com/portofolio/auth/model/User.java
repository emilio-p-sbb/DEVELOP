package com.portofolio.auth.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="users_seq",sequenceName="users_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="users_seq")
	@Column(name="user_id",nullable=false)
    private Long userId;
	
	@Column(length=128,name="username",nullable=false)
    private String username;
	
	@Column(length=128,name="password")
    private String password;
	
	@Column(length=128,name="fullname")
	private String fullname;
	
	@Column(length=128,name="email")
	private String email;
	
	@Column(length=128,name="gender")
	private String gender;
	
	@Column(length=15 ,name="phone")
	private String phone;
	
	@Column(length=128,name="avatar")
	private String avatar;
    
    @ManyToOne
    private Role role;
    
    @OneToMany(mappedBy = "user")
    private Set<Token> tokens;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> authorities = new HashSet<>();
        this.role.getPermissions().forEach(permission -> authorities.add(permission.getAuthority()));
        authorities.add(role.getAuthority());
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
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
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
}