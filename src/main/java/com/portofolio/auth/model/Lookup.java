package com.portofolio.auth.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.portofolio.auth.enums.LookupCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="lookup",
uniqueConstraints={@UniqueConstraint(columnNames={"lookup_category","lookup_code"})}
)
@BatchSize(size=100)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lookup extends BaseEntity{

	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="lookup_seq",sequenceName="lookup_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="lookup_seq")
	@Column(name="lookup_id", nullable=false)
	private long lookupId;

	@Enumerated(EnumType.STRING)
	@Column(name="lookup_category", nullable=false, length=128)
	private LookupCategory lookupCategory;

	@Column(name="lookup_code", nullable=false, length=128)
	private String lookupCode;

	@Column(name="lookup_name", nullable=false, length=255)
	private String lookupName;
	
	@Column(name="lookup_name_idn", nullable=false, length=255)
	private String lookupNameIdn;

	@Column(name="lookup_description", length=1000)
	String lookupDescription;
	
}
