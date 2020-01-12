package game;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class to handle change of language
 * Greek/English are the languages supported
 *
 * @author Maria Martha Baroni
 *
 */
public class Language {

    private Locale currentLocale;
    private ResourceBundle bundle;

    // constructor
    public Language (){
        currentLocale = Locale.getDefault();
        bundle = ResourceBundle.getBundle("language", currentLocale);
    }

    /**
     * Changes the text given
     * @param key value for the text
     * @return the changed text
     */
    public String getText(String key){
        return bundle.getString(key);
    }

    /**
     * Switches between languages
     * @param locale greek or english locale
     */
    public void switchLanguage (Locale locale){
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("language", locale);
    }
}
