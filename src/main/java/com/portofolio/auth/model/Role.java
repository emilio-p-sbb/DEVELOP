package com.portofolio.auth.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "role_seq", sequenceName = "role_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
	@Column(name = "role_id", nullable = false)
	private long roleId;
	
    @Column(unique = true, length = 128, nullable = false, name = "role_name")
	private String roleName;
    
    @OneToMany(mappedBy = "role")
    private Set<User> users;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;
    
    @Override
    public String getAuthority() {
        String role = this.roleName.toUpperCase();
        return String.format("ROLE_%s", role);
    }
}