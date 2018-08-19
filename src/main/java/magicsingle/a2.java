package magicsingle;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class a2 {


    public static void main(String[] args) throws IOException {

        List<String> urlList;

        //1.新建url对象，表示要访问的网页
        //FileOutputStream fos = null;
        //urlList = UList2.getList();
        urlList = ListFromFile2.getList();

        CopyOnWriteArrayList<String> l1 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l2 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l3 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l4 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l5 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l6 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l7 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l8 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l9 = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> l10 = new CopyOnWriteArrayList<>();

        for (int i = 0; i < urlList.size(); i++) {
            if (i % 10 == 1) {
                l1.add(urlList.get(i));
            }
            else if (i % 10 == 2) {
                l2.add(urlList.get(i));
            } else if (i % 10 == 3) {
                l3.add(urlList.get(i));
            } else if (i % 10 == 4) {
                l4.add(urlList.get(i));
            } else if (i % 10 == 5) {
                l5.add(urlList.get(i));
            } else if (i % 10 == 6) {
                l6.add(urlList.get(i));
            } else if (i % 10 == 7) {
                l7.add(urlList.get(i));
            } else if (i % 10 == 8) {
                l8.add(urlList.get(i));
            } else if (i % 10 == 9) {
                l9.add(urlList.get(i));
            } else if (i % 10 == 0) {
                l10.add(urlList.get(i));
            }

        }




        new Thread(() -> {
            fillSheet(l1);
        }, "A").start();
        new Thread(() -> {
            fillSheet(l2);
        }, "B").start();
        new Thread(() -> {
            fillSheet(l3);
        }, "C").start();
        new Thread(() -> {
            fillSheet(l4);
        }, "D").start();
        new Thread(() -> {
            fillSheet(l5);
        }, "E").start();
        new Thread(() -> {
            fillSheet(l6);
        }, "F").start();
        new Thread(() -> {
            fillSheet(l7);
        }, "G").start();
        new Thread(() -> {
            fillSheet(l8);
        }, "H").start();
        new Thread(() -> {
            fillSheet(l9);
        }, "I").start();
        new Thread(() -> {
            fillSheet(l10);
        }, "J").start();
    }


    public static void fillSheet(CopyOnWriteArrayList<String> list) {
        for (String s : list) {
            RequestConfig globalConfig =
                    RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();


            String r;
            String[] a;
            String fileName = FileName2.getFileName(s);

            try {
                FileOutputStream writeName = new FileOutputStream("D:\\crawler\\magicsingle\\fileNames.txt",true);
                writeName.write((fileName+"|").getBytes());
                writeName.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            BufferedWriter writer = null;
            try {
                fos = new FileOutputStream("D:\\crawler\\magicsingle\\" + fileName + ".txt");
                osw = new OutputStreamWriter(fos);
                writer = new BufferedWriter(osw);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //新建工作表
            loop1:
            for (int i = 1; i <= 50; i++) {
                String str = s + "?page=" + i + "&sort_by_price=0";
                HttpGet httpGet = new HttpGet(str);
                httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:53.0) Gecko/20100101 Firefox/53.0");

                try {
                    //URL url = new URL(s + "?page=" + i + "&sort_by_price=0");

                    Thread.sleep(2000);
                    CloseableHttpResponse response = httpClient.execute(httpGet);


                    BufferedReader reader = null;

                    InputStream instream = null;

                    try {
                        HttpEntity entity = response.getEntity();
                        //System.out.println(EntityUtils.toString(entity));
                        if (entity == null) {
                            System.out.println("空的");
                            //i--;
                            break;
                        }

                        Lock lock = new ReentrantLock();
                        lock.lock();
                        try {

                            if (entity != null) {
                                instream = entity.getContent();
                                reader = new BufferedReader(new InputStreamReader(instream, "utf-8"));


                                while (!reader.ready()) {
                                    try {
                                        System.out.println("第" + i + "页没准备好");
                                        TimeUnit.SECONDS.sleep(60);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                while ((r = reader.readLine()) != null) {
                                    if (r.contains("There are currently no products to display within this category")) {
                                        break loop1;
                                    }
                                    if (r.contains("product-info-row variantRow data-setter")) {
                                        a = r.split("\"");
                                        String amount = a[9].substring(1);
                                        if(amount.contains(",")){
                                            amount.replace(",","");
                                        }
                                        if(a[5].contains("- Foil")||Double.parseDouble(amount)<2){
                                            continue;
                                        }
                                        writer.write(a[5] +"|"+ a[9]);
                                        writer.newLine();
                                    }
                                }

                                //urlConnection.disconnect();
                                System.out.println(fileName + "第" + i + "页完成");
                            }
                        } finally {
                            lock.unlock();
                        }

                    } finally {
                        instream.close();

                        reader.close();
                        response.close();
                        //httpClient.close();
                    }
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "获取不到网页源码：" + e);
                    i--;
                }
            }

            System.out.println(fileName + "完成");
            {
                try {
                    writer.close();
                    osw.close();
                    fos.close();
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}


