package buylist;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class a {


    public static void main(String[] args) throws IOException {

        List<String> urlList;

        //1.新建url对象，表示要访问的网页
        //FileOutputStream fos = null;
        //urlList = UList.getList();
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
            } else if (i % 10 == 2) {
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
            HSSFRow row1;
            HSSFCell cell1;
            HSSFCell cell2;
            //创建新工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            String fileName = FileName1.getFileName(s);
            //新建工作表
            HSSFSheet sheet = workbook.createSheet(fileName);
            int count = 0;
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
                                        if(a[5].contains("- Foil")||Double.parseDouble(a[9].substring(1))<2){
                                            continue;
                                        }
                                        row1 = sheet.createRow(count);
                                        cell1 = row1.createCell(0);
                                        cell2 = row1.createCell(1);
                                        cell1.setCellValue(a[5]);
                                        cell2.setCellValue(a[9]);
                                        count++;
                                    }
                                }

                                //urlConnection.disconnect();
                                System.out.println(fileName + "第" + i + "页完成");
                            }
                        } finally {
                            lock.unlock();
                        }

                    } finally {
                        reader.close();
                        instream.close();
                        response.close();
                        //httpClient.close();
                    }
                } catch (Exception e) {
                    System.out.println("获取不到网页源码：" + e);
                    i--;
                }
            }
            try {
                FileOutputStream fos = new FileOutputStream(new File("D:\\crawler\\crawler7\\" + fileName + ".xls"));
                workbook.write(fos);
                workbook.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(fileName + "完成");
            {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

