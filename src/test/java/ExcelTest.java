package buylist;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.*;
import java.net.URL;
import java.util.List;

public class ExcelTest {
    private UrlList urlList;

    public static void main(String[] args) throws IOException {
        String r;
        String[] a;
        List<String> urlList;
        HSSFRow row1;
        HSSFCell cell1;
        HSSFCell cell2;

//1.新建url对象，表示要访问的网页
        //FileOutputStream fos = null;
        //urlList = UrlList.getList();
        //创建新工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //新建工作表
        HSSFSheet sheet = workbook.createSheet("core_set_2019");
        try {
            URL url = new URL("http://store.channelfireball.com/buylist/magic_singles-core_set_2019/7373");
//2.建立http连接,返回连接对象urlconnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//3.获取相应的http状态码，
            int responseCode= urlConnection.getResponseCode();
//4.如果获取成功，从URLconnection对象获取输入流来获取请求网页的源代码
            //fos = new FileOutputStream(dest);
            if(responseCode == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                int count = 0;
                while((r=reader.readLine())!=null){
                    if(r.contains("product-info-row variantRow data-setter")){
                        a = r.split("\"");
                        row1 = sheet.createRow(count);
                        cell1 = row1.createCell(0);
                        cell2 = row1.createCell(1);
                        cell1.setCellValue(a[5]);
                        cell2.setCellValue(a[9]);
                        count++;
                    }
                }
                FileOutputStream fos = new FileOutputStream(new File("D:\\pachong\\core_set_2019.xls"));
                workbook.write(fos);
                workbook.close();
                fos.close();
            }else{
                System.out.println("获取不到源代码 ，服务器响应代码为："+responseCode);
            }
        } catch (Exception e) {
            System.out.println("获取不到网页源码："+e);

        }





    }
}
