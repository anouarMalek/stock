package com.backend.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.backend.entities.Fournisseur;
import com.backend.entities.Inventaire;
import com.backend.entities.Produit;
import com.backend.entities.Stock;
import com.backend.entities.Utilisateur;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.InventaireRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class InventaireService {
	
	@Autowired
	InventaireRepository rep;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	FournisseurService fournisseurService;
	
	@Autowired
	UtilisateurService utilisateurService;
	
	Logger logger = LoggerFactory.getLogger(InventaireService.class.getName());
	
	

	//Liste des inventaires
	public List<Inventaire> getInventaires(Long id) throws NotFoundException
	{
		List<Inventaire> inventaires = new ArrayList<Inventaire>();
		if(id==null)
			inventaires = rep.findAll();
		else
			inventaires.add(rep.findById(id).orElseThrow(() -> new NotFoundException("Aucun inventaire avec l'id "+id+" trouvé.")));
		
		if(inventaires.isEmpty()) throw new NotFoundException("Aucun inventaire trouvé");		
		
		return inventaires;
	}
	
	
	//ajouter une inventaire
	public void addInventaire(Inventaire inventaire) throws IOException, DocumentException
	{

		
		inventaire.setDate(LocalDateTime.now());
		
		Stock stock = stockService.getStocks(inventaire.getStock().getId()).get(0);
		LocalDateTime date = inventaire.getDate();
		List<Produit> produits = stockService.getProduits(stock.getId());
		Fournisseur fournisseur = new Fournisseur();
		
		Font fontTitre=new Font(FontFamily.TIMES_ROMAN,20f,Font.UNDERLINE,BaseColor.RED);
		Font fontHeader=new Font(FontFamily.HELVETICA,12f,Font.BOLD,BaseColor.BLACK);
		Font fontData=new Font(FontFamily.HELVETICA,12f,Font.NORMAL,BaseColor.BLACK);
		Font fontAlert=new Font(FontFamily.HELVETICA,12f,Font.BOLD,BaseColor.RED);
		Paragraph titre = new Paragraph("Inventaire du Stock de: "+stock.getEmplacement().getDesignation(),fontTitre);
		titre.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph space = new Paragraph(" ");
		Paragraph emplacement = new Paragraph("Emplacement du stock : "+stock.getEmplacement().getAdresse(),fontHeader);
		Paragraph tel = new Paragraph("Telephone : "+stock.getTelephone(),fontHeader);
		Paragraph fax = new Paragraph("Fax : "+stock.getFax(),fontHeader);
		Paragraph dateInventaire = new Paragraph("Date : "+ date,fontHeader);
		
		
		
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		Paragraph h1 = new Paragraph("Produit",fontHeader);
		Paragraph h2 = new Paragraph("Fournisseur",fontHeader);
		Paragraph h3 = new Paragraph("Categorie",fontHeader);
		Paragraph h4 = new Paragraph("Prix Achat",fontHeader);
		Paragraph h5 = new Paragraph("Qte en stock",fontHeader);
		Paragraph h6 = new Paragraph("Qte totale",fontHeader);
		Paragraph h7 = new Paragraph("Etat",fontHeader);
		
		
		table.addCell(new PdfPCell(h1));
		table.addCell(new PdfPCell(h2));
		table.addCell(new PdfPCell(h3));
		table.addCell(new PdfPCell(h4));
		table.addCell(new PdfPCell(h5));
		table.addCell(new PdfPCell(h6));
		table.addCell(new PdfPCell(h7));
		
		for (Produit produit : produits) {
			h1 = new Paragraph(produit.getNom(),fontData);
			fournisseur = fournisseurService.getFournisseurs(produit.getFournisseur().getId()).get(0);
			
			h2 = new Paragraph(fournisseur.getNom(),fontData);
			h3 = new Paragraph(produit.getCategorie().getDesignation(),fontData);
			h4 = new Paragraph(String.valueOf(produit.getPrixAchat()),fontData);
			h5 = new Paragraph(String.valueOf(produit.getQuantiteEnStock()),fontData);
			h6 = new Paragraph(String.valueOf(produit.getQuantiteTotale()),fontData);
			
			if(produit.getQuantiteEnStock() < produit.getQuantiteMin())
				h7 = new Paragraph("En rupture !",fontAlert);
			else
				h7 = new Paragraph("Suffisant",fontData);
			
			table.addCell(new PdfPCell(h1));
			table.addCell(new PdfPCell(h2));
			table.addCell(new PdfPCell(h3));
			table.addCell(new PdfPCell(h4));
			table.addCell(new PdfPCell(h5));
			table.addCell(new PdfPCell(h6));
			table.addCell(new PdfPCell(h7));
				
		}
		
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		
		Document document= new Document();
		PdfWriter.getInstance(document, new FileOutputStream
		(path+"\\src\\main\\resources\\inventaire\\stock_"+stock.getId()+"_"+date.withNano(0).toString().replace(':', '-')+".pdf"));
		
		document.open();
		
		document.add(titre);
		document.add(space);
		document.add(emplacement);
		document.add(tel);
		document.add(fax);
		document.add(dateInventaire);
		document.add(space);
		document.add(table);
		document.close();
		
		
		rep.save(inventaire);
		
		Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'utilisateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a effectué un inventaire du stock "+stock.getEmplacement().getDesignation()+" à la date: "+date);
		
	}
	
	
	public ResponseEntity<InputStreamResource> getInventairePDF(Long id) throws IOException
	{
		Inventaire inventaire = getInventaires(id).get(0);
		Stock stock = stockService.getStocks(inventaire.getStock().getId()).get(0);
		
		String fileName = "stock_"+stock.getId()+"_"+inventaire.getDate().toString().replace(':', '-')+".pdf";
		
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		PathResource pdfFile = new PathResource(path+"\\src\\main\\resources\\inventaire\\"+fileName);
		 
		
		
		  ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
		    new InputStreamResource(pdfFile.getInputStream()), HttpStatus.OK);
		  
		  Utilisateur user = utilisateurService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		logger.debug("L'utilisateur "+user.getNom()+" "+user.getPrenom()+" ayant le Username "+user.getUsername()+" a téléchargé le fichier "+fileName+" à la date: "+LocalDateTime.now());
			
		  
		  return response;

	}


}
