package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import connexionAPI.ExtractionToHTML;

public class ClasseDeTests {

	@SuppressWarnings("static-access")
	@Test
	public void testExtractionToHTML() throws IOException {
	//	fail("Not yet implemented");

		ExtractionToHTML eh = new ExtractionToHTML();
		try{
			eh.getContentHtml("https://fr.wikipedia.org/wiki/Statistiques_et_records_du_Paris_Saint-Germain");
		} catch (IOException e) {
			e.printStackTrace();}
			
		
	}

	@Test
	public void testExtractionToWiki() {
		fail("Not yet implemented");
	}
	
	
}
