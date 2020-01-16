package connexionAPI;

import java.net.HttpURLConnection;
import java.net.URL;
import org.eclipse.mylyn.wikitext.mediawiki.MediaWikiLanguage;
import org.eclipse.mylyn.wikitext.parser.MarkupParser;
import org.json.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import Exception.PageNotFoundException;
import abstractClass.AbstractExtractor;
import utils.Converter;
import utils.Messages;

import  java.io. * ;


public class ExtractionToWiki extends AbstractExtractor{
	Converter converter;
	public ExtractionToWiki() {
		this.converter = new Converter(this);
	}
	
	public Converter getConverter() {
		return converter;
	}
	/**
	 *  Recupération contenue wikitext en format Json
	 * @param language
	 * @param pageTitle
	 * @return
	 * @throws IOException
	 * @throws PageNotFoundException
	 */
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

	private  String getTableFormatwikitext(String language, String pageTitle) throws IOException, PageNotFoundException {
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