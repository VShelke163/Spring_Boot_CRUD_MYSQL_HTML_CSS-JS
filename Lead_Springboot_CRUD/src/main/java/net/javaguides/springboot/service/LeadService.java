package net.javaguides.springboot.service;

import java.util.List;
import org.springframework.data.domain.Page;
import net.javaguides.springboot.model.Springlead;

public interface LeadService {
	List<Springlead> getAllLeads();

	void saveLead(Springlead lead);

	Springlead getLeadById(long id);

	void deleteLeadById(long id);

	Page<Springlead> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
