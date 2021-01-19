package com.demo.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExportUtils {
    private int headRowNum = 0;//表格头所在的行
    private int bodyRowNum = 1;//表格体所在的行
    private String title;//设置标题
    private String sDate;
    private String eDate;
    private Map<String, Object> map = new HashMap<String, Object>();//封装的数据

    public ExcelExportUtils(){

    }
    public ExcelExportUtils(Map<String,Object> map){
        this.map = map;
    }
    public ExcelExportUtils(Map<String,Object> map,int headRowNum,int bodyRowNum){
        this.map = map;
        this.headRowNum = headRowNum;
        if (headRowNum==bodyRowNum){
            this.bodyRowNum = bodyRowNum+1;
        }else{
            this.bodyRowNum = bodyRowNum;
        }
    }
    public void exportUtil(File file,List<String> list) throws Exception{
        Workbook wb;
        file.delete();
        if (file.exists()) {

            // Load existing
            wb = WorkbookFactory.create(file);
        } else {
            if (file.getName().endsWith(".xls")) {
                wb = new HSSFWorkbook();
            }
            else if (file.getName().endsWith(".xlsx")) {
                wb = new XSSFWorkbook();
            }
            else {
                throw new IllegalArgumentException("I don't know how to create that kind of new file");
            }
        }
        Sheet sheet = wb.createSheet();
        wb.setSheetName(0,"sheet1");
        Map<String, Object> map = this.getMap();

        //获取headData
        List<String> headData = (List<String>) map.get("headData");
        //设置标题
        if(this.title!=null){
            this.headRowNum++;
            this.bodyRowNum++;
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(this.title);
            //合并单元格
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, headData.size() - 1);
            sheet.addMergedRegion(cellRangeAddress);
            //设置单元格样式
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            cell.setCellStyle(cellStyle);
            this.setAllBorder(wb,cell);
        }
        //设置表头
        Row row = sheet.createRow(this.headRowNum);
        for (int i=0;i<headData.size();i++){
            Cell cell = row.createCell(i);
            if (list==null){
                cell.setCellValue(headData.get(i));
            }else{
                cell.setCellValue(list.get(i));
            }
            this.setAllBorder(wb,cell);
        }
        //获取bodyData
        List<Map<String,String>> bodyData = (List<Map<String, String>>) map.get("bodyData");
        for (int i=0;i<bodyData.size();i++){
            Row row1 = sheet.createRow(bodyRowNum);
            for (int j=0;j<headData.size();j++){
                Cell  cell = row1.createCell(j);
                String s = bodyData.get(i).get(headData.get(j));
                cell.setCellValue(s);
                this.setAllBorder(wb,cell);
            }
            this.bodyRowNum++;
        }
        //文档输出
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //设置边框
    public void setAllBorder(Workbook wb,Cell cell){
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cell.setCellStyle(cellStyle);
    }

    public static void main(String[] args) throws Exception {
        File file = new File("E:\\02.xlsx");
        ExcelImportUtils demoUtils = new ExcelImportUtils(0, 1);
        Map<String, Object> excelData = demoUtils.getExcelData(file);


        ExcelExportUtils excelExportUtils = new ExcelExportUtils(excelData);
        File file1 = new File("E:\\05.xlsx");
        List list = new ArrayList();
        list.add("ID");
        list.add("平台名字");
        list.add("用户编号");
        list.add("表号");
        excelExportUtils.exportUtil(file1,null);
    }
}
