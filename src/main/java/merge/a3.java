package merge;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class a3 {
    public static void main(String[] args) throws IOException {

        List<String> magicsinglelist = MagicSinleList.getMagicSingleList();
        List<String> buyinglelist = BuyList.getBuyList();
        String key = null;
        String v2 = null;

        for (String mFileName : magicsinglelist) {
            HSSFRow row1;
            HSSFCell cell1;
            HSSFCell cell2;
            HSSFCell cell3;
            //创建新工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();

            //新建工作表
            HSSFSheet sheet = workbook.createSheet(mFileName);

            HashMap<String, String> magicSingleMap = MagicSingleMap.getMagicSingleMap(mFileName);
            if(magicSingleMap == null){
                continue;
            }
            HashMap<String, String> buyListMap = BuyListMap.getBuyListMap(mFileName);

            int count = 0;
            row1 = sheet.createRow(count);
            cell1 = row1.createCell(0);
            cell2 = row1.createCell(1);
            cell3 = row1.createCell(2);
            cell1.setCellValue("名称");
            cell2.setCellValue("卖价");
            cell3.setCellValue("买价");
            count += 1;

            for (Map.Entry<String, String> entry : magicSingleMap.entrySet()) {
                key = entry.getKey();
                row1 = sheet.createRow(count);
                cell1 = row1.createCell(0);
                cell2 = row1.createCell(1);
                cell3 = row1.createCell(2);
                cell1.setCellValue(key);
                cell2.setCellValue(entry.getValue());
                if(buyListMap != null){
                    v2 = buyListMap.get(key);
                    cell3.setCellValue(v2);
                }else{
                    cell3.setCellValue((String) null);
                }

                count++;
            }
            System.out.println(mFileName + "完成");
            try {
                FileOutputStream fos = new FileOutputStream(new File("D:\\crawler\\result\\" + mFileName + ".xls"));
                workbook.write(fos);
                workbook.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
