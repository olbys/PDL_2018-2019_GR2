package connexionAPITest;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import Exception.PageNotFoundException;
import connexionAPI.ExtractionToWiki;
import createfileCSV.GestionnaireCSV;
import utils.Messages;

public class ExtractionToWikiTest {
	ExtractionToWiki extractor;
	static int NUMBER_OF_VALIDE_FILE_TO_CREATE;
	
	@BeforeEach
	public void setup() {
		extractor = new ExtractionToWiki();
	}  	
	
	@Test
	@DisplayName("Test validity of all CSV file extracted by WikiExtractor ")
	public void testAreCsvFilesValidFromHtml() {
		GestionnaireCSV gestionnaire=new GestionnaireCSV();
		File[] files = null;
		int counter = 0;
		File wikiDirectory = null;
		try {
			wikiDirectory = new File(Messages.WIKI_OUTPUT_DIR);
			assertTrue(wikiDirectory.isDirectory());
			files = wikiDirectory.listFiles();
			for(File f : files) {
				System.out.println("filename : "+f.getName()+" is valid : "+gestionnaire.isCsvFileValid(f, ','));
				assertTrue(gestionnaire.isCsvFileValid(f, ','));
				counter++;
			}
		} catch (Exception e) {
		}
		System.out.println("total CSV Valid files extracted : "+counter);
		
	}
	
}
