package com.gsnotes.web.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/*
import com.boudaa.tools.ExcelHandler;
import com.boudaa.tools.ExcelHandlerException;
import com.boudaa.tools.FileManagerHelper;*/
//import com.google.common.io.Files;
import com.gsnotes.bo.Compte;
import com.gsnotes.bo.Enseignant;
import com.gsnotes.bo.UserPrincipal;
import com.gsnotes.bo.Utilisateur;
import com.gsnotes.dao.IEnseignantDao;
import com.gsnotes.services.IEnseignantService;
import com.gsnotes.web.models.UserAndAccountInfos;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
public class LoginController {

	
	@Autowired
	private IEnseignantService sEnseignantService;
	
	@Autowired
	private HttpSession httpSession;

	/**
	 * Récupère les données de l'utilisateur connecté du contexte de securité et le
	 * stocke dans un objet personnalisé à enregistrer dans la session http
	 * 
	 * @return
	 */
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

	@GetMapping("/showMyLoginPage")
	public String showLoginForm() {

		return "loginForm";
	}

	@GetMapping("/access-denied")
	public String showAccessDenied() {

		return "access-denied";

	}

	@GetMapping("/student/showHome")
	public String showStudentHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);

		return "student/userHomePage";

	}

	@GetMapping("/prof/showHome")
	public String showProfHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "prof/userHomePage";

	}

	/*@PostMapping("/prof/import")
	public String importExcel (Model model, @RequestParam("name") MultipartFile file ) {
		
		//String nameFileString = file.getContentType().get
		
		List<ArrayList<Object>> lst = null;
		
		List<Double> listDoubles = new ArrayList<Double>();
		double noteMax = 20;
		
		double noteMin= 0;
		
		if("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(file.getContentType()))
		{
			
			// validate file type

            model.addAttribute("message", "Please select an excell file to upload.");
            model.addAttribute("status", false);
		
		}
	    
 		 try {
			   
			    
 		 lst = ExcelHandler.readFromExcel("notes.xlsx", 0);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
            model.addAttribute("status", e);
		}
		
 		 
 		 for( int i =3 ; i< lst.size(); i++)
 		 {
 			 
 			 
 			if (Double.compare((double) lst.get(i).get(4), noteMin)==1 && Double.compare(noteMax,(double)lst.get(i).get(4))==1)
 				
 			{ model.addAttribute("check","kolchi mrigl 0");
 			
 				listDoubles.add((double) lst.get(i).get(4));}
 			 else {
 				 model.addAttribute("check","mamrigl walo");
 				 break;
 				 
			
 		 }}
 		 
 		String moduleName = (String) lst.get(0).get(1);
		String profName = ((String) lst.get(1).get(1)).replace("\n", " ");
		String session = (String) lst.get(0).get(3);
		String className = (String) lst.get(1).get(5);
		String year = (String) lst.get(0).get(5);
		
		UserAndAccountInfos userAccountInfos= getUserAccount();
		
		model.addAttribute("numbr", userAccountInfos.getPrenom()+" "+userAccountInfos.getNom());

		// test nom du prof 
		
		
		if((userAccountInfos.getPrenom()+" "+userAccountInfos.getNom()).equals(profName))
			model.addAttribute("check1","nom et prenom merigl");
		
		else {
			model.addAttribute("check1","nom et prenom not checked ");
		}
		
		Enseignant currentProf = sEnseignantService.getEnseignantById(Long.valueOf(10));
        System.out.println(currentProf.getNom());

        System.out.println(currentProf.getModules());
		
		
		
		// test du module 
		
	//	Enseignant pEnseignant = userAccountInfos.getIdPersonne()
		
		
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        }*/ 
        // valider le format 
        
		/*
		 * else if (!name.contains(".csv")) { model.addAttribute("message",
		 * "Please Select a csv File to Upload"); model.addAttribute("status", false);
		 * 
		 * 
		 * }
		
        
        else
        {
         */	
        	//List<ArrayList<Object>> lst  = ExcelHandler.readFromExcel(fileName, 0);
        	
			/*
			 * java.util.List<String> list; try { list = new
			 * FileManagerHelper().readFileLines(name); System.out.println(list);
			 * 
			 * } catch (IOException e) { // TODO Auto-generated catch block
			 * model.addAttribute("message",
			 * "An error occurred while processing the CSV file.");
			 * model.addAttribute("status", false); }
			 */
         
        	
        	// parse CSV file to create a list of `User` objects
        	// Read csv and test notes <20 & >0
            /*try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            	
            	// create csv reader
				
				 * CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(4).build();;
				 * 
				 * // read all records at once List<String[]> records = csvReader.readAll();
				 
            	List<String> list = new ArrayList<String>();
        		String lLigne = null;
        		BufferedReader lIn ;
        		lIn = new BufferedReader(new InputStreamReader(file.getInputStream()));
        		do {
        			lLigne = lIn.readLine();
        			if (lLigne != null && !"".equals(lLigne.trim())) {
        				list.add(lLigne.trim());
        			}
        		} while (lLigne != null);
        		lIn.close();
            	*/
                // iterate through list of records
				
				/*  for (String record : list) { 
					  System.out.println("ID: " + record);
					  System.out.println("Name: " + record); 
					  System.out.println("Email: " + record); 
					  System.out.println("Country: " + record);
				  
				  
				  }
				  
				  // close readers csvReader.close(); reader.close();
				 
                
         }    	
            	// create csv bean reader
             //   CsvToBean<User> csvToBean = new CsvToBeanBuilder(reader)
                     //   .withType(User.class)
                     //   .withIgnoreLeadingWhiteSpace(true)
                       // .build();

                // convert `CsvToBean` object to list of users
                
                //List<User> users = csvToBean.parse();

                // TODO: save users in DB?

                // save users list on model
              //  model.addAttribute("users", users);
                //model.addAttribute("status", true);

				
				  catch(Exception ex) { model.addAttribute("message",
				  "An error occurred while processing the CSV file.");
		
				  model.addAttribute("status", false); }
				 
            
         

	return"prof/import";

	}   }*/

	@GetMapping("/cadreadmin/showHome")
	public String showCadreAdminHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "cadreadmin/userHomePage";

	}

	@GetMapping("/biblio/showHome")
	public String showBiblioHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "biblio/userHomePage";

	}

	@GetMapping("/admin/showAdminHome")
	public String showAdminHome(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "admin/adminHome";

	}

}
