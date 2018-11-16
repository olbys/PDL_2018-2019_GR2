package connexionAPI;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sweble.wikitext.engine.EngineException;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngPage;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.parser.LinkTargetException;
import org.wikipedia.Mediawiki;
import com.bitplan.mediawiki.japi.MediawikiApi;
import com.google.inject.matcher.Matcher;


public class ExtractionToWiki {

	/**
	 * 
	 */
	

	public ExtractionToWiki() {
		
	}
	
	//Fonction pour essayeer de Parcer le wikiText avec Parse
	public static void getTabeWiki(String chaine) {//que fait cette fonction?
		String prefix ="|";	 
		String text="{|"+chaine+"|}"; 
		Pattern p = Pattern.compile("<^>]+>"); 
		//Pattern p = Pattern.compile("<^>]+>"); 
		java.util.regex.Matcher m = p.matcher(text); 
		String result =""; 

		while(m.find()) { 

			result = m.replaceAll("");
			
			System.out.println("\ntest:");
			int cpt = result.length();
			System.out.println(result);   //ici on récupère tout le wikicode de la page je crois
			System.out.println("\ntest2:");
		} 
	}
	//Recupération contenue wikitext
	public static void getContenuePage(String url) throws IOException {
			String balisedebut ="{|";
			String baliseFin   = "|}";
			String nvligne     = "\n";
	        URL wikipedia = new URL(url);
	        URLConnection yc = wikipedia.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        String inputLine;
	 
	        while ((inputLine = in.readLine()) != null) 
	        {
	        	getTabeWiki(inputLine); //appel  de la fonction getTablexiki 
	        }
	        in.close();
	}
	
	public static void main(String[] args) throws Exception {
<<<<<<< HEAD

		//brouillon();
		//	        HttpURLConnection conn = (HttpURLConnection) new URL(
		//	                "https://meta.wikimedia.org/wiki/Help:Table/fr").openConnection();
		//	        conn.connect();
		//	  
		//	        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		//	       
		//	        byte[] bytes = new byte[1024];
		//	        int tmp ;
		//	        while( (tmp = bis.read(bytes) ) != -1 ) {
		//	            String chaine = new String(bytes,0,tmp);
		//	            System.out.print(chaine);
		//	        }
		//	          
		//	        conn.disconnect();

		HttpURLConnection conn = (HttpURLConnection) new URL(
				"https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras").openConnection();
		conn.connect();

		BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

		byte[] bytes = new byte[1024];
		int tmp ;
		while( (tmp = bis.read(bytes) ) != -1 ) {

			String chaine = new String(bytes,0,tmp);
			getTabeWiki(chaine); //qu'est ce que ça fait cette fonction?
			//	            System.out.print(chaine);
		}

		System.out.print(bis);

		conn.disconnect();

		String target = "https://meta.wikimedia.org/wiki/Help:Table/fr";
		// Set-up a simple wiki configuration

		ExtractionToWiki wiki = new ExtractionToWiki();
		URL link = new URL(target);
		//			//wiki.convertWikiText(title, , 1000);
		//		    WikiConfig config = DefaultConfigEnWp.generate();
		//		    // Instantiate a compiler for wiki pages
		//		    WtEngineImpl engine = new WtEngineImpl(config);
		//		    // Retrieve a page
		//		    PageTitle pageTitle = PageTitle.make(config, title);
		//		    PageId pageId = new PageId(pageTitle, -1);
		// Compile the retrieved page
		// EngProcessedPage cp = engine.postprocess(pageId, wikiText, null);
		// ExtractionToWiki p = new ExtractionToWiki(config, maxLineLength);
		//return cp.getPage();
		
	}


		String Url ="https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras";
		
		getContenuePage(Url);
		
	}
}