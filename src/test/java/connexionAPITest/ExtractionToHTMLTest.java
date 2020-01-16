package connexionAPITest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import Exception.PageNotFoundException;
import InterfaceExtractor.Extractor;
import connexionAPI.ExtractionToHTML;
import createfileCSV.GestionnaireCSV;
import utils.Messages;


class ExtractionToHTMLTest {

	private ExtractionToHTML extractor;
	
	@BeforeEach
	public void setup() {
		extractor = new ExtractionToHTML();
	}
	
	@Test
	@DisplayName("Comparison_of_Android_e-book_reader_software")
	@Tag("robustness")
	public void testComparison_of_Exchange_ActiveSync_clients() throws IOException {
		Document doc = extractor.getDocumentFromHtml("en", "Comparison_of_Android_e-book_reader_software");
		Elements tables= extractor.getExtractedTables(doc, Messages.ENGLISH_WIKI_URL+"Comparison_of_Android_e-book_reader_software");
		assertEquals(6, tables.size());	 
	}
	

	@Test
	@DisplayName("test converts all files to csv")
	@Tag("robustness")
	public void testConvertAllFromHtmlToCSV()
	{
		try {
		assertTrue(new File(Messages.HTML_OUTPUT_DIR).isDirectory());
		assertDoesNotThrow(()-> extractor.getConverter().convertAllToCSVformat(Messages.HTML_OUTPUT_DIR));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Comparison_of_e-book_formats)")
	@Tag("robustness")
	public void testComparison_of_audio_player_software() throws IOException, PageNotFoundException {
		Document doc = extractor.getDocumentFromHtml("en", "Comparison_of_e-book_formats");
		Elements tables = extractor.getExtractedTables(doc, Messages.ENGLISH_WIKI_URL + "Comparison_of_e-book_formats");
		assertEquals(31, tables.size());
	}
	
	@Test
	@DisplayName("Comparison_of_SSH_clients")
	public void testComparison_of_SSH_clients() throws IOException, PageNotFoundException{
		Document doc =extractor.getDocumentFromHtml("en", "Comparison_of_SSH_clients");
		Elements tables = extractor.getExtractedTables(doc, Messages.ENGLISH_WIKI_URL + "Comparison_of_SSH_clients");
		assertEquals(3, tables.size());
	}
	
	@Test
	@DisplayName("Comparison_of_TLS_implementations") 
	public void testComparison_of_TLS_implementations() throws IOException, PageNotFoundException {
		Document doc = extractor.getDocumentFromHtml("en", "Comparison_of_TLS_implementations");
		Elements tables = extractor.getExtractedTables(doc, Messages.ENGLISH_WIKI_URL + "Comparison_of_TLS_implementations");
		assertEquals(18, tables.size());
	}
	
	@Test
	@DisplayName("Test Python_(langage)")
	public void testComparison_of_PythonLangage() throws IOException, PageNotFoundException {
		Document doc =extractor.getDocumentFromHtml("fr", "Python_(langage)");
		Elements tables = extractor.getExtractedTables(doc, Messages.ENGLISH_WIKI_URL + "Python_(langage)");
		assertEquals(0, tables.size());
	}
	
	@Test
	@AfterAll
	@DisplayName("Test validity of all CSV file extracted by HtmlExtractor ")
	public static void testAreCsvFilesValidFromHtml() {
		GestionnaireCSV gestionnaire=new GestionnaireCSV();
		File[] files = null;
		int counter = 0;
		File wikiDirectory = null;
		try {
			wikiDirectory = new File(Messages.HTML_OUTPUT_DIR);
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
	
	@Test
	@DisplayName("Test validity of all CSV file extracted by WikiExtractor ")
	public void testAreCsvFilesValidFromWiki() {
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
