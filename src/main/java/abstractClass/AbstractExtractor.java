package abstractClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interfaceExtractor.Extractor;

public abstract class AbstractExtractor implements Extractor {
	
	protected String url;
 

	public AbstractExtractor(String _url) {
		 this.url = _url; 
	 }
	
	public AbstractExtractor() {
		
	}
	
	

	public String getUrl() {
		return this.url;
	}
	
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	/**
	 * Test si l'url de l'extractor est valide 
	 */
	public boolean isUrlValid() {
		String pattern = "http(s)?://en.wikipedia.org/wiki/";
        Pattern regPat = Pattern.compile(pattern , Pattern.CASE_INSENSITIVE);
        Matcher matcher = regPat.matcher(this.url);
      return matcher.find();
		    
	}
	
	
	/**
	 * Test si la connection est etablir pour faire l'extraction 
	 */
	public boolean isConnectionOn() {
		return false;
	}

     
}
