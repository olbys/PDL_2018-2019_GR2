
package connexionAPI;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import abstractClass.AbstractExtractor;
import utils.ConcreteConverter;
import utils.Messages;

public class ExtractionToHTML extends AbstractExtractor {
	ConcreteConverter converter;

	public ExtractionToHTML() {
		this.converter = new ConcreteConverter(this);
		this.urls=new ArrayList<String>();
	}

	public static void main(String[] args) {
		ExtractionToHTML html = new ExtractionToHTML();
		html.converter.convertAllToCSVformat(Messages.HTML_OUTPUT_DIR);
	}

	public void setUrls(String url) throws IOException {
		this.urls.addAll(this.getUrlsFromFile());
	}
	public List<String> getUrls() {
		return super.urls;
	}
	public ConcreteConverter getConverter() {
		return converter;
	}
	public Document getDocumentFromHtml(String langage, String url) throws IOException {
		if(langage == "en")
			return Jsoup.connect(Messages.ENGLISH_WIKI_URL + url).get();
		return Jsoup.connect(Messages.FRENCH_WIKI_URL + url).get();
	}

	
}