package com.backend.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
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
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.InventaireRepository;


@Service
public class InventaireService {
	
	@Autowired
	InventaireRepository rep;
	
	@Value("classpath:/inventaire/inventaire.txt")
	private Resource resource;
	
	public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(),Charset.forName("UTF-8"))) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
	

	//Liste des inventaires
	public List<Inventaire> getInventaires() throws NotFoundException
	{
		
		List<Inventaire> inventaires = rep.findAll();
		if(inventaires.isEmpty()) throw new NotFoundException("Aucun inventaire trouvé");		
		
		return inventaires;
	}
	
	
	//ajouter une inventaire
	public void addInventaire(Inventaire inventaire) throws IOException
	{
		
		String text=asString(resource);
		
		String[] lines=text.split("\r\n");
		

		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
	 
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		 PDRectangle mediabox = page.getMediaBox();
		 float margin = 72;
		 float startX = mediabox.getLowerLeftX() + margin;
		 float startY = mediabox.getUpperRightY() - margin;
		 float fontSize = 16;
		 float leading = 1.5f * fontSize;
		 for (String line: lines) {System.out.println(line);}

		
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 24);
		contentStream.beginText();
		contentStream.newLineAtOffset(startX+100, startY);
		contentStream.showText("Contrat de creation de compte");
	    contentStream.newLineAtOffset(0, -leading);
		contentStream.endText(); 
		contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
		contentStream.beginText();
	    contentStream.newLineAtOffset(startX, startY-40);
	    for (String line: lines)
	    {
	        contentStream.showText(line);
	        contentStream.newLineAtOffset(0, -leading);
	    }
	    contentStream.endText(); 
		contentStream.close();
		 
		document.save("contratCompte_"+".pdf");
		document.close();
		
		rep.save(inventaire);
	}


}
