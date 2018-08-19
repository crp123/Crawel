package magicsingle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListFromFile2 {
    public static CopyOnWriteArrayList<String> getList(){
        CopyOnWriteArrayList<String> list =  new CopyOnWriteArrayList<>();
        BufferedReader bufr = null;
        try {
            //创建一个字符读取流对象和文件相关联。
            FileReader fr = new FileReader("D:\\IdeaWorkspace\\kkkkkkk\\crawler\\crawler1\\src\\main\\resources\\site2.txt");
            //为了提高效率。加入缓冲技术。将字符读取流对象作为参数传递给缓冲对象的构造函数。
            bufr = new BufferedReader(fr);
            String line;
            try {
                while ((line = bufr.readLine()) != null) {
                    list.add(line);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (bufr != null) {
                try {
                    bufr.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

//        for (String s : list) {
//            System.out.println(s);
//        }
//        System.out.println(list.size());
        return list;
    }
}
