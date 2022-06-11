package com.gsnotes.web.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import com.gsnotes.utils.export.ExcelHandler;
import com.gsnotes.utils.export.UtilsFUNCTIONverif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.gsnotes.bo.*;
import com.gsnotes.bo.Module;
import com.gsnotes.services.IEnseignantService;
import com.gsnotes.web.models.UserAndAccountInfos;

@Controller
@RequestMapping("/prof")
public class EnseignantController {
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private IEnseignantService sEnseignantService;
	

	
	private UserAndAccountInfos getUserAccount() {
		// On vérifie si les infors de l'utilisateur sont déjà dans la session
		UserAndAccountInfos userInfo = (UserAndAccountInfos) httpSession.getAttribute("userInfo");

		if (userInfo == null) {

			// On obtient l'objet representant le compte connecté (Objet UserPrincipal
			// implémentant UserDetails)
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// On cast vers notre objet UserPrincipal
			Compte userAccount = ((UserPrincipal) principal).getUser();

			Utilisateur u = userAccount.getProprietaire();

			String roleName = userAccount.getRole().getNomRole();

			userInfo = new UserAndAccountInfos(u.getIdUtilisateur(), userAccount.getIdCompte(), userAccount.getLogin(),
					u.getNom(), u.getPrenom(), u.getEmail(), roleName);

			// On le stocke dans la session
			httpSession.setAttribute("userInfo", userInfo);
		}

		return userInfo;

	}
	
	
	
	
	
	
	@PostMapping("import")
	public String importExcel (Model model, @RequestParam("name") MultipartFile file ) {
		

		List<ArrayList<Object>> lst = null;
		List<Double> listDoubles = new ArrayList<Double>();
		double nMax = 20;
		double nMin= 0;



			String[] extensions = {"xlsx","xlsm","xlsb","xltx","xltm","xls","xlt","xls","xml","xlam","xla","xlw","xlr"};
			Boolean eN = false;
			String fN ="notes-gs3.xlsx";
			String extension = fN.substring(fN.lastIndexOf(".") + 1,fN.length());

			for(String e : extensions) {
				System.out.println(extension.equals(e));
				if(e.equals(extension)) {
					eN = true;
					//model.addAttribute("message", "le fichier excel a été importé avec succées ");
					break;
				}}
			if(eN==true)
			{
				model.addAttribute("message", "le fichier excel a été importé avec succées ");

			}

				else
				{
					model.addAttribute("message", "Vous devez vérifier l'extension de l'excel");
					model.addAttribute("s", false);
				}



















 		 try {
 		 lst = ExcelHandler.readFromExcel("notes-gs3.xlsx", 0);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
            model.addAttribute("s", e);
		}



 		 // 2    vérifier que les notes sont bien entre 0 et 20
 		 for( int i =3 ; i< lst.size(); i++)
 		 {
 			if (Double.compare((double) lst.get(i).get(4), nMin)==1 && Double.compare(nMax,(double)lst.get(i).get(4))==1)
 				
 			{
				 model.addAttribute("checkNOTE","Les notes sont bien vérifiées");
 			
 				 listDoubles.add((double) lst.get(i).get(4));}
 			 else {
 				 model.addAttribute("checkNOTE","vous devez s'assurer que les sont entre 0 et 20");
 				 break;
 				 
			
 		 }
		  }




		  //les attributs à vérifier
		//on va les extraire du fichier excel à partir den notre liste : "lst"
		String profN = ((String) lst.get(1).get(1)).replace("\n", " ").toLowerCase();
 		String moduleN =  ((String) lst.get(0).get(1));
		String y = (String) lst.get(0).get(5);
		String classN = (String) lst.get(1).get(5);

		//String session = (String) lst.get(0).get(3);

		UserAndAccountInfos userAccountInfos= getUserAccount();
		model.addAttribute("NOMprenom", userAccountInfos.getPrenom()+" "+userAccountInfos.getNom());

		// test nom du prof
		if((userAccountInfos.getPrenom().toLowerCase()+" "+userAccountInfos.getNom().toLowerCase()).equals(profN))
			model.addAttribute("checkNOMPROF","LE NOM ET LE PRENOM DU PROF SONT CORRECTS");
		
		else {
			model.addAttribute("checkNOMPROF","CE N'EST PAS LE PROF DESIGNE D'ETRE");
		}
		//useraccount.getuserid






        //verfifiez le nom de l'enseignat
		Enseignant cProf = sEnseignantService.getEnseignantById(Long.valueOf(userAccountInfos.getIdPersonne()));

        //  list des modules du prof et check du module 
        
      List<Module> listModules = cProf.getModules();
        System.out.println(listModules);

        Module currentModule = null;
        for ( Module module : listModules)
        {
        	
        	if ((module.getTitre().toLowerCase()).equals(moduleN.toLowerCase()) ) {
				model.addAttribute("checkNOMmODULE", "le nom du module est celui qu'enseigne ce professeur");

				currentModule = module;

				// check niveau
				if (currentModule.getNiveau().getAlias().toLowerCase().equals(classN.toLowerCase()))
					model.addAttribute("checkniveau", "le niveau est bie vérifié");


				// check annee
				String anneCurrent = currentModule.getNiveau().getInscriptions().get(0).getAnnee();
				if (anneCurrent.equals(y)) {
					model.addAttribute("checkannee", "l'annee est bien vérifié");
				}


				break;

			}}


        





       // check niveau
     /*   if(currentModule.getNiveau().getAlias().toLowerCase().equals(className.toLowerCase()))
			model.addAttribute("checkniveau","le niveau est bie vérifié");*/

		/*
		// check annee
       // String anneCurrent =  currentModule.getNiveau().getInscriptions().get(0).getAnnee() ;
       // if( anneCurrent.equals(year) )
        {
		//	model.addAttribute("checkannee","l'annee est bien vérifié");
		}
        /*
        // check semestre 
        
        
        
        
        
        
        // check session 
        
     */
        
        // vérifier le formule de moyen et les coeff , moyenne
        
        double coeff1 = currentModule.getElements().get(0).getCurrentCoefficient();
  
        double coeff2 = currentModule.getElements().get(1).getCurrentCoefficient();

        List<Double> note1 =  new ArrayList<>()  ;
        
        List<Double> note2 =  new ArrayList<>()  ;		
        
        List<Double> moyenne =  new ArrayList<>()  ;

        
        
        for(int i =3 ;  i< lst.size()-3; i++)
        		{
        				note1.add((Double) lst.get(i).get(4));
        		}
        
        
		
        for(int i =3 ;  i< lst.size()-3; i++)
        		{
        				note2.add((Double) lst.get(i).get(5));
        		}
        
		
        for(int i =3 ;  i< lst.size()-3; i++)
        		{
        				moyenne.add((Double) lst.get(i).get(5));
        		}
        
       
       for ( int i =0 ;  i< moyenne.size(); i++) 
       {
    	   if ( (note1.get(i)*coeff1 + note2.get(i)*coeff2) != moyenne.get(i) )
    	   {
			   model.addAttribute("checkmoyen","l'annee est bien vérifié");
    		   break;
    	   }
    	   
    	   else {
			
    		   System.out.println(" Test Moyenne est validé");
		
    	   }
    		   
       }
        


        
        		
                
        
        
        
        
        
        
        
        
        
        
		
	

	return"prof/import";

	}

}
