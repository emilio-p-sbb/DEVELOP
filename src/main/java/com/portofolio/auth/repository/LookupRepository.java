package com.portofolio.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.portofolio.auth.enums.LookupCategory;
import com.portofolio.auth.model.Lookup;

@Repository
public interface LookupRepository  extends JpaRepository<Lookup, Long> , JpaSpecificationExecutor<Lookup> {

	Lookup findByLookupCategoryAndLookupCode(LookupCategory category, String code);
	List<Lookup> findByLookupCategory(LookupCategory category);
	Lookup findByLookupCategoryAndLookupCodeAndLookupIdNot(LookupCategory category, String code, long lookupId);
	List<Lookup> findByLookupCategoryAndLookupIdNot(LookupCategory category, long lookupId);
	
}