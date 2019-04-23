package com.quality;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.quality.common.util.PoiWordUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class wordTest {

    @Test
    public void SimpleWordExport() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("department", "5");
        map.put("test", "3");

        try {
            XWPFDocument doc = WordExportUtil.exportWord07(
                    "/template/test.docx", map);
            FileOutputStream fos = new FileOutputStream("D:/simple.docx");

            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试poi的版本是3.15
     */
    @Test
    public void testWrod(){
        OPCPackage opcPackage = null;//包含所有POI OOXML文档类的通用功能，打开一个文件包。
        try {
            //这个地方必须是绝对路径，相对路径无法获取到
            opcPackage = POIXMLDocument.openPackage("D:\\workspace\\idea_workspace\\qc_workspace\\QualityCheck\\quality\\src\\test\\resource\\template\\test.docx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        XWPFDocument document = null;//使用XWPF组件XWPFDocument类获取文档内容
        try {
            document = new XWPFDocument(opcPackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //后去每一行的段落的文字
        List<XWPFParagraph> paras = document.getParagraphs();
        //获取表格，
        // 表格和段落是完全分开的，
        int i=1;
        Map<String, Object> values = new HashMap<>();
        values.put("{department}", "政府部门");
        PoiWordUtils poiUtils = new PoiWordUtils();
        for(XWPFParagraph paragraph : paras){
            //获取文字
            String words = paragraph.getText();
            poiUtils.replaceInParagraph(paragraph,"{department}","政府部门");

            System.out.println(words);
        }

        XWPFTable xwpfTable = document.getTables().get(0);

        //在已有的基础上 测试 get addnew set 回去 都不行，
        //但是新创建的很好用
      /*  CTTblBorders borders=xwpfTable.getCTTbl().getTblPr().getTblBorders();
        //水平方向没有线
        CTBorder hBorder=borders.getInsideH();
        hBorder.setVal(STBorder.Enum.forString("none"));
        hBorder.setSz(new BigInteger("1"));
        hBorder.setColor("0000FF");

        borders.setInsideH(hBorder);*/

        //开始测试复制属性这条路
        XWPFTableRow sourceRow = xwpfTable.getRow(1);

        List<XWPFTableCell> cellList = sourceRow.getTableCells();
        XWPFTableRow targetRow = xwpfTable.insertNewTableRow(2);
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        XWPFTableCell targetCell = null;
        for (XWPFTableCell sourceCell : cellList) {
            targetCell = targetRow.addNewTableCell();
            //列属性
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
        }


        //创建的很好用
        XWPFTable table = document.createTable(2,5);

        CTTblBorders borders2=table.getCTTbl().getTblPr().addNewTblBorders();


        //水平方向没有线
        CTBorder hBorder2=borders2.addNewInsideH();
        hBorder2.setVal(STBorder.Enum.forString("none"));
        hBorder2.setSz(new BigInteger("1"));
        hBorder2.setColor("0000FF");


       /* XWPFTableRow firstRow = xwpfTable.insertNewTableRow(1);
        XWPFTableCell firstCell=firstRow.addNewTableCell();
        XWPFTableCell cell2=firstRow.addNewTableCell();

        firstCell.setText("11");
        cell2.setText("22");

        XWPFTableCell cell3=firstRow.addNewTableCell();
        cell3.setText("33");
        //设置单元格的内容
        //获取最后一行的tableCells

        int lastRow = xwpfTable.getRows().size();
        List<XWPFTableCell> tableCells=   xwpfTable.getRow(lastRow-1).getTableCells();

        for(XWPFTableCell cell :tableCells){

            XWPFParagraph para = cell.getParagraphs().get(0);
            para.setBorderLeft(Borders.NONE);
            para.setBorderRight(Borders.BALLOONS_3_COLORS);
            XWPFRun rh = para.createRun();
            rh.setText("test");
            XWPFRun rh1 = para.createRun();
            rh1.setText("-1");
            rh1.setTextPosition(4);

        }*/



      /*  //获取表格中的所有行
        List<XWPFTableRow> xwpfTableRows = xwpfTables.get(0).getRows();
        //获取 表格中的行，
        // 在获取行中的列，
        // 列在获取文本内容
        for(int j=0;j<xwpfTableRows.size();j++){
            XWPFTableRow row =  xwpfTableRows.get(j);
            List<XWPFTableCell> cells = row.getTableCells();

            for(XWPFTableCell  cell: cells){
                List<XWPFParagraph> paragraphList =  cell.getParagraphs();
                for(XWPFParagraph paragraph :paragraphList){
                    System.out.println(paragraph.getText());
                }
            }
        }*/

        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream("D:/simple.docx");
            document.write(outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @Description: 得到Table的CTTblPr,不存在则新建
     */
    public CTTblPr getTableCTTblPr(XWPFTable table) {
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
                .getTblPr();
        return tblPr;
    }


    /**
     *  测试特殊字符
     */
    @Test
    public void wordTest() throws FileNotFoundException {
      /*  ImportParams params = new ImportParams();

        FileInputStream fileInputStream = new FileInputStream(PoiPublicUtil.getWebRootPath("/template/temp.xlsx"));
        List<String> result = null;
        try {
            Workbook workbook = getWorkBook(fileInputStream);

            Sheet sheet =  workbook.getSheetAt(0);
            Row row =  sheet.getRow(0);
            Cell c = row.getCell(0);


        } catch (Exception e) {
            e.printStackTrace();
        }*/
      /*  System.out.println(result);*/
    }


    public static Workbook getWorkBook(FileInputStream file) {

        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file;
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
                //2007
            workbook = new XSSFWorkbook(is);

        } catch (IOException e) {

        }
        return workbook;
    }

    @Test
    public void testE(){
        String testNumber = "2.0E-10";

        if(testNumber.indexOf("E")>0){
            int startIndex = testNumber.indexOf("E");
            String numberR = testNumber.substring(0, startIndex);
            String deep = testNumber.substring(startIndex+1, testNumber.length());
            String numResult = numberR+"x"+"10"+"^"+deep;

        }
    }
}
