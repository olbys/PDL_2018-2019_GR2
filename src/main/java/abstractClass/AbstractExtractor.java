package abstractClass;

import java.net.MalformedURLException;
import java.net.URL;

import interfaceExtractor.Extractor;

public abstract class AbstractExtractor implements Extractor {
 private String url;
 
	public boolean isConnectionOn() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWikipediaUrl(String url) {
		String s=url.substring(0,24);
		if(s=="https://fr.wikipedia.org/"||s=="https://fr.wikipedia.org/") {
			return true;
		}
		return false;
	}

	public boolean isUrlValid(String url) {
		
		try {
			URL s= new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
     
}
