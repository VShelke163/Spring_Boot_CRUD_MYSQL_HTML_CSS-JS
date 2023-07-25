package net.javaguides.springboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.javaguides.springboot.model.Springlead;
import net.javaguides.springboot.service.LeadService;

@Controller
public class LeadController {

	@Autowired
	private LeadService leadService;

	// display list of lead
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "name", "asc", model);
	}

	@GetMapping("/showNewLeadForm")
	public String showNewLeadForm(Model model) {
		// create model attribute to bind form data
		Springlead lead = new Springlead();
		model.addAttribute("lead", lead);
		return "new_lead";
	}

	@PostMapping("/saveLead")
	public String saveEmployee(@ModelAttribute("lead") Springlead lead) {
		// save lead to database
		leadService.saveLead(lead);
		return "redirect:/";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		// get lead from the service
		Springlead lead = leadService.getLeadById(id);

		// set lead as a model attribute to pre-populate the form
		model.addAttribute("lead", lead);
		return "update_lead";
	}

	@GetMapping("/deleteLead/{id}")
	public String deleteLead(@PathVariable(value = "id") long id) {

		// call delete lead method
		this.leadService.deleteLeadById(id);
		return "redirect:/";
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;

		Page<Springlead> page = leadService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Springlead> listLeads = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listLeads", listLeads);
		return "index";
	}
}
