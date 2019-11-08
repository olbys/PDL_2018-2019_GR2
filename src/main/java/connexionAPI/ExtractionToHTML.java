
package connexionAPI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class ExtractionToHTML {
	private String url;

	public ExtractionToHTML(String url) {
		this.url = url;
	}
	public ExtractionToHTML() {
	}

	public static void main(String[] args) {
		//ExtractionToHTML html = new ExtractionToHTML("https://en.wikipedia.org/wiki/Comparison_of_Norwegian_Bokm%C3%A5l_and_Standard_Danish");
		//html.getContentHtml();
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
	public void getContentHtml() {// trouver un meilleur nom pour cette fonction
		System.out.println("D�but de l'extraction :");

		// r�cup�ration des donn�es d'une page wikip�dia dans un Document
		Document doc = getHtmlJsoup(this.url);

		// Cr�ation du fichier csv avec comme titre le premier h1 de la page wikip�dia
		if(doc!=null) {
			FileWriter fileWriter = creationFichierCsv(doc);
			// parcours du code html et insertion dans le fichier csv des donn�es contenues
			// dans des tableaux
			insertionDonnesTableauDansFichierCSV(doc, fileWriter);
		}else {
			System.out.println("le document est  null");
		}

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
	public FileWriter creationFichierCsv(Document doc) { // peut �tre d�placer dans un autre package
		// (createFileCSV)
		// Cr�ation du fichier csv avec comme titre le premier h1 de la page wikip�dia
		Elements titre = doc.select("h1");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("output\\html\\" + titre.first().text() + ".csv");
		} catch (IOException e) {
			System.out.println("erreur lors de la cr�ation du fichier .CSV");
			e.printStackTrace();
		}
		return fileWriter;
	}
	public void pourTousLesTableaux(Document doc, String csvFileName) {
		if(doc!=null) {
			if(doc.select("table")!=null) {
				int i=1;
				FileWriter fileWriter=null;
				for (Element table : doc.select("table")) {
					if (table.className().contains("wikitable")) {
						try {
							fileWriter=new FileWriter(csvFileName);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						insertionDonnesTableauDansFichierCSV2(table,fileWriter);
						i++;
						csvFileName=csvFileName.substring(0,csvFileName.indexOf("-"));
						//csvFileName=csvFileName.trim() + "-" + i + ".csv";
						csvFileName=csvFileName+"-"+i+".csv";
					}
				}
			}
		}
	}
	public void insertionDonnesTableauDansFichierCSV2(Element table, FileWriter fileWriter) {
		for (Element row : table.select("tr")) {
			String ligneDunTableau = "";
			for(Element th : row.select("th")) {
				ligneDunTableau += th.text().trim() + ";";
			}
			for (Element td : row.select("td")) {
				ligneDunTableau += td.text().trim() + ";";
				for(Element th : td.select("th")) {
					ligneDunTableau += th.text().trim() + ";";
				}
			}
			try {
				fileWriter.append(ligneDunTableau);
				fileWriter.append("\n");
			} catch (IOException e) {
				System.out.println("erreur lors de l'ajout d'une ligne dans le fichier .CSV");
				e.printStackTrace();
			}
		}


		try {
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("erreur lors de la fermeture du fichier .CSV");
			e.printStackTrace();
		}
	}


	public void insertionDonnesTableauDansFichierCSV(Document doc, FileWriter fileWriter) {


		if(doc!=null) {
			if(doc.select("table")!=null) {

				for (Element table : doc.select("table")) {

					if (table.className().contains("wikitable")) {

						for (Element row : table.select("tr")) {
							String ligneDunTableau = "";
							for(Element th : row.select("th")) {
								ligneDunTableau += th.text().trim() + ";";
							}
							for (Element td : row.select("td")) {
								ligneDunTableau += td.text().trim() + ";";
								for(Element th : td.select("th")) {
									ligneDunTableau += th.text().trim() + ";";
								}
							}
							try {
								fileWriter.append(ligneDunTableau);
								fileWriter.append("\n");
							} catch (IOException e) {
								System.out.println("erreur lors de l'ajout d'une ligne dans le fichier .CSV");
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("erreur lors de la fermeture du fichier .CSV");
			e.printStackTrace();
		}
	}


}
