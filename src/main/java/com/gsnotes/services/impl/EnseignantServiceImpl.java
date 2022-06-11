package com.gsnotes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsnotes.bo.Enseignant;
import com.gsnotes.dao.IEnseignantDao;
import com.gsnotes.services.IEnseignantService;

@Service
public class EnseignantServiceImpl implements IEnseignantService {

	@Autowired
	IEnseignantDao enseignantDao; 
	
	
	@Override
	public Enseignant getEnseignantById(Long id)
	
	{	
		return enseignantDao.getEnseignantByIdUtilisateur(id);
	}
}
