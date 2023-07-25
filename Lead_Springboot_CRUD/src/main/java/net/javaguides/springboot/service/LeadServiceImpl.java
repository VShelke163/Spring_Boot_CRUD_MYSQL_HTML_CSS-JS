package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Springlead;
import net.javaguides.springboot.repository.LeadRepository;

@Service
public class LeadServiceImpl implements LeadService {

	@Autowired
	private LeadRepository leadRepository;

	@Override
	public List<Springlead> getAllLeads() {
		return leadRepository.findAll();
	}

	@Override
	public void saveLead(Springlead lead) {
		this.leadRepository.save(lead);
	}

	@Override
	public Springlead getLeadById(long id) {
		Optional<Springlead> optional = leadRepository.findById(id);
		// Optional<Springlead> optional = leadRepository.findById(id);
		Springlead lead = null;
		if (optional.isPresent()) {
			lead = optional.get();
		} else {
			throw new RuntimeException(" Lead not found for client_id :: " + id);
		}
		return lead;
	}

	@Override
	public void deleteLeadById(long id) {
		this.leadRepository.deleteById(id);
	}

	@Override
	public Page<Springlead> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.leadRepository.findAll(pageable);
	}
}
