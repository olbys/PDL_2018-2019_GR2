package utils;

import java.io.File;

public class Messages {
	
	public static final String INTRO = "----------------------------------------------------------\n"
			+ "Matrix Synthesize Wiki\n\n"
			+ "Permet de creer une matrice de comparaison\n"
			+ "suivant des mots recherches.\n" + "Le programme genere un fichier CSV d'elements qu'on souhaite\n"
			+ "comparer. (Vous devez etre connecte sur internet). "
			+ "Programme développe par M1 MIAGE Groupe 4 (2017-2018).\n\n"
			+ "----------------------------------------------------------\n";
	
	public static final String ENTRERCHOIX = "Veillez choisir le type d'extration : " 
	+ "1 : HTML ou 2 WikiText (q pour quitter)";
	
	public static final String PATIENT = "Veuillez patienter !";
	
	public static final String DEMANDERURL = "renseignez l'adresse wikipedia dont vous voulez extraire les donnees: ";
	
	public static final String MESSAGEDEFIN = "Fin de l'extraction";
	
	
	/**
	 * List of All constants in the project
	 */
	public static String URLS_FILE_PATH = "input" + File.separator + "wikiurls.txt";
	public static String ENGLISH_WIKI_URL = "https://en.wikipedia.org/wiki/";
	public static String FRENCH_WIKI_URL = "https://fr.wikipedia.org/wiki/";
	public static String HTML_OUTPUT_DIR = "output" + File.separator + "html" + File.separator;
	public static String WIKI_OUTPUT_DIR = "output" + File.separator + "wiki" + File.separator;
	public static String EN_WIKIPEDIA_API_BASE_URL = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions|wikitext&rvprop=content&format=json&formatversion=2&titles=";
	public static String FR_WIKIPEDIA_API_BASE_URL = "https://fr.wikipedia.org/w/api.php?action=query&prop=revisions|wikitext&rvprop=content&format=json&formatversion=2&titles=";
	
	public static String CLASS_TO_REMOVE = "not_pertinent";
	public static String ROW_SPAN = "rowspan";
	public static String COL_SPAN = "colspan";
	public static String MBOX_IMAGE_CLASS = "mbox-image";
	public static String WIKI_REGEX = "^https:\\//[a-z]{2}\\.wikipedia\\.org\\/wiki/.+";
		
}
