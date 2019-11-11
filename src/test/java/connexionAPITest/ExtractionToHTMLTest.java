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
        	
	@Test
	public void testCreateCsvFileFromHtml1() {
		eth = new ExtractionToHTML("https://fr.wikipedia.org/wiki/Loi_des_Douze_Tables");
		Document doc = eth.extractDataTablesIntoHtmlFormat();
		int nbFile = eth.createCsvFiles(doc);
		File file = new File("output\\html\\Loi des Douze Tables0.csv");
		assertTrue("fichier existe", file.exists());
		assertEquals(nbFile, 1);
	}
	
	@Test
	public void testCreateCsvFileFromHtml2() {
		eth = new ExtractionToHTML("https://en.wikipedia.org/wiki/Comparison_of_Norwegian_Bokm%C3%A5l_and_Standard_Danish");
		Document doc = eth.extractDataTablesIntoHtmlFormat();
		int n = eth.createCsvFiles(doc);
		File file=null;
		for(int i = 1;i<6;i++) {
			if(i == 1) {
				file = new File("output\\html\\Comparison of Norwegian Bokmål and Standard Danish.csv");
			}else {
				file = new File("output\\html\\Comparison of Norwegian Bokmål and Standard Danish"+i+".csv");	
			}
			assertTrue("fichier existe", file.exists());
			assertEquals(n, 9);
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
	@Test
	public void testNumberOfFilesCreated() {
		
		eth = new ExtractionToHTML("https://fr.wikipedia.org/wiki/Loi_des_Douze_Tables");
		Document doc = eth.extractDataTablesIntoHtmlFormat();
		int n = eth.createCsvFiles(doc);
		assertEquals(n, 1);
	}

}
