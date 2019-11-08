package interfaceExtractor;

public interface Extractor {
    
    /**
     * vérifie sur l'url correspond au lien en.wikipedia.org/wiki/ 
     * @return boolean 
     */
     boolean urlIsValid();
    
     /**
      *  verifie si la connexion http a pu s'etablir 
      * @return boolean  
      */
     boolean connexionDone();
     
    
     
     
    
    

}
