package connexionAPI;

import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.eclipse.mylyn.wikitext.mediawiki.MediaWikiLanguage;
import org.eclipse.mylyn.wikitext.parser.MarkupParser;
import org.json.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.google.inject.spi.Element;

import Exception.PageNotFoundException;
import abstractClass.AbstractExtractor;
import  info.bliki.wiki.model.WikiModel ;
import  info.bliki.wiki.model.Configuration ;
import  info.bliki.wiki.tags. * ;
import utils.ConcreteConverter;
import utils.Messages;

import  java.io. * ;


public class ExtractionToWiki extends AbstractExtractor{
	ConcreteConverter converter;
	public ExtractionToWiki() {
		this.converter = new ConcreteConverter(this);
	}
	public ConcreteConverter getConverter() {
		return converter;
	}
	// Recupération contenue wikitext en format Json
	public static String getContenuePage(String language, String pageTitle) throws IOException, PageNotFoundException {
		String apiUrl = "";
		if(language.equals("en"))
		 apiUrl = Messages.EN_WIKIPEDIA_API_BASE_URL+encodeValue(pageTitle);
		else if(language.equals("fr"))
			apiUrl = Messages.FR_WIKIPEDIA_API_BASE_URL+pageTitle;
		StringBuffer response = null;
		URL urlObject = new URL(apiUrl);
				
		HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode(); 
		
		if(responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		}else {
			if(language.equals("fr"))
				throw new PageNotFoundException(pageTitle);
			else if(language.equals("en"))
				throw new PageNotFoundException(pageTitle);
		}	
		return response.toString();
	}
	
	//cette fonction format le contenu en format tableau wikitext

	public  String getTableFormatwikitext(String language, String pageTitle) throws IOException, PageNotFoundException {
		String reponse = getContenuePage(language, pageTitle);
		String wikitext = null;
		try {
			if(reponse != null) {
				JSONObject jsonObject = new JSONObject(reponse);
				JSONObject query = jsonObject.getJSONObject("query");
				if(query.has("pages")) {
					JSONArray pages = query.getJSONArray("pages");
					if(!pages.isEmpty() && pages.getJSONObject(0).has("revisions")) {
						JSONArray revisions = pages.getJSONObject(0).getJSONArray("revisions");
						if(revisions.getJSONObject(0).has("content"))
							wikitext = revisions.getJSONObject(0).getString("content");
					}
				}
				
			}
			
			if(wikitext == null)
				throw new PageNotFoundException(pageTitle);
		}catch (PageNotFoundException e) {
			throw new PageNotFoundException(pageTitle);
		}	
		return wikitext;
	}
	
	//formater le code et enlever
	//plus besoin de cette fonction, elle est donnee par les profs (cf norme de nommage)
	public static FileWriter creationFichierCsv(Document doc, String titre) { 
		
		// peut �tre d�placer dans un autre package
		// (createFileCSV)
		// Cr�ation du fichier csv avec comme titre le premier h1 de la page wikip�dia
		//Elements titre = doc.select("h1");
		FileWriter fileWriter = null;
		try {
			
			fileWriter = new FileWriter("output\\wikitext\\" + titre + ".csv");
			
		} catch (IOException e) {
			System.out.println("erreur lors de la cr�ation du fichier .CSV");
			e.printStackTrace();
		}
		return fileWriter;
	}
	
	public static String getTitreUrl(String url) {

		String titreformater ="";
		Document doc=null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements titreh1 = doc.select("h1");
		String titre = titreh1.first().text();    
		for (int i = 0; i < titre.length(); i++) {
			if(titre.charAt(i)==' ') {
				titreformater += '_';
			}else {
				titreformater += titre.charAt(i);
			}
		}
		return titreformater ; 
	}
	//Fonction pour générer les documents de format HTML à partir du code wiki et le Parseur de bliki en utilisant WikiModel.toHtml
	public Document getdocumentFromWiki(String language, String url) throws IOException, PageNotFoundException {
		String wikitext = getTableFormatwikitext(language, url);
		String html = null;
		Document doc = null;
		if (wikitext != null) {
			try {
				MarkupParser markupParser = new MarkupParser();
				markupParser.setMarkupLanguage(new MediaWikiLanguage());
				html = markupParser.parseToHtml(wikitext);			
			} catch (Exception e) {
				e.printStackTrace();
			}	
			doc = Jsoup.parse(html);
		}
		return doc;
	}
}