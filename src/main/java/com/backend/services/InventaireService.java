package com.backend.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.backend.entities.Inventaire;
import com.backend.entities.Produit;
import com.backend.entities.Stock;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.InventaireRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
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
	
	

	//Liste des inventaires
	public List<Inventaire> getInventaires() throws NotFoundException
	{
		
		List<Inventaire> inventaires = rep.findAll();
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
		
		Font fontTitre=new Font(FontFamily.TIMES_ROMAN,20f,Font.UNDERLINE,BaseColor.RED);
		Font fontHeader=new Font(FontFamily.HELVETICA,14f,Font.BOLD,BaseColor.BLACK);
		Font fontData=new Font(FontFamily.HELVETICA,12f,Font.NORMAL,BaseColor.BLACK);
		Font fontAlert=new Font(FontFamily.HELVETICA,12f,Font.BOLD,BaseColor.RED);
		Paragraph titre = new Paragraph("Inventaire du Stock de: "+stock.getEmplacement().getDesignation(),fontTitre);
		titre.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph space = new Paragraph(" ");
		Paragraph emplacement = new Paragraph("Emplacement du stock : "+stock.getEmplacement().getAdresse(),fontHeader);
		Paragraph tel = new Paragraph("Telephone : "+stock.getTelephone(),fontHeader);
		Paragraph fax = new Paragraph("Fax : "+stock.getFax(),fontHeader);
		Paragraph dateInventaire = new Paragraph("Date : "+ date,fontHeader);
		
		
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		Paragraph h1 = new Paragraph("Produit",fontHeader);
		Paragraph h2 = new Paragraph("Type",fontHeader);
		Paragraph h3 = new Paragraph("Categorie",fontHeader);
		Paragraph h4 = new Paragraph("Qte en stock",fontHeader);
		Paragraph h5 = new Paragraph("Qte totale",fontHeader);
		Paragraph h6 = new Paragraph("Etat",fontHeader);
		
		
		table.addCell(new PdfPCell(h1));
		table.addCell(new PdfPCell(h2));
		table.addCell(new PdfPCell(h3));
		table.addCell(new PdfPCell(h4));
		table.addCell(new PdfPCell(h5));
		table.addCell(new PdfPCell(h6));
		
		for (Produit produit : produits) {
			h1 = new Paragraph(produit.getNom(),fontData);
			h2 = new Paragraph(produit.getType(),fontData);
			h3 = new Paragraph(produit.getCategorie().getDesignation(),fontData);
			h4 = new Paragraph(String.valueOf(produit.getQuantiteEnStock()),fontData);
			h5 = new Paragraph(String.valueOf(produit.getQuantiteTotale()),fontData);
			
			if(produit.getQuantiteEnStock() < produit.getQuantiteMin())
				h6 = new Paragraph("En rupture !",fontAlert);
			else
				h6 = new Paragraph("Suffisant",fontData);
			
			table.addCell(new PdfPCell(h1));
			table.addCell(new PdfPCell(h2));
			table.addCell(new PdfPCell(h3));
			table.addCell(new PdfPCell(h4));
			table.addCell(new PdfPCell(h5));
			table.addCell(new PdfPCell(h6));
				
		}
		
		
		Document document= new Document();
		PdfWriter.getInstance(document, new FileOutputStream
		("C:\\Users\\DELL\\eclipse-workspace\\stock\\src\\main\\resources\\inventaire\\stock_"+stock.getId()+"_"+date.toString().replace(':', '-')+".pdf"));
		
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
		
	}


}
