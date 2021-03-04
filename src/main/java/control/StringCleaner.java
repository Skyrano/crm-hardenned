package control;

public class StringCleaner {
    public static String cleaner(String initString, int maxLenght){
        String finalString = null;
        if (initString != null){
            finalString = initString.replaceAll("<", "&lt");
            finalString = finalString.replaceAll(">", "&gt");
            if (finalString.length() > maxLenght){
                finalString = finalString.substring(0, maxLenght);
            }
        }
        return finalString;
    }
}
