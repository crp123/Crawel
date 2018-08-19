package buylist;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

public class B {

    public static void main(String[] args) {
        String r;
        try {
            URL url = new URL("http://store.channelfireball.com/buylist/magic_singles-core_sets/65");
//2.建立http连接,返回连接对象urlconnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//3.获取相应的http状态码，
            int responseCode= urlConnection.getResponseCode();
//4.如果获取成功，从URLconnection对象获取输入流来获取请求网页的源代码
            File dest= new File("D:\\pachong\\core_set_2019.html");
            //fos = new FileOutputStream(dest);
            if(responseCode == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                int count = 0;
                while((r=reader.readLine())!=null){
                    if(r.contains("cat-img")){
                        reader.readLine();
                        r=reader.readLine();

                        System.out.println(r);
                    }
                }

            }else{
                System.out.println("获取不到源代码 ，服务器响应代码为："+responseCode);
            }
        } catch (Exception e) {
            System.out.println("获取不到网页源码："+e);
        }
    }

}
