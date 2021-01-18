package com.demo;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取Excel表格数据（根据表格头来获取数据）
 * 例：
 * h1      h2      h3      h4      h5
 * b1-1    b1-2    b1-3    b1-4    b1-5
 * b2-1    b2-2    b2-3    b2-4    b2-5
 */
public class ExcelImportUtils {
    private int headRowNum = 0;//表格头所在的行
    private int bodyRowNum = 0;//表格体所在的行
    private Map<String, Object> map = new HashMap<String, Object>();//封装的数据

    public ExcelImportUtils() {

    }

    public ExcelImportUtils(Integer headRowNum, Integer bodyRowNum) {
        if (headRowNum != null) this.headRowNum = headRowNum;
        if (bodyRowNum != null) this.bodyRowNum = bodyRowNum;
    }

    /**
     * 获取Excel数据
     *
     * @param file
     * @return
     * @throws Exception
     */
    public Map<String, Object> getExcelData(File file) throws Exception {
        ExcelImportUtils demoUtils = this;
        Sheet excel = demoUtils.getExcel(file, 0);
        demoUtils.getHeadData(excel);
        demoUtils.getBodyData(excel);
        return this.getMap();
    }

    /**
     * 获取Excel表格页
     *
     * @param file
     * @param page
     * @return
     * @throws Exception
     */
    public Sheet getExcel(File file, int page) throws Exception {
        Workbook wb = WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(page);
        return sheet;
    }

    public void getHeadData(Sheet sheet) {
        getHeadData(sheet, this.headRowNum);
    }

    public void getBodyData(Sheet sheet) {
        getBodyData(sheet, this.bodyRowNum);
    }

    /**
     * 封装表格头信息
     *
     * @param sheet
     * @param rowNum
     */
    public void getHeadData(Sheet sheet, int rowNum) {
        this.headRowNum = rowNum;
        List head = new ArrayList();
        Row row = sheet.getRow(rowNum);
        if (row != null) {
            for (int i = 0; ; i++) {
                String headVal = this.getValue(row.getCell(i), "1");
                if (!"".equals(headVal)) {
                    String capitalize = ChineseToSpell.getFirstSpell(headVal);
                    head.add(capitalize);
                } else {
                    break;
                }
            }
        }
        map.put("headData", head);
    }

    /**
     * 封装表格体信息
     *
     * @param sheet
     * @param startRow
     */
    public void getBodyData(Sheet sheet, Integer startRow) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        List<String> headData = (List) this.map.get("headData");
        if (startRow == null)
            startRow = this.headRowNum + 1;

        for (int i = startRow; ; i++) {
            Map<String, String> rowMap = new HashMap<String, String>();
            Row row = sheet.getRow(i);
            if (row != null) {
                int cellNum = row.getLastCellNum();

                for (int j = 0; j < cellNum; j++) {
                    String cellVal = "";
                    Cell cell = row.getCell(j);
                    if (cell == null)
                        break;
                    int cellType = cell.getCellType();//获取该列的类型
                    cellVal = this.getValue(cell, "1");//类型为字符串
                    if (j < headData.size())
                        rowMap.put(headData.get(j), cellVal);
                }
                if (rowMap.size() > 0)
                    list.add(rowMap);
            } else {
                break;
            }
        }
        map.put("bodyData", list);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    //按类型获取值
    public String getValue(Cell cell, String sformat) {
        if (cell == null)
            return "";
        String value = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                // 如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    value = sdf
                            .format(HSSFDateUtil.getJavaDate(cell
                                    .getNumericCellValue())).toString().trim();
                    break;
                } else {
                    value = new DecimalFormat(sformat).format(cell
                            .getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue().trim();
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                value = cell.getBooleanCellValue() + "";
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                value = cell.getCellFormula() + "";
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("E:\\02.xlsx");
        ExcelImportUtils demoUtils = new ExcelImportUtils(0, 1);
        Map<String, Object> excelData = demoUtils.getExcelData(file);
        System.out.println(excelData);
    }
}
