package abstractClass;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

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
		
		try {
			
			if(!this.isUrlValid()) {
				return false;
			}
			
			 URL une_url = new URL(this.url);
			 HttpURLConnection test_connexion = (HttpURLConnection) une_url.openConnection();
			 return (test_connexion.getResponseCode() == HttpURLConnection.HTTP_OK || HttpURLConnection.HTTP_MOVED_PERM == test_connexion.getResponseCode()  );
		} catch(Exception e ) {
			
		};
		
		return false;
	}

     
}
