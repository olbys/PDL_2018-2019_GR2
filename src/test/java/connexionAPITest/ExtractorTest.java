package connexionAPITest;

import static org.junit.Assert.*;

import org.junit.Test;

import connexionAPI.ExtractionToHTML;
import interfaceExtractor.Extractor;

public class ExtractorTest {

	//@Test
	public void testIsUrlValid() {
		Extractor extractor =  new ExtractionToHTML("http://www.google.com");
		assertFalse("url must be no valid" , extractor.isUrlValid());
		extractor =  new ExtractionToHTML("https://en.wikipedia.org/wiki/Loi_des_Douze_Tables");
		assertTrue(" url must be valid", extractor.isUrlValid());
	}

	//@Test
	public void testIsConnectionOn() {
		 Extractor extractor =  new ExtractionToHTML("http://www.google.com");
	    	 assertFalse("url must be no valid" , extractor.isConnectionOn());
			extractor =  new ExtractionToHTML("https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Interlingua");
			assertTrue(" url must be valid", extractor.isConnectionOn());
	}

}
