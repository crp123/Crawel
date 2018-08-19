package buylist;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UrlList {
    public static List<String> getList(){
        List<String> parentList = PList.getList();
        List<String> urlList = new ArrayList<>();
        String r;
        String[] a = null;
//1.新建url对象，表示要访问的网页
        urlList.add(parentList.get(0));
        for(int j = 1; j< parentList.size() -1;j++){
            try {
                URL url = new URL(parentList.get(j));
//2.建立http连接,返回连接对象urlconnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//3.获取相应的http状态码，
                int responseCode= urlConnection.getResponseCode();
//4.如果获取成功，从URLconnection对象获取输入流来获取请求网页的源代码
                if(responseCode == 200){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                    while(!reader.ready()){
                        TimeUnit.SECONDS.sleep(60);
                    }
                    while((r=reader.readLine())!=null){
                        if(r.contains("class=\"name\"")){
                            a =  r.split("\"");
                            System.out.println(a[1]);
                            urlList.add("http://store.channelfireball.com" + a[1]);

                        }
                    }
                    reader.close();
                    //urlConnection.disconnect();
                }else{
                    System.out.println("获取不到源代码 ，服务器响应代码为："+responseCode);
                }
            } catch (Exception e) {
                System.out.println("获取不到网页源码："+e);
            }
        }

        urlList.add(parentList.get(parentList.size()-1));
        System.out.println(urlList.size());
        for (String s : urlList) {
            System.out.println(s);
        }
        return urlList;
    }
}
