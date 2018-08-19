package merge;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MagicSinleList {
    public static List<String> getMagicSingleList() throws IOException {
        List<String> magicsinglelist = new CopyOnWriteArrayList<>();
        FileInputStream buylistFIS = null;
        try {
            buylistFIS = new FileInputStream("D:\\crawler\\magicsingle\\fileNames.txt");

            int len = 0;
            byte[] buf = new byte[1024*1024];
            while((len=buylistFIS.read(buf))!=-1){
                String[] split = new String(buf).split("\\|");
                magicsinglelist = Arrays.asList((String[]) split);
            }
        } catch (FileNotFoundException e) {

        }finally {
            buylistFIS.close();
        }

        return magicsinglelist;
    }
}
