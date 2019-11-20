package connexionAPITest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.junit.Test;

import connexionAPI.ExtractionToHTML;

public class ExtractionToHTMLTest {
	ExtractionToHTML eth;
	static int NUMBER_OF_VALIDE_FILE_TO_CREATE;
        	
	//@Test
	public void testCreateCsvFileFromHtml1() {
		NUMBER_OF_VALIDE_FILE_TO_CREATE=0;
		eth = new ExtractionToHTML("https://fr.wikipedia.org/wiki/Loi_des_Douze_Tables");
		Document doc = eth.extractDataTablesIntoHtmlFormat();
		int numberOfCreatedFile = eth.createCsvFiles(doc);
		assertEquals(numberOfCreatedFile, NUMBER_OF_VALIDE_FILE_TO_CREATE);
	}
	
	//@Test
	public void testCreateCsvFileFromHtml2() {
		eth = new ExtractionToHTML("https://en.wikipedia.org/wiki/Comparison_of_Norwegian_Bokm%C3%A5l_and_Standard_Danish");
		NUMBER_OF_VALIDE_FILE_TO_CREATE = 6;
		Document doc = eth.extractDataTablesIntoHtmlFormat();
		int numberOfCreatedFile = eth.createCsvFiles(doc);
		File file=null;
		for(int i = 0;i<=numberOfCreatedFile;i++) {
			if(i < 1) {
				file = new File("output\\html\\Comparison of Norwegian Bokmål and Standard Danish.csv");
			}else {
				file = new File("output\\html\\Comparison of Norwegian Bokmål and Standard Danish"+i+".csv");	
			}
			assertTrue("fichier existe", file.exists());
			assertEquals(numberOfCreatedFile, NUMBER_OF_VALIDE_FILE_TO_CREATE);
		}
	}

	@Test
	public void testGetHtmlJsoup() {
		eth = new ExtractionToHTML("https://fr.wikipedia.org/wiki/Loi_des_Douze_Tables");
		Document doc = this.eth.getHtmlJsoup("https://fr.wikipedia.org/wiki/Loi_des_Douze_Tables");
		assertTrue("getHtmljsoup doit retourner une instance de document", doc instanceof Document);
	}

	@Test
	public void testGetHtmlJsoupParamShouldNotBeNull() {
		eth = new ExtractionToHTML("https://fr.wikipedia.org/wiki/Loi_des_Douze_Tables");
		try {
			this.eth.getHtmlJsoup(null);
			fail("L'url n'est pas valide");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	//@Test
	public void testNumberOfFilesCreated() {
		NUMBER_OF_VALIDE_FILE_TO_CREATE=0;
		eth = new ExtractionToHTML("https://fr.wikipedia.org/wiki/Loi_des_Douze_Tables");
		Document doc = eth.extractDataTablesIntoHtmlFormat();
		int numberOfCreatedFile = eth.createCsvFiles(doc);
		assertEquals(numberOfCreatedFile, NUMBER_OF_VALIDE_FILE_TO_CREATE);
	}
	
	//@Test
	public void testNumberOfFilesCreated2() {
		eth = new ExtractionToHTML("https://en.wikipedia.org/wiki/Comparison_of_Chernobyl_and_other_radioactivity_releases");
		NUMBER_OF_VALIDE_FILE_TO_CREATE=3;
		Document doc = eth.extractDataTablesIntoHtmlFormat();
		int numberOfCreatedFile = eth.createCsvFiles(doc);
		assertEquals(numberOfCreatedFile, NUMBER_OF_VALIDE_FILE_TO_CREATE);
	}
	
}
