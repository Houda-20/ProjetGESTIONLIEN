package com.gsnotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Enseignant;
import com.gsnotes.bo.Utilisateur;

public interface IEnseignantDao extends JpaRepository<Enseignant, Long>{
	

	public Enseignant getEnseignantByIdUtilisateur(Long id);

}
