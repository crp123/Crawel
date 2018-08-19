package merge;

import java.io.*;
import java.util.HashMap;

public class BuyListMap {
    public static HashMap<String,String> getBuyListMap(String mFileName){
        String r = null;
        String[] a = null;
        FileInputStream magicsingleFIS = null;
        BufferedReader reader = null;
        HashMap<String,String> magicsingleMap = null;
        try {
            try {
                magicsingleFIS = new FileInputStream("D:\\crawler\\buylist\\"+mFileName+".txt");
            } catch (FileNotFoundException e) {
                return  magicsingleMap;
            }
            magicsingleMap = new HashMap<>();
            reader = new BufferedReader(new InputStreamReader(magicsingleFIS, "utf-8"));
            while ((r = reader.readLine()) != null){
                if(!r.isEmpty()){
                    a = r.split("\\|");
                    magicsingleMap.put(a[0],a[1]);
                }
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                magicsingleFIS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  magicsingleMap;
    }
}
