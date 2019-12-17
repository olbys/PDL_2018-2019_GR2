package abstractClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
import connexionAPI.ExtractionToHTML;
import connexionAPI.ExtractionToWiki;
import createfileCSV.GestionnaireCSV;
import interfaceExtractor.Extractor;
import utils.Messages;

public abstract class Converter {
	
	protected Extractor extractor;
	protected GestionnaireCSV filehandler;
	protected char separator = ',';

	public StringBuilder processCurrentTDText(StringBuilder tdText) {	
		for (int k = 0; k < tdText.length(); k++) {	
			if (tdText.charAt(k) == separator) {		
				if (k > 0) {					
					if (!isNumeric(tdText.charAt(k - 1))) {						
						tdText.setCharAt(k, ' ');
					} else {						
						if ((k + 1) < tdText.length()) {							
							if (isNumeric(tdText.charAt(k + 1))) {						
								tdText.setCharAt(k, '.');
							} else
								tdText.setCharAt(k, ' ');
						}
					}
				}
				if (k == tdText.length() - 1)
					tdText.setCharAt(k, ' ');
			}
		}
		return tdText;
	}

	
	/**
	 * Checks whether or not a character is a number Used to process td text 
	 * @param character the character to check
	 * @return true if that charcacter is a number
	 */
	private boolean isNumeric(char character) {
		String numericRegex = "^[0-9]*$";
		String charString = String.valueOf(character);
		return Pattern.matches(numericRegex, charString);
	}

	
	public void convertAllToCSVformat(String outputDir) {
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			file = new File(Messages.URLS_FILE_PATH);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String url = null;
			Document doc=null;
			while ((url = br.readLine()) != null) {
				if (!doesUrlExist(Messages.ENGLISH_WIKI_URL+ url))
					continue;
				if(this.extractor instanceof ExtractionToHTML)
					doc = ((ExtractionToHTML) extractor).getDocumentFromHtml("en", url);
				if(this.extractor instanceof ExtractionToWiki)
					doc = ((ExtractionToWiki) extractor).getdocumentFromWiki("en", url);
				convertToCsv(doc, Messages.ENGLISH_WIKI_URL, url, outputDir);
			}
		} catch (Exception e) {

		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 
	 * Checks whether or not a url exists May throw HttpStatusException 
	 * @param url the url to check
	 * @return true if the url exists
	 * @throws IOException if is not possible to connect to that url
	 * 
	 */
	private boolean doesUrlExist(String url) throws IOException {
		try {
			Jsoup.connect(url).get();
			return true;
		} catch (HttpStatusException e) {
			System.out.println("[" + url + "] does not exist!");
			return false;
		}
	}
	
	/**
	 *
	 * @param doc
	 * @param baseUrl
	 * @param pageTitle
	 * @param filePath
	 * @throws HttpStatusException
	 *  
	 */
		public void convertToCsv(Document doc, String baseUrl, String pageTitle, String filePath) throws HttpStatusException {
			List<String> data = new ArrayList<String>();
			String line = "";
			String filename;
			StringBuilder currentTdText;
			int filenameCounter = 1;
			try {
				Elements tableElements = this.extractor.extractTables(doc, baseUrl + pageTitle);
				if (tableElements != null) {
					for (Element currentTable : tableElements) {
						Elements currentTableTrs = currentTable.select("tr");
						for (int i = 0; i < currentTableTrs.size(); i++) {
							Element currentTr = currentTableTrs.get(i);
							Elements currentRowTds = currentTr.select("td");
							Element currentTd;
							for (int j = 0; j < currentRowTds.size(); j++) {
								currentTd = currentRowTds.get(j);
								currentTdText = new StringBuilder(currentTd.text());
								currentTdText = processCurrentTDText(currentTdText);
								if (j == currentRowTds.size() - 1)
									line += currentTdText.toString();
								else
									line += currentTdText.toString() + separator;
							}
							if (line != "") {
								line = replaceEndOfLine(line);
								data.add(line);
								line = "";
							}
						}
						filename = this.filehandler.extractFilenameFromUrl(pageTitle, filenameCounter);
						this.filehandler.write(filePath, filename, data);
						System.out.println(filename + "has created");
						filenameCounter++;
						data.clear();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	/**
	 * replace the end of line caracter in the line by white space
	 * @param line
	 * @return String
	 */
	private String replaceEndOfLine(String line) {
		StringBuilder sb = new StringBuilder(line);
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '\n') 
				sb.setCharAt(i, ' ');
		}	
		return sb.toString();
	}
	
}
