package com.quality.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoiWordUtils {

    /**
     * 把科学技术法的数字转换成有规律的数字
     * @param str
     * @return
     */
    public String[]  replaceDeep(String str){
        String numResult[]= new String[2];
        if(str.indexOf("E")>0){
            int startIndex = str.indexOf("E");
            String numberR = str.substring(0, startIndex);
            String deep = str.substring(startIndex+1, str.length());
            numResult[0] = numberR+"x"+"10";
            numResult[1] = deep;
        }
        return numResult;
    }




    /**
     * 替换所有段落中的标记
     *
     * @param xwpfParagraphList
     * @param params
     */
    public static void replaceInAllParagraphs(List<XWPFParagraph> xwpfParagraphList, Map<String, String> params) {
        for (XWPFParagraph paragraph : xwpfParagraphList) {
            if (paragraph.getText() == null || paragraph.getText().equals("")) continue;
            for (String key : params.keySet()) {
                if (paragraph.getText().contains(key)) {
                    replaceInParagraph(paragraph, key, params.get(key));
                }
            }
        }
    }

    /**
     * 替换段落中的字符串
     *
     * @param xwpfParagraph
     * @param oldString
     * @param newString
     */
    public static void replaceInParagraph(XWPFParagraph xwpfParagraph, String oldString, String newString) {
        Map<String, Integer> pos_map = findSubRunPosInParagraph(xwpfParagraph, oldString);
        if (pos_map != null) {
            List<XWPFRun> runs = xwpfParagraph.getRuns();
            XWPFRun modelRun = runs.get(pos_map.get("end_pos"));
            XWPFRun xwpfRun = xwpfParagraph.insertNewRun(pos_map.get("end_pos") + 1);
            xwpfRun.setText(newString);
            if (modelRun.getFontSize() != -1) xwpfRun.setFontSize(modelRun.getFontSize());//默认值是五号字体，但五号字体getFontSize()时，返回-1
            xwpfRun.setFontFamily(modelRun.getFontFamily());
            for (int i = pos_map.get("end_pos"); i >= pos_map.get("start_pos"); i--) {
                xwpfParagraph.removeRun(i);
            }
        }
    }

    /**
     * 找到段落中子串的起始XWPFRun下标和终止XWPFRun的下标
     *
     * @param xwpfParagraph
     * @param substring
     * @return
     */
    public static Map<String, Integer> findSubRunPosInParagraph(XWPFParagraph xwpfParagraph, String substring) {

        List<XWPFRun> runs = xwpfParagraph.getRuns();
        int start_pos = 0;
        int end_pos = 0;
        String subtemp = "";
        for (int i = 0; i < runs.size(); i++) {
            subtemp = "";
            start_pos = i;
            for (int j = i; j < runs.size(); j++) {
                if (runs.get(j).getText(runs.get(j).getTextPosition()) == null) continue;
                subtemp += runs.get(j).getText(runs.get(j).getTextPosition());
                if (subtemp.equals(substring)) {
                    end_pos = j;
                    Map<String, Integer> map = new HashMap<>();
                    map.put("start_pos", start_pos);
                    map.put("end_pos", end_pos);
                    return map;
                }
            }
        }
        return null;
    }


    /**
     * 替换所有的表格
     *
     * @param xwpfTableList
     * @param params
     */
    public static void replaceInTables(List<XWPFTable> xwpfTableList, Map<String, String> params) {
        for (XWPFTable table : xwpfTableList) {
            replaceInTable(table, params);

        }
    }

    /**
     * 替换一个表格中的所有行
     *
     * @param xwpfTable
     * @param params
     */
    public static void replaceInTable(XWPFTable xwpfTable, Map<String, String> params) {
        List<XWPFTableRow> rows = xwpfTable.getRows();
        replaceInRows(rows, params);
    }


    /**
     * 替换表格中的一行
     *
     * @param rows
     * @param params
     */
    public static void replaceInRows(List<XWPFTableRow> rows, Map<String, String> params) {
        for (int i = 0; i < rows.size(); i++) {
            XWPFTableRow row = rows.get(i);
            replaceInCells(row.getTableCells(), params);
        }
    }

    /**
     * 替换一行中所有的单元格
     *
     * @param xwpfTableCellList
     * @param params
     */
    public static void replaceInCells(List<XWPFTableCell> xwpfTableCellList, Map<String, String> params) {
        for (XWPFTableCell cell : xwpfTableCellList) {
            replaceInCell(cell, params);
        }
    }

    /**
     * 替换表格中每一行中的每一个单元格中的所有段落
     *
     * @param cell
     * @param params
     */
    public static void replaceInCell(XWPFTableCell cell, Map<String, String> params) {
        List<XWPFParagraph> cellParagraphs = cell.getParagraphs();
        replaceInAllParagraphs(cellParagraphs, params);
    }

    /**
     * @Description: 设置Table的边框
     */
    public void setTableBorders(XWPFTable table, STBorder.Enum borderType,
                                String size, String color, String space) {
        CTTblPr tblPr = getTableCTTblPr(table);
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders()
                : tblPr.addNewTblBorders();
        CTBorder hBorder = borders.isSetInsideH() ? borders.getInsideH()
                : borders.addNewInsideH();
        hBorder.setVal(borderType);
        hBorder.setSz(new BigInteger(size));
        hBorder.setColor(color);
        hBorder.setSpace(new BigInteger(space));

        CTBorder vBorder = borders.isSetInsideV() ? borders.getInsideV()
                : borders.addNewInsideV();
        vBorder.setVal(borderType);
        vBorder.setSz(new BigInteger(size));
        vBorder.setColor(color);
        vBorder.setSpace(new BigInteger(space));

        CTBorder lBorder = borders.isSetLeft() ? borders.getLeft() : borders
                .addNewLeft();
        lBorder.setVal(borderType);
        lBorder.setSz(new BigInteger(size));
        lBorder.setColor(color);
        lBorder.setSpace(new BigInteger(space));

        CTBorder rBorder = borders.isSetRight() ? borders.getRight() : borders
                .addNewRight();
        rBorder.setVal(borderType);
        rBorder.setSz(new BigInteger(size));
        rBorder.setColor(color);
        rBorder.setSpace(new BigInteger(space));

        CTBorder tBorder = borders.isSetTop() ? borders.getTop() : borders
                .addNewTop();
        tBorder.setVal(borderType);
        tBorder.setSz(new BigInteger(size));
        tBorder.setColor(color);
        tBorder.setSpace(new BigInteger(space));

        CTBorder bBorder = borders.isSetBottom() ? borders.getBottom()
                : borders.addNewBottom();
        bBorder.setVal(borderType);
        bBorder.setSz(new BigInteger(size));
        bBorder.setColor(color);
        bBorder.setSpace(new BigInteger(space));
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

    public void setTableBorders(XWPFTable table, CTBorder left, CTBorder top,
                                CTBorder right, CTBorder bottom) {
        CTTblBorders tblBorders = getTableBorders(table);
        if (left != null) {
            tblBorders.setLeft(left);
        }
        if (top != null) {
            tblBorders.setTop(top);
        }
        if (right != null) {
            tblBorders.setRight(right);
        }
        if (bottom != null) {
            tblBorders.setBottom(bottom);
        }
    }

    /**
     * @Description: 得到Table的边框,不存在则新建
     */
    public CTTblBorders getTableBorders(XWPFTable table) {
        CTTblPr tblPr = getTableCTTblPr(table);
        CTTblBorders tblBorders = tblPr.isSetTblBorders() ? tblPr
                .getTblBorders() : tblPr.addNewTblBorders();
        return tblBorders;
    }

    /**
     * @Description: 创建表格,创建后表格至少有1行1列,设置列宽
     */
    public XWPFTable createTable(XWPFDocument xdoc, int rowSize, int cellSize,
                                 boolean isSetColWidth, int[] colWidths) {
        XWPFTable table = xdoc.createTable(rowSize, cellSize);
        if (isSetColWidth) {
            CTTbl ttbl = table.getCTTbl();
            CTTblGrid tblGrid = ttbl.addNewTblGrid();
            for (int j = 0, len = Math.min(cellSize, colWidths.length); j < len; j++) {
                CTTblGridCol gridCol = tblGrid.addNewGridCol();
                gridCol.setW(new BigInteger(String.valueOf(colWidths[j])));
            }
        }
        return table;
    }

    /**
     *
     *  设置单元格的内容并设置相关的属性，这个方法主要是用在表头
     * @param cell   设置内容的单元格
     * @param text   需要设置的文本内容
     * @param bgcolor 需要设置的宽度
     * @param width 单元格的宽度
     * @param p  段落
     * @param pRun  run
     * @param cnFontFamily  中文字体
     * @param enFontFamily  英文字体
     * @param fontSize   字体的大小
     * @param spacingValue   这里是段落的边距，这里是最小值 0，还有很多 比如多倍边距，单倍距。在
     *                       STLineSpacingRule.AT_LEAST 这个规则中
     */
    public  void setCellText(XWPFTableCell cell,String text, String bgcolor, int width,
                             XWPFParagraph p, XWPFRun pRun,String cnFontFamily,
                             String enFontFamily,String fontSize,String spacingValue,
                             STVerticalJc.Enum stv,STJc.Enum stjc) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        //cell.setColor(bgcolor);
        CTTcPr ctPr = cttc.addNewTcPr();
        //CTShd ctshd = ctPr.addNewShd();
        ctPr.addNewVAlign().setVal(stv);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(stjc);

        CTRPr pRpr = getRunCTRPr(p, pRun);

        //准备设置字体 和大小
        CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr
                .addNewRFonts();
        if (StringUtils.isNotBlank(enFontFamily)) {
            fonts.setAscii(enFontFamily);
            fonts.setHAnsi(enFontFamily);
        }
        if (StringUtils.isNotBlank(cnFontFamily)) {
            fonts.setEastAsia(cnFontFamily);
            fonts.setHint(STHint.EAST_ASIA);
        }
        // 设置字体大小
        CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
        sz.setVal(new BigInteger(fontSize));

        CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr
                .addNewSzCs();
        szCs.setVal(new BigInteger(fontSize));

        //设置了段落与段落之间的行间距
        if(StringUtils.isNotBlank(spacingValue)) {
            setParagraphSpacingInfo(p, true, "0", "0", "0", "0", true, "0",
                    STLineSpacingRule.AT_LEAST);
        }

        //设置run的文本内容
        pRun.setText(text);

    }

    /**
     * @Description: 设置列宽和垂直对齐方式
     */
    public void setCellWidthAndVAlign(XWPFTableCell cell, String width,
                                      STTblWidth.Enum typeEnum, STVerticalJc.Enum vAlign) {
        CTTcPr tcPr = getCellCTTcPr(cell);
        CTTblWidth tcw = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
        if (width != null) {
            tcw.setW(new BigInteger(width));
        }
        if (typeEnum != null) {
            tcw.setType(typeEnum);
        }
        if (vAlign != null) {
            CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr
                    .addNewVAlign();
            vJc.setVal(vAlign);
        }
    }

    /**
     *
     * @Description: 得到Cell的CTTcPr,不存在则新建
     */
    public CTTcPr getCellCTTcPr(XWPFTableCell cell) {
        CTTc cttc = cell.getCTTc();
        CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
        return tcPr;
    }


    /**
     * @Description: 得到XWPFRun的CTRPr
     */
    public CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
        CTRPr pRpr = null;
        if (pRun.getCTR() != null) {
            pRpr = pRun.getCTR().getRPr();
            if (pRpr == null) {
                pRpr = pRun.getCTR().addNewRPr();
            }
        } else {
            pRpr = p.getCTP().addNewR().addNewRPr();
        }
        return pRpr;
    }

    /**
     * @Description: 得到段落CTPPr
     */
    public CTPPr getParagraphCTPPr(XWPFParagraph p) {
        CTPPr pPPr = null;
        if (p.getCTP() != null) {
            if (p.getCTP().getPPr() != null) {
                pPPr = p.getCTP().getPPr();
            } else {
                pPPr = p.getCTP().addNewPPr();
            }
        }
        return pPPr;
    }



    /**
     * @Description: 设置段落对齐
     */
    public void setParagraphAlignInfo(XWPFParagraph p,
                                      ParagraphAlignment pAlign, TextAlignment valign) {
        if (pAlign != null) {
            p.setAlignment(pAlign);
        }
        if (valign != null) {
            p.setVerticalAlignment(valign);
        }
    }


    /**
     * @Description: 设置段落间距信息,一行=100 一磅=20
     */
    public void setParagraphSpacingInfo(XWPFParagraph p, boolean isSpace,
                                        String before, String after, String beforeLines, String afterLines,
                                        boolean isLine, String line, STLineSpacingRule.Enum lineValue) {
        CTPPr pPPr = getParagraphCTPPr(p);
        CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing()
                : pPPr.addNewSpacing();
        if (isSpace) {
            // 段前磅数
            if (before != null) {
                pSpacing.setBefore(new BigInteger(before));
            }
            // 段后磅数
            if (after != null) {
                pSpacing.setAfter(new BigInteger(after));
            }
            // 段前行数
            if (beforeLines != null) {
                pSpacing.setBeforeLines(new BigInteger(beforeLines));
            }
            // 段后行数
            if (afterLines != null) {
                pSpacing.setAfterLines(new BigInteger(afterLines));
            }
        }
        // 间距
        if (isLine) {
            if (line != null) {
                pSpacing.setLine(new BigInteger(line));
            }
            if (lineValue != null) {
                pSpacing.setLineRule(lineValue);
            }
        }
    }


}
