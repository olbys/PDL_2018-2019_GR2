package abstractClass;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import InterfaceExtractor.Extractor;
import createfileCSV.GestionnaireCSV;
import utils.Messages;


public abstract class AbstractExtractor implements Extractor {
	
	protected List<String> urls;
	
	/**
	 * Test si les urls de l'extractor sont valide 
	 */
	public boolean isUrlValid(String url) {
		String pattern = "http(s)?://en.wikipedia.org/wiki/";
        Pattern regPat = Pattern.compile(pattern , Pattern.CASE_INSENSITIVE);
        	Matcher matcher = regPat.matcher(url);
        	return matcher.find();
	}
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<String> getUrlsFromFile() throws IOException{
		File file = new File(Messages.URLS_FILE_PATH);
		BufferedReader br = new BufferedReader(new FileReader(file));
	    List<String> urls=new ArrayList<>();
	    String url;
	    while ((url = br.readLine()) != null) {
	    	urls.add(url);
	    }
	    return urls;
	}
	/**
	 * Test si la connection est etablir pour faire l'extraction 
	 */
	public boolean isConnectionOn(String url) {
		
		try {
			
			if(!this.isUrlValid(url)) {
				return false;
			}
			
			 URL une_url = new URL(Messages.ENGLISH_WIKI_URL+url);
			 HttpURLConnection test_connexion = (HttpURLConnection) une_url.openConnection();
			 return (test_connexion.getResponseCode() == HttpURLConnection.HTTP_OK || HttpURLConnection.HTTP_MOVED_PERM == test_connexion.getResponseCode()  );
		} catch(Exception e ) {
			
		};
		
		return false;
	}
	
	@Override
	public Elements ignoredClasses(Elements tableElements) {
		String[] currentTableClasses;
		for (Element currentTable : tableElements) {
			currentTableClasses = currentTable.className().split(" ");
			for (String cs : currentTableClasses) {
				if (cs.startsWith("infobox")) {
					currentTable.addClass(Messages.CLASS_TO_REMOVE);
				}
			}
			if (currentTable.hasClass("navbox")
					&& currentTable.hasClass("autocollapse")
					&& currentTable.hasClass("collapsible")
					&& currentTable.hasClass("noprint")) {				
				currentTable.addClass(Messages.CLASS_TO_REMOVE);
			}	
		}	
		return tableElements.not("."+Messages.CLASS_TO_REMOVE);
	}
	
	@Override
	public Elements extractTables(Document doc, String url) throws IOException, HttpStatusException {
		Elements tableElements = null;
		if (!isUrlValid(url)) {
			System.out.println(url + " is not valid");
		} else {
			tableElements = doc.select("table"); 
			int initialSize = tableElements.size();
			tableElements = convertThsToTds(tableElements);
			tableElements = formatTables(tableElements);
			tableElements = fillEmptyTds(tableElements);
			tableElements = ignoredElements("div", tableElements);
			tableElements = ignoredElements("code", tableElements);
			tableElements = ignoredElements("ul", tableElements);
			tableElements = ignoredClasses(tableElements);
			tableElements = ignoredElements("p", tableElements);
			
		}
		return tableElements;
	}
	
	/**
	 * Converts ths to tds
	 * 
	 * @param tableElements the tables whose ths are transformed to tds
	 * @return the processed tables
	 */
	private Elements convertThsToTds(Elements tableElements) {
		for (Element currentTable : tableElements) {
			Elements currentTableRowElements = currentTable.select("tr");
			for (int i = 0; i < currentTableRowElements.size(); i++) {
				Element currentRow = currentTableRowElements.get(i);
				for (Element ex : currentRow.children()) {
					if (ex.is("th"))
						ex.tagName("td");
				}
			}
		}
		return tableElements;
	}
	
	private Elements fillEmptyTds(Elements tableElements) {
		for (Element currentTable : tableElements) {
			Elements currentTableRowElements = currentTable.select("tr");
			for (Element currentRow : currentTableRowElements) {
				for (Element currentTd : currentRow.select("td")) {
					if (currentTd.text().equals("") || currentTd.text().length() == 0)
						currentTd.text("[null]");
					if(currentTd.text().equalsIgnoreCase("Ubuntu/Kubuntu/Xubuntu/Lubuntu")) {
						//System.out.println(" tr with pnm = "+currentRow);	
					}
				}
			}
		}
		return tableElements;
	}
	@Override
	public Elements ignoredElements(String tag, Elements tableElements) {
		
		for (Element currentTable : tableElements) {
			Elements currentTableRowElements = currentTable.select("tr"); 
			Elements currentTdTags;
			for (int i = 0; i < currentTableRowElements.size(); i++) {
				Element currentRow = currentTableRowElements.get(i);
				Elements currentRowItems = currentRow.select("td");
				Elements TdInnerTables =  currentRowItems.select("table");
				if(TdInnerTables.size() > 0) {
					TdInnerTables.addClass(Messages.CLASS_TO_REMOVE);
					currentRow.remove();
				}
				
				for (int j = 0; j < currentRowItems.size(); j++) {
					currentTdTags = currentRowItems.get(j).select(tag);
					if (currentRowItems.get(j).hasAttr(Messages.ROW_SPAN) 
							|| currentRowItems.get(j).hasAttr(Messages.COL_SPAN)
							|| currentRowItems.get(j).hasClass(Messages.MBOX_IMAGE_CLASS)) {
						currentTable.addClass(Messages.CLASS_TO_REMOVE); 
					}
					
					if(currentRowItems.get(j).hasClass("extra_td_to_remove"))
						currentRowItems.get(j).remove();
					
					if (!currentTdTags.isEmpty()) {
						if (tag.equals("p") || tag.equals("br")) {
							//currentRowItems.get(j).remove();
						}
						if(tag.equals("div") || tag.equals("code"))
							currentTable.addClass(Messages.CLASS_TO_REMOVE);
					}
				}
				
			}
		}
		return tableElements.not("."+Messages.CLASS_TO_REMOVE);
	}
	
	@Override
	public Elements ignoreTablesWithLessRows(Elements tableElements, int numberOfRows) {
		for (Element currentTable : tableElements) {
			Elements currentTableRowElements = currentTable.select("tr");
			if (currentTableRowElements.size() < numberOfRows) {
				currentTable.addClass(Messages.CLASS_TO_REMOVE);
			}

		}
		return tableElements.not("."+Messages.CLASS_TO_REMOVE);
	}
	private void addClassToRemoveExtraTds(int firstRowTdsCount, Element tr) {
		int trTdsCount = tr.select("td").size();
		for(int i=firstRowTdsCount; i<trTdsCount; i++) {
			tr.select("td").get(i).addClass("extra_td_to_remove");
		}
	}
	
	private void addTdsTofitFirstRow(Element tr, int firstRowTdsCount) {
		int trTdsCount = tr.select("td").size();
		int diffCount = firstRowTdsCount - trTdsCount;
		for(int i=0; i<diffCount; i++)
			tr.appendChild(new Element("td").text("[added]"));
	}
	
	
	private Elements formatTables(Elements tableElements) {
		Element firstRow;
		int firstRowTdsCount;
		for (Element currentTable : tableElements) {
			firstRow = currentTable.select("tr").get(0);
			firstRowTdsCount = firstRow.select("td").size();
			Elements currentTableRowElements = currentTable.select("tr");
			for (int i=1; i<currentTableRowElements.size(); i++) {
				if(currentTableRowElements.get(i).select("td").size() < firstRowTdsCount)
					addTdsTofitFirstRow(currentTableRowElements.get(i), firstRowTdsCount);
				else if(currentTableRowElements.get(i).select("td").size() > firstRowTdsCount)
					addClassToRemoveExtraTds(firstRowTdsCount, currentTableRowElements.get(i));
			}
		}
		return tableElements;
	}
	
	/** Encodes a value. i.e for utf-8 url enconding especially parameters
	 * @param value the value to encode
	 * @return the encoded value
	 * @throws UnsupportedEncodingException if the encoding method is not supported
	 */
	public static String encodeValue(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
	}
	
}
