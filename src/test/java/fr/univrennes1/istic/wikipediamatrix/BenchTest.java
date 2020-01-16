package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import connexionAPI.ExtractionToHTML;
import connexionAPI.ExtractionToWiki;
import utils.Converter;
import utils.Messages;

public class BenchTest {

	public static void main(String[] args) {
		new BenchTest();
		System.out.println(utils.Messages.INTRO);
		System.out.println(utils.Messages.ENTRERCHOIX);
		ExtractionToHTML extHtml = new ExtractionToHTML();
		
		System.out.println(Messages.PATIENT);
		assertDoesNotThrow(()->extHtml.getConverter().convertAllToCSVformat(Messages.HTML_OUTPUT_DIR));
		System.out.println(Messages.MESSAGEDEFIN+" Html");
		ExtractionToWiki extWiki = new ExtractionToWiki();
		
		System.out.println(Messages.PATIENT);
		assertDoesNotThrow(()->extWiki.getConverter().convertAllToCSVformat(Messages.WIKI_OUTPUT_DIR));
		System.out.println(Messages.MESSAGEDEFIN + " Wiki");
		}
}