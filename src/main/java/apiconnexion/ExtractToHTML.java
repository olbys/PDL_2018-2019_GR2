/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiconnexion;

import interfaceExtractor.Extractor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * @author Jean Olivier Koko
 * Classe permettant d'extraire tous les tableaux issus d'un lien wikipedia suivant une certaine structure
 * pour cette version un seule url est lue par instance
 */
public class ExtractToHTML implements Extractor{
    private String url;
    
    static Logger logger = Logger.getLogger(ExtractToHTML.class);
     
    
    public ExtractToHTML(){   
            this.url ="";
    }
    
    
    /**
     * retoune l'url qui doit etre lue pour l'extraction
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * l'url qui doit etre lue pour l'extraction
     * @param url {@link String}
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * @param urlTo {@link String}  
     * constructeur prenant l'url a extraire 
     */
    public ExtractToHTML (String urlTo){
        this.url = urlTo;
    }
    
    
    
    
    @Override
    public boolean urlIsValid(){    
        String pattern = "http[s]://en.wikipedia.org/wiki/";
        Pattern regPat = Pattern.compile(pattern , Pattern.CASE_INSENSITIVE);
        Matcher matcher = regPat.matcher(this.url);
      return matcher.find();
    }
    
    @Override
    public boolean connexionDone(){
        return false;
    }
    
    
    
    
    
    
    
    public static void main(String [] args){
        
        ExtractToHTML ext =  new ExtractToHTML("https://google");
        
        System.err.println(" "+ext);
    }
    
    
}
