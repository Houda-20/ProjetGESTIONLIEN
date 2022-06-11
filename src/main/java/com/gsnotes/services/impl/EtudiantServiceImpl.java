package com.gsnotes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.dao.IEtudiantDao;
import com.gsnotes.services.IEtudiantService;

public class EtudiantServiceImpl implements IEtudiantService {

	@Autowired	
	private IEtudiantDao etudiantDao;
	
	@Override
	public Etudiant getEtudiantById(Long id)
	{
		return etudiantDao.getEtudiantByIdUtilisateur(id);
	}
	



}
