package utils;




import abstractClass.Converter;
import connexionAPI.*;
import createfileCSV.GestionnaireCSV;
import interfaceExtractor.Extractor;

public class ConcreteConverter extends Converter {
	
	public ConcreteConverter(Extractor ext) {
		
		 if (ext instanceof ExtractionToHTML) {
			this.extractor = (ExtractionToHTML) ext;	
		} else if (ext instanceof ExtractionToWiki) {
			 this.extractor = (ExtractionToWiki) ext;
		}	
		this.filehandler = new GestionnaireCSV();
	}
}