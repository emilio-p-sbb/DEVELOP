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
@Table(name = "permissions", uniqueConstraints = {@UniqueConstraint(columnNames = {"resource", "operation"})})
public class Permission implements GrantedAuthority {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="permission_seq",sequenceName="permission_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="permission_seq")
	@Column(name="permission_id")
	private long permissionId;
	
    @Column(nullable = false)
    private String resource;
    @Column(nullable = false)
    private String operation;
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
    @Override
    public String getAuthority() {
        return String.format("%s:%s", resource.toUpperCase(), operation.toUpperCase());
    }
}
