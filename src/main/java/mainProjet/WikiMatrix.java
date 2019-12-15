package mainProjet;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import connexionAPI.ExtractionToHTML;
import connexionAPI.ExtractionToWiki;
import utils.ConcreteConverter;
import utils.Messages;

public class WikiMatrix {

	public static void main(String[] args) {
		new WikiMatrix();
		System.out.println(utils.Messages.INTRO);
		System.out.println(utils.Messages.ENTRERCHOIX);
		String choix = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			choix = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!choix.equals("1") && !choix.equals("2") && !choix.equals("q")) {
			System.out.println("Erreur veuillez choisir parmi les choix proposes");
			System.out.println(utils.Messages.ENTRERCHOIX);
			try {
				choix = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (choix.equals("1")) {
			ExtractionToHTML extHtml = new ExtractionToHTML();
			ConcreteConverter c=new ConcreteConverter(extHtml);
			System.out.println(Messages.PATIENT);
			assertDoesNotThrow(()->extHtml.getConverter().convertAllToCSVformat(Messages.HTML_OUTPUT_DIR));
			System.out.println(Messages.MESSAGEDEFIN);
		} else if (choix.equals("2")) {
			ExtractionToWiki extWiki = new ExtractionToWiki();
			ConcreteConverter c2=new ConcreteConverter(extWiki);
			System.out.println(Messages.PATIENT);
			assertDoesNotThrow(()->extWiki.getConverter().convertAllToCSVformat(Messages.WIKI_OUTPUT_DIR));
			System.out.println(Messages.MESSAGEDEFIN);
		}

	}

}