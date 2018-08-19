package magicsingle;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PList2 {
    public static List<String> getList() {
        List<String> urlList = new ArrayList<>();
        RequestConfig globalConfig =
                RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();

        String str = "http://store.channelfireball.com/catalog/magic_singles/8";
        HttpGet httpGet = new HttpGet(str);
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:53.0) Gecko/20100101 Firefox/53.0");


        try {
            Thread.sleep(2000);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            BufferedReader reader = null;
            InputStream instream = null;

            HttpEntity entity = response.getEntity();
            if (entity == null) {
                System.out.println("空的");
                //i--;
            }

            Lock lock = new ReentrantLock();
            String r = null;
            String[] a = null;
            lock.lock();
            try {
                if (entity != null) {
                    instream = entity.getContent();
                    reader = new BufferedReader(new InputStreamReader(instream, "utf-8"));

                    while(!reader.ready()){
                        TimeUnit.SECONDS.sleep(60);
                    }
                    while((r=reader.readLine())!=null){
                        if(r.contains("class=\"name\"")){
                            a =  r.split("\"");
                            //System.out.println("http://store.channelfireball.com" + a[1]);
                            urlList.add("http://store.channelfireball.com" + a[1]);
                            //System.out.println(urlList.size());
                        }

                    }

                    reader.close();
                    instream.close();
                    //urlConnection.disconnect();
                    System.out.println(urlList.size());
                    for (String s : urlList) {
                        System.out.println(s);
                    }
                }
            } finally {
                lock.unlock();
                response.close();
            }
        } catch (Exception e) {
            System.out.println("获取不到网页源码："+e);
        }
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlList;
    }
}
