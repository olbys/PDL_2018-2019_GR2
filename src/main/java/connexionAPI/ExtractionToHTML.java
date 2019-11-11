
package connexionAPI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import abstractClass.AbstractExtractor;
import interfaceExtractor.Extractor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.text.html.HTMLEditorKit.InsertHTMLTextAction;

public class ExtractionToHTML extends AbstractExtractor{
	private String url;

	public ExtractionToHTML(String url) {
		this.url = url;
	}
	public ExtractionToHTML() {
	}

	public static void main(String[] args) {	
		
		ExtractionToHTML html = new ExtractionToHTML("https://en.wikipedia.org/wiki/Comparison_of_Norwegian_Bokm%C3%A5l_and_Standard_Danish");
		html.extractDataTablesIntoHtmlFormat();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Cette fonction cr�e un fichier un CSV contenant les donn�es des tableaux
	 * d'une page wikip�dia
	 * 
	 * @param url
	 *            l'adresse de la page que nous voulons exploiter
	 * @throws IOException
	 */
	public Document extractDataTablesIntoHtmlFormat() {
		
		System.out.println("Debut de l'extraction :");
		// r�cup�ration des donn�es de la page wikip�dia dans un Document
		Document doc=null;
		doc = getHtmlJsoup(this.url);
		System.out.println("Extraction en cours");
		//Création du fichier csv avec comme titre le premier h1 de la page wikip�dia
		if(doc!=null) {
			System.out.println("Extraction terminée");
		return doc;
		}
		System.out.println("le document est  null");
		return null;
	}

	public void urltrue(String url) throws IOException {
		if ( !url.contains("https://en.wikipedia.org") || !url.contains("https://fr.wikipedia.org") ){
			throw new IOException();
		}
	}

	public Document getHtmlJsoup(String url) {
		Document doc = null; //
		try {
			//urltrue(url);
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("erreur lors de la r�cup�ration du code html");
			e.printStackTrace();
		}
		return doc;
	}
	//plus besoin de cette fonction, elle est donnee par les profs (cf norme de nommage)
	public int createCsvFiles(Document doc) { // peut �tre d�placer dans un autre package
		Elements titre = doc.select("h1");
		FileWriter fileWriter = null;
		int i=0;
		try {
			for (Element table : doc.select("table")) {
				String tab=table.toString();
				if(i==1) {
				if(tab.contains("th") && tab.contains("/th")) {
					fileWriter = new FileWriter("output\\html\\" + titre.first().text() + ".csv");
					this.inserHtmlTableToCsvFile(table, fileWriter);
					i++;
				}
				}else {
					if(tab.contains("th") && tab.contains("/th")) {
						fileWriter = new FileWriter("output\\html\\" + titre.first().text()+i + ".csv");
						this.inserHtmlTableToCsvFile(table, fileWriter);
						i++;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Erreur lors de la création du fichier .CSV");
			e.printStackTrace();
		}
		return i;
	}
	
	public void pourTousLesTableaux(Document doc, String csvFileName) {
		this.createCsvFiles(doc);
	}
	public void inserHtmlTableToCsvFile(Element table, FileWriter fileWriter){
		String tab=table.toString();
		try {
			Document doc = Jsoup.parseBodyFragment(tab);
			Elements rows = doc.getElementsByTag("tr");
			for (Element row : rows) {
				Elements cells = row.getElementsByTag("th");
				for (Element cell : cells) {
					fileWriter.write(cell.text().concat(","));
				}
				fileWriter.write("\n");
			}

			for (Element row : rows) {
				Elements cells = row.getElementsByTag("td");
				for (Element cell : cells) {
					fileWriter.write(cell.text().concat(","));
				}
				fileWriter.write("\n");
			}

			fileWriter.close();
		    } catch (IOException e) {
		        e.getStackTrace();
		    }	
		}
}
