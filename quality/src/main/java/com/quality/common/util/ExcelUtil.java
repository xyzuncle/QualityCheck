package com.quality.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Description: 根据类型获取单元格内容,统一转换成String
 * @Author: yerui
 * @CreateDate : 2019/3/19 6:02
 * @Version: 1.0
 *
 */
public class ExcelUtil {

    //解析公式需要用的地点
    private static FormulaEvaluator evaluator;

    public static String getCellValue(Cell cell, Workbook workbook){
        String result = "";
        if(cell.getCellTypeEnum().getCode()==1){
            result  =  cell.getStringCellValue();
        }else if(cell.getCellTypeEnum().getCode()==0){
            result =   new Double(cell.getNumericCellValue()).intValue() + "";
        }else if(cell.getCellTypeEnum().getCode()==2){
            evaluator=workbook.getCreationHelper().createFormulaEvaluator();
            Short formateInt = cell.getCellStyle().getDataFormat();
            String code = formateInt.toString();
            result =getCellValue(evaluator.evaluate(cell),code);

        }
        return result;
    }

    public static String getCellValue(CellValue cell,String code) {
        if (cell == null) {
            return null;
        }

        if(cell.getCellTypeEnum().getCode()==1){
            return cell.getStringValue();
        }else if(cell.getCellTypeEnum().getCode() ==0){
            //证明带有百分比
            if(code.equals("181") || code.equals("10") || code.equals("179")){
                NumberFormat nf = NumberFormat.getPercentInstance();
                nf.setMaximumFractionDigits(2);
                return nf.format(cell.getNumberValue());
            }else if(code.equals("178") || code.equals("176")){
                String temp = cell.getNumberValue()+"";
                if(temp.length()>6){
                    return new DecimalFormat("#.#E0").format(cell.getNumberValue());
                }
                return String.valueOf(cell.getNumberValue());
            }else{
                return String.valueOf(cell.getNumberValue());
            }
        }else if(cell.getCellTypeEnum().getCode()==4){
            return String.valueOf(cell.getBooleanValue());
        }else{
            return null;
        }

    /*    switch (cell.getCellType()) {
                case cell.getCellType().STRING :
                    return cell.getStringValue();
                case Cell.CELL_TYPE_NUMERIC:
                    //证明带有百分比
                    if(code.equals("181") || code.equals("10") || code.equals("179")){
                        NumberFormat nf = NumberFormat.getPercentInstance();
                        nf.setMaximumFractionDigits(2);
                        return nf.format(cell.getNumberValue());
                    }else if(code.equals("178") || code.equals("176")){
                        String temp = cell.getNumberValue()+"";
                        if(temp.length()>6){
                            return new DecimalFormat("#.#E0").format(cell.getNumberValue());
                        }
                        return String.valueOf(cell.getNumberValue());
                    }else{
                        return String.valueOf(cell.getNumberValue());
                    }

                case Cell.CELL_TYPE_BOOLEAN:
                    return String.valueOf(cell.getBooleanValue());
                default:
                    return null;
        }*/
    }


}
