package buylist;

public class FileName1 {
    public static String getFileName(String url) {

        String[] urlList =  url.split("/");
        //System.out.println(urlList[4].split("-")[1]);
        return urlList[4].substring(14);
    }
}
