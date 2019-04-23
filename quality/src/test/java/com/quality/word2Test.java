package com.quality;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class word2Test {

    @Test
    public void create() throws Exception {
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

        this.createSimpleTableWithNotBd(document);

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




    //表格无边框
    public  void createSimpleTableWithNotBd(XWPFDocument doc) throws Exception {
        List<String> columnList = new ArrayList<String>();
        columnList.add("序号");
        columnList.add("姓名信息|姓甚|名谁");
        columnList.add("名刺信息|籍贯|营生");
        XWPFTable table = doc.createTable(2,5);

        CTTblBorders borders=table.getCTTbl().getTblPr().addNewTblBorders();


        //水平方向没有线
        CTBorder hBorder=borders.addNewInsideH();
        hBorder.setVal(STBorder.Enum.forString("none"));
        hBorder.setSz(new BigInteger("1"));
        hBorder.setColor("0000FF");

      //垂直方向 没有实体线
       /* CTBorder vBorder=borders.addNewInsideV();
        vBorder.setVal(STBorder.Enum.forString("none"));
        vBorder.setSz(new BigInteger("1"));
        vBorder.setColor("00FF00");*/

        CTBorder lBorder=borders.addNewLeft();
        lBorder.setVal(STBorder.Enum.forString("none"));
        lBorder.setSz(new BigInteger("1"));
        lBorder.setColor("3399FF");

        CTBorder rBorder=borders.addNewRight();
        rBorder.setVal(STBorder.Enum.forString("none"));
        rBorder.setSz(new BigInteger("1"));
        rBorder.setColor("F2B11F");

        CTBorder tBorder=borders.addNewTop();
        tBorder.setVal(STBorder.Enum.forString("none"));
        tBorder.setSz(new BigInteger("1"));
        tBorder.setColor("C3599D");

        CTBorder bBorder=borders.addNewBottom();
        bBorder.setVal(STBorder.Enum.forString("none"));
        bBorder.setSz(new BigInteger("1"));
        bBorder.setColor("F7E415");

        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc=tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger("8000"));
        tblWidth.setType(STTblWidth.DXA);

        XWPFTableRow firstRow=null;
        XWPFTableRow secondRow=null;
        XWPFTableCell firstCell=null;
        XWPFTableCell secondCell=null;

        for(int i=0;i<2;i++){
            firstRow=table.getRow(i);
            firstRow.setHeight(380);
            for(int j=0;j<5;j++){
                firstCell=firstRow.getCell(j);
                setCellText(firstCell, "测试", "FFFFC9", 1600);
            }
        }

        firstRow=table.insertNewTableRow(0);
        secondRow=table.insertNewTableRow(1);
        firstRow.setHeight(380);
        secondRow.setHeight(380);
        for(String str:columnList){
            if(str.indexOf("|") == -1){
                firstCell=firstRow.addNewTableCell();
                secondCell=secondRow.addNewTableCell();
                createVSpanCell(firstCell, str,"CCCCCC",1600,STMerge.RESTART);
                createVSpanCell(secondCell, "", "CCCCCC", 1600,null);
            }else{
                String[] strArr=str.split("\\|");
                firstCell=firstRow.addNewTableCell();
                createHSpanCell(firstCell, strArr[0],"CCCCCC",1600,STMerge.RESTART);
                for(int i=1;i<strArr.length-1;i++){
                    firstCell=firstRow.addNewTableCell();
                    createHSpanCell(firstCell, "","CCCCCC",1600,null);
                }
                for(int i=1;i<strArr.length;i++){
                    secondCell=secondRow.addNewTableCell();
                    setCellText(secondCell, strArr[i], "CCCCCC", 1600);
                }
            }
        }
    }

    public  void setCellText(XWPFTableCell cell,String text, String bgcolor, int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        //cell.setColor(bgcolor);
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();
        ctshd.setFill(bgcolor);
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cell.setText(text);
    }
    public void createHSpanCell(XWPFTableCell cell,String value, String bgcolor, int width,STMerge.Enum stMerge){
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        cellPr.addNewHMerge().setVal(stMerge);
        cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
    }

    public void createVSpanCell(XWPFTableCell cell,String value, String bgcolor, int width,STMerge.Enum stMerge){
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        cellPr.addNewVMerge().setVal(stMerge);
        cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
    }

    public void addNewPage(XWPFDocument document,BreakType breakType){
        XWPFParagraph xp = document.createParagraph();
        xp.createRun().addBreak(breakType);
    }

    public void saveDocument(XWPFDocument document,String savePath) throws Exception{
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }


}
