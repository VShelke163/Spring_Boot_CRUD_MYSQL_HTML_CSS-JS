package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.javaguides.springboot.model.Springlead;

@Repository
public interface LeadRepository extends JpaRepository<Springlead, Long> {

}
