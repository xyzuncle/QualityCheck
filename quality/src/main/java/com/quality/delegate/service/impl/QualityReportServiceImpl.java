package com.quality.delegate.service.impl;

import cn.afterturn.easypoi.util.PoiPublicUtil;
import cn.afterturn.easypoi.view.EasypoiTemplateExcelView;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.quality.common.dto.PageResult;
import com.quality.common.entity.QualityAttachment;
import com.quality.common.fastdfs.FastDFSClient;
import com.quality.common.service.impl.QualityAttachmentServiceImpl;
import com.quality.common.util.BeanCopierUtils;
import com.quality.common.util.ExcelUtil;
import com.quality.common.util.PoiWordUtils;
import com.quality.common.util.Tools;
import com.quality.delegate.dto.QualityAssignmentStatementDto;
import com.quality.delegate.dto.QualityReportDto;
import com.quality.delegate.dto.ReportDto;
import com.quality.delegate.dto.ReportIndexDto;
import com.quality.delegate.entity.QualityCheckAbility;
import com.quality.delegate.entity.QualityReport;
import com.quality.delegate.entity.QualitySample;
import com.quality.delegate.entity.QualityTask;
import com.quality.delegate.mapper.QualityReportMapper;
import com.quality.delegate.service.IQualityCheckAbilityService;
import com.quality.delegate.service.IQualityReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quality.delegate.service.IQualitySampleService;
import com.quality.delegate.service.IQualityTaskService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.joda.time.DateTime;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2019-03-02
 */
@Service
public class QualityReportServiceImpl extends ServiceImpl<QualityReportMapper, QualityReport> implements IQualityReportService {

    @Autowired
    private IQualitySampleService sampleService;

    @Autowired
    private IQualityTaskService taskService;

    @Autowired
    private IQualityCheckAbilityService checkAbilityService;

    @Autowired
    private QualityAttachmentServiceImpl qualityAttachmentService;

    private FastDFSClient fastDFSClient = new FastDFSClient();


    @Autowired
    private QualityAssignmentStatementServiceImpl qualityAssignmentStatementService;


    /**
     *  中伟写的数据会显
     * @param qualityReportPage
     * @return
     */
    @Override
    public PageResult<QualityReportDto> converQualityReportDto(PageResult<QualityReport> qualityReportPage) {

        PageResult<QualityReportDto> qualityReportDtotListPage = new PageResult<QualityReportDto>();
        List<QualityReport> list = qualityReportPage.getData();

        List<QualityReportDto> dtolist = new ArrayList<QualityReportDto>();

        for(int i=0;i<list.size();i++){

            QualityReport report = list.get(i);
            QualityReportDto dto = new QualityReportDto();
            BeanCopierUtils.copyProperties(report, dto);

            //样品
            String sampleId = report.getSampleId();
            if(StringUtils.isNotBlank(sampleId)){
                 QualitySample sample = sampleService.getById(sampleId);
                 dto.setSampleName(sample.getSampleName());
            }
            //查询任务
            String taskId = report.getTaskId();
            if(StringUtils.isNotBlank(taskId)){
                QualityTask task = taskService.getById(taskId);
                dto.setDelegateUnit(task.getDelegateUnit());
                dto.setTaskIssuedBy(task.getTaskIssuedBy());
                dto.setTaskIssuedDate(task.getTaskIssuedDate());

            }

            //查询校验能力
            String checkAbilityID = report.getCheckAbilityIDs();
            String checkAbilityName="";
            String[] ids = Tools.str2StrArray(checkAbilityID);
            for(int j=0;j<ids.length;j++){
                  String id = ids[j];
                  QualityCheckAbility checkAbility = checkAbilityService.getById(id);
                if(checkAbility!=null){
                    checkAbilityName += checkAbility.getSpecificationCName()+",";
                }
            }
            dto.setCheckAbilityName(checkAbilityName.substring(0,checkAbilityName.length()-1));
            dtolist.add(dto);

        }
        qualityReportDtotListPage.setCount(dtolist.size());
        qualityReportDtotListPage.setData(dtolist);
        return qualityReportDtotListPage;
    }

    @Override
    public boolean customSaveCalculate(String attid,String reportId,HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isNotBlank(attid)){
           QualityAttachment attachment =  qualityAttachmentService.getById(attid);
            String path = attachment.getPath();
            byte[] bytes = fastDFSClient.download(path);

            //转换字节流
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ByteArrayOutputStream byteArrayOutputStream = null;
            InputStream in = null;
            Workbook workbook = null;
            try {
                workbook = new XSSFWorkbook(inputStream);
                //创建一个新的sheet
                Sheet  existSheet = workbook.getSheet("委托单与样品信息");
                if(existSheet!=null){
                    int index = workbook.getSheetIndex("委托单与样品信息");
                    workbook.removeSheetAt(index);
                }

                Sheet sheet =  workbook.createSheet("委托单与样品信息");


                //拿到委托单的数据，然后写入
                QualityReport report = this.getById(reportId);

                String agreementNo = report.getAgreementNo();
                //根据委托编号获取委托单内容
                QualityAssignmentStatementDto statementDto =
                        qualityAssignmentStatementService.getByAgreementNo(agreementNo);

                //获取样品信息
                QualitySample sample = sampleService.getById(report.getSampleId());


                //单位名称
                Row  row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                //设置单位名称
                cell.setCellValue("单位名称：");
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(statementDto.getUnitName());

                //单位地址
                Row row2 = sheet.createRow(1);
                Cell rowCell = row2.createCell(0);
                rowCell.setCellValue("单位地址：");
                Cell rowCellOne= row.createCell(1);
                rowCellOne.setCellValue(statementDto.getAddress());

                //设备名称
                Row row3 = sheet.createRow(2);
                Cell row3Cell = row3.createCell(0);
                row3Cell.setCellValue("设备名称：");
                Cell row3CellOne= row3.createCell(1);
                row3CellOne.setCellValue(sample.getSampleName());

                //设备编号
                Row row4 = sheet.createRow(3);
                Cell row4Cell = row4.createCell(0);
                row4Cell.setCellValue("设备编号：");
                Cell row4CellOne= row4.createCell(1);
                row4CellOne.setCellValue(sample.getSampleModel());

                //制造单位
                Row row5 = sheet.createRow(4);
                Cell row5Cell = row5.createCell(0);
                row5Cell.setCellValue("制造单位：");
                Cell row5CellOne= row5.createCell(1);
                row5CellOne.setCellValue(sample.getManufacturer());


                //转化字节流
                byteArrayOutputStream = new ByteArrayOutputStream();
                workbook.write(byteArrayOutputStream);

                //临时的把字节流转换成输入流，在写回FASTDFS
                in = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());


                String fileName = attachment.getFileName();
                String fianlpath = fastDFSClient.upload(in, fileName,null);
                attachment.setPath(fianlpath);
                qualityAttachmentService.saveOrUpdate(attachment);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                // 关闭流
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    if (in != null) {
                        in.close();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean customSaveReport(QualityReport report,HttpServletRequest request,HttpServletResponse response) {
        boolean result = true;
        try {
            this.saveOrUpdate(report);
            this.customSaveCalculate(report.getCalculateTemplateId(), report.getId(), request, response);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }


    /**
     * 读取计算模板的内容，嵌套打印模板
     *
     * @param reportId
     * @param request
     * @param response
     */
    public XWPFDocument genReport(String reportId,HttpServletRequest request, HttpServletResponse response){
        XWPFDocument doc = null;
        QualityReport report = this.getById(reportId);
        String calumniateId = report.getCalculateTemplateId();
        String printId = report.getPrintTemplateId();
        //如果是姜主任的模板
        QualityAttachment attachment =  qualityAttachmentService.getById(calumniateId);
        HashMap<String, ReportIndexDto> readDataMap = null;
        if(report.getCalculateTemplateName().equals("姜主任模板")){
            readDataMap = someTemplate(reportId,attachment);
        }
        if(readDataMap!=null && readDataMap.size()>0){
            QualityAttachment printTemplate =  qualityAttachmentService.getById(printId);
            try {
                doc = writePrintTemplate(readDataMap,printTemplate,response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return doc;
    }

    //转换成docx需要的格式
    public List<ReportDto> ConversionReport(HashMap<String, ReportIndexDto> readDataMap){
        List<ReportDto> reprotDtoList = new LinkedList<>();
        ReportDto leavesSpace  = new ReportDto();
        readDataMap.forEach((k,v)->{
            Map<String, List<ReportDto>> reportVlaue =   v.getSheetContent();
            //初始化对象
            ReportDto reportDto = new ReportDto();
            reportDto.setNumber(v.getIndexNumber());
            reportDto.setProjectContent(v.getIndexName());
            reportDto.setUnit(" ");
            reportDto.setResult(" ");
            reprotDtoList.add(reportDto);
            //开始进行循环添加
            reportVlaue.forEach((key,list)->{
                list.stream().forEach(dto ->{
                    ReportDto leaves = new ReportDto();
                    leaves.setNumber(dto.getNumber());
                    if(dto.getSquareFlag() == true){
                        leaves.setDisSquareContent(dto.getDisSquareContent());
                        leaves.setSquare(dto.getSquare());
                        leaves.setSquareFlag(true);
                    }else{
                        leaves.setProjectContent(dto.getProjectContent());
                        leaves.setSquareFlag(false);
                    }
                    leaves.setUnit(dto.getUnit());
                    leaves.setResult(dto.getResult());

                    reprotDtoList.add(leaves);
                    //这里还要增加一条空行
                    if(StringUtils.isNotBlank(leaves.getResult())){
                        reprotDtoList.add(leavesSpace);
                    }

                });

            });


        });

        return reprotDtoList;
    }



    //根据读取内容写入报告模板
    public XWPFDocument writePrintTemplate(HashMap<String, ReportIndexDto> readDataMap,
                                   QualityAttachment printTemplate,HttpServletResponse response) throws IOException {
        String path = printTemplate.getPath();
        byte[] bytes = fastDFSClient.download(path);
        File smImg = new File(File.listRoots()[0],   "TempTemplate/" + path);
        if (!smImg.getParentFile().exists()) {
            smImg.getParentFile().mkdirs();
        }

        try {
            //寫入文件
            FileUtils.writeByteArrayToFile(smImg,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //这里渲染模板数据,重新组装数据
        List<ReportDto> resultDto = this.ConversionReport(readDataMap);
        //回填数据，基于easypoi
        /*map.put("reportdto", resultDto);*/

        XWPFDocument doc = null;
        try {
              doc = WordExportUtil.exportWord07(
                    smImg.getPath(), map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //这里需要重新处理表格的数据,目的是为了处理最后的表格
        String name = DateTime.now().toLocalDate()+"report.docx";
        File tempReport = new File(File.listRoots()[0],   "TempTemplate/report/" + name);
        if (!tempReport.getParentFile().exists()) {
            tempReport.getParentFile().mkdirs();
        }

        FileOutputStream out = new FileOutputStream(tempReport);

        doc.write(out);

        OPCPackage opcPackage = null;//包含所有POI OOXML文档类的通用功能，打开一个文件包。
        try {
            //这个地方必须是绝对路径，相对路径无法获取到
            opcPackage = POIXMLDocument.openPackage(tempReport.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        XWPFDocument document = null;//使用XWPF组件XWPFDocument类获取文档内容
        try {
            document = new XWPFDocument(opcPackage);
            // 表格和段落是完全分开的，
            //这是最后一个table，代表的标准器关联的内容
            List<XWPFTable> xwpfTables = document.getTables();
            PoiWordUtils poiWordUtils = new PoiWordUtils();

            //这下面的逻辑应该是封装标准器的,看看标准器在谁身上。
          /*  if(xwpfTables!=null && xwpfTables.size()>0){
                XWPFTable xwpfTable = xwpfTables.get(xwpfTables.size()-1);
                //设定表格的边框


                //插入多少行数据由封装的数据决定
                for(int i=0;i<resultDto.size()-1;i++){
                    XWPFTableRow firstRow = xwpfTable.insertNewTableRow(i+1);
                    ReportDto reportDto = resultDto.get(i);
                    XWPFTableCell firstCell=firstRow.addNewTableCell();
                    XWPFParagraph firstPara = firstCell.getParagraphs().get(0);
                    firstPara.setBorderBottom(Borders.NONE);
                    firstPara.setBorderRight(Borders.SINGLE);
                    firstCell.setText(reportDto.getNumber());

                    XWPFTableCell secondCell=firstRow.addNewTableCell();

                    if(reportDto.getSquareFlag()==true){
                        XWPFParagraph para = secondCell.getParagraphs().get(0);
                        para.setBorderLeft(Borders.NONE);
                        para.setBorderRight(Borders.BALLOONS_3_COLORS);
                        XWPFRun rh = para.createRun();
                        rh.setText(reportDto.getDisSquareContent());
                        XWPFRun rh1 = para.createRun();
                        rh1.setText(reportDto.getSquare());
                        rh1.setTextPosition(4);
                    }else{
                        secondCell.setText(reportDto.getProjectContent());
                    }

                    XWPFTableCell thirdCell=firstRow.addNewTableCell();
                    XWPFParagraph thirdPara = thirdCell.getParagraphs().get(0);
                    thirdPara.setBorderBottom(Borders.NONE);
                    thirdCell.setText(reportDto.getUnit());

                    XWPFTableCell fourthCell=firstRow.addNewTableCell();
                    XWPFParagraph fourthPara = fourthCell.getParagraphs().get(0);
                    fourthPara.setBorderBottom(Borders.NONE);
                    fourthCell.setText(reportDto.getResult());
                }

                //设置单元格的内容
                //获取最后一行的tableCells
                int lastRow = xwpfTable.getRows().size();
                List<XWPFTableCell> tableCells=   xwpfTable.getRow(lastRow-1).getTableCells();
                for(XWPFTableCell cell :tableCells){
                    XWPFParagraph para = cell.getParagraphs().get(0);
                    para.setBorderLeft(Borders.NONE);
                    XWPFRun rh = para.createRun();
                    rh.setText("test");
                    XWPFRun rh1 = para.createRun();
                    rh1.setText("-1");
                    rh1.setTextPosition(4);

                }
            }*/

            //尝试createTable看看差距多少
            XWPFTable careateTableTest = document.createTable(1, 4);

            //设定表格的边框
            CTTblPr tblPr = poiWordUtils.getTableCTTblPr(careateTableTest);
            CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders()
                    : tblPr.addNewTblBorders();

            //设置水平方向无线条
            CTBorder hBorder=borders.isSetInsideH()? borders.getInsideH(): borders.addNewInsideH();
            hBorder.setVal(STBorder.Enum.forString("none"));
            hBorder.setSz(new BigInteger("1"));
            hBorder.setColor("0000FF");

            CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
            CTJc cTJc=tblPr.addNewJc();
            cTJc.setVal(STJc.Enum.forString("center"));
            //模板中是17厘米 大约等于9650
            tblWidth.setW(new BigInteger("9650"));
            tblWidth.setType(STTblWidth.DXA);

            XWPFTableRow firstRowOne = careateTableTest.getRow(0);

            //创建表头
            createTabelHeard(poiWordUtils, firstRowOne);
            //循环创建数据
            for(int i=0;i<resultDto.size()-1;i++){
                XWPFTableRow firstRow = careateTableTest.insertNewTableRow(i+1);
                ReportDto reportDto = resultDto.get(i);
                XWPFTableCell firstCell=firstRow.addNewTableCell();
                XWPFParagraph firstPara = firstCell.getParagraphs().get(0);
                XWPFRun xwpfRun = firstPara.createRun();

                //设置单元格对齐，看看行不行
                poiWordUtils.setCellText(firstCell,reportDto.getNumber(),"",850,
                        firstPara,xwpfRun,"楷体","","22",
                        "0",STVerticalJc.BOTH,STJc.BOTH);
                /*firstCell.setText(reportDto.getNumber());*/

                XWPFTableCell secondCell=firstRow.addNewTableCell();

                if(reportDto.getSquareFlag()==true){
                    XWPFParagraph para = secondCell.getParagraphs().get(0);
                    XWPFRun rh = para.createRun();

                    poiWordUtils.setCellText(secondCell,reportDto.getDisSquareContent(),"",3500,
                            para,rh,"楷体","","22",
                            "0",STVerticalJc.BOTH,STJc.LEFT);

                    XWPFRun rh1 = para.createRun();
                    poiWordUtils.setCellText(secondCell,reportDto.getSquare(),"",3500,
                            para,rh1,"楷体","","22",
                            "0",STVerticalJc.BOTH,STJc.LEFT);

                    rh1.setTextPosition(4);
                }else{
                    XWPFParagraph para = secondCell.getParagraphs().get(0);
                    XWPFRun rh2 = para.createRun();
                    poiWordUtils.setCellText(secondCell,reportDto.getProjectContent(),"",3500,
                            para,rh2,"楷体","","22",
                            "0",STVerticalJc.BOTH,STJc.LEFT);


                }

                XWPFTableCell thirdCell=firstRow.addNewTableCell();
                if(StringUtils.isNotBlank(reportDto.getUnit())){
                    XWPFParagraph thirdPara = thirdCell.getParagraphs().get(0);
                    XWPFRun rh2 = thirdPara.createRun();

                    poiWordUtils.setCellText(thirdCell,reportDto.getUnit(),"",1130,
                            thirdPara,rh2,"楷体","","22",
                            "0",STVerticalJc.CENTER,STJc.LEFT);
                }else{
                    XWPFParagraph thirdPara = thirdCell.getParagraphs().get(0);
                    XWPFRun rh2 = thirdPara.createRun();
                    poiWordUtils.setCellText(thirdCell,"","",1128,
                            thirdPara,rh2,"楷体","","22",
                            "0",STVerticalJc.CENTER,STJc.LEFT);
                }



                XWPFTableCell fourthCell=firstRow.addNewTableCell();

                if(StringUtils.isNotBlank(reportDto.getResult())){
                    XWPFParagraph fourthPara = fourthCell.getParagraphs().get(0);
                    XWPFRun rh3 = fourthPara.createRun();
                    XWPFRun rh3_1 = fourthPara.createRun();
                    String val = reportDto.getResult();
                    if(val.indexOf("E")>0){
                        String deep[] = poiWordUtils.replaceDeep(val);
                        poiWordUtils.setCellText(fourthCell,deep[0],"",4140,
                                fourthPara,rh3,"楷体","","22",
                                "0",STVerticalJc.CENTER,STJc.LEFT);

                        poiWordUtils.setCellText(fourthCell,deep[1],"",4140,
                                fourthPara,rh3_1,"楷体","","22",
                                "0",STVerticalJc.CENTER,STJc.LEFT);
                        rh3_1.setTextPosition(4);

                    }else{
                        poiWordUtils.setCellText(fourthCell,reportDto.getResult(),"",4140,
                                fourthPara,rh3,"楷体","","22",
                                "0",STVerticalJc.CENTER,STJc.LEFT);
                    }
                }else{
                    XWPFParagraph fourthPara = fourthCell.getParagraphs().get(0);
                    XWPFRun rh3 = fourthPara.createRun();
                    poiWordUtils.setCellText(fourthCell,"","",4141,
                            fourthPara,rh3,"楷体","","22",
                            "0",STVerticalJc.CENTER,STJc.LEFT);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
        return document;
    }

    //抽取出调用表头的方法
    private void createTabelHeard(PoiWordUtils poiWordUtils, XWPFTableRow firstRow) {

        XWPFTableCell firstCell = firstRow.getTableCells().get(0);
        XWPFTableCell secondCell = firstRow.getTableCells().get(1);
        XWPFTableCell thirdCell = firstRow.getTableCells().get(2);
        XWPFTableCell fourtdCell = firstRow.getTableCells().get(3);

        XWPFParagraph paragraph = firstCell.getParagraphs().get(0);
        XWPFRun xwpfRun = paragraph.createRun();
        //fontsize28 代表14
        poiWordUtils.setCellText(firstCell,"序号","",850,
                                paragraph,xwpfRun,"宋体(正文)","",
                "28","0",STVerticalJc.CENTER,STJc.CENTER);


        XWPFParagraph paragraph2= secondCell.getParagraphs().get(0);
        XWPFRun xwpfRun2 = paragraph2.createRun();
        poiWordUtils.setCellText(secondCell,"校 准 技 术 项 目 要 求","",3500,
                paragraph2,xwpfRun2,"宋体(正文)","","28","0",
                STVerticalJc.CENTER,STJc.CENTER);


        XWPFParagraph paragraph3= thirdCell.getParagraphs().get(0);
        XWPFRun xwpfRun3 = paragraph3.createRun();
        poiWordUtils.setCellText(thirdCell,"计量  单位","",1130,
                paragraph3,xwpfRun3,"宋体(正文)","","28","5",
                STVerticalJc.CENTER,STJc.CENTER);

        XWPFParagraph paragraph4= fourtdCell.getParagraphs().get(0);
        XWPFRun xwpfRun4 = paragraph4.createRun();
        poiWordUtils.setCellText(fourtdCell,"实 测 结 果","",4140,
                paragraph4,xwpfRun4,"宋体(正文)","","28","0",
                STVerticalJc.CENTER,STJc.CENTER);
    }


    private HashMap<String, ReportIndexDto> someTemplate(String reportId, QualityAttachment attachment) {
        //先读取数据
        HashMap<String, ReportIndexDto> reportDtoHashMap = new HashMap<>();
        String path = attachment.getPath();

        byte[] bytes = fastDFSClient.download(path);

        //转换字节流
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream in = null;
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
            //获取索引列表的sheet
            Sheet indexsheet = workbook.getSheetAt(0);

            //获得总行数
            int rowNum=indexsheet.getLastRowNum();
            //开始循环总行数 读取内容
            for(int i=1;i<=rowNum;i++){
                int temp = i;
                ReportIndexDto reportIndexDto = new ReportIndexDto();
                Row row = indexsheet.getRow(temp);

                String parentName =  ExcelUtil.getCellValue(row.getCell(1),workbook);;
                reportIndexDto.setIndexName(parentName);
                String index = ExcelUtil.getCellValue(row.getCell(2),workbook);
                reportIndexDto.setIndexNumber(index);
                List<ReportDto> reportDtoList = new LinkedList<>();
                HashMap<String, List<ReportDto>> reportDtoMap = new HashMap<>();

                reportDtoMap.put(temp+"",reportDtoList);
                reportIndexDto.setSheetContent(reportDtoMap);
                reportDtoHashMap.put(temp+"", reportIndexDto);
            }


            //根据索引封装map
            Workbook finalWorkbook = workbook;
            reportDtoHashMap.keySet().stream().forEach(key->{
                ReportIndexDto reportIndexDto = reportDtoHashMap.get(key);
                Map<String,List<ReportDto>> sheetReportList =  reportIndexDto.getSheetContent();
                List<ReportDto> sheetContentList = sheetReportList.get(key);
                Sheet someSheet = finalWorkbook.getSheetAt(Integer.parseInt(reportIndexDto.getIndexNumber()));
                int tempIndex = 20;
                int lastRowNumber = someSheet.getLastRowNum();

                for(int i=tempIndex-1;i<=lastRowNumber;i++){
                    Row row =someSheet.getRow(i);
                    //如果行中没有内容，则不处理
                    if(row!=null){
                        Cell flagCell = row.getCell(4);
                        //TODO 换值没有换完
                        String value = ExcelUtil.getCellValue(flagCell,finalWorkbook);
                        if(value.equals("1")){
                            ReportDto reportDto = new ReportDto();
                            String number = ExcelUtil.getCellValue(row.getCell(0),finalWorkbook);

                            reportDto.setNumber(number);

                            String projectContent = ExcelUtil.getCellValue(row.getCell(1),finalWorkbook);
                            //这里需要判断是否是否有特殊符号
                            int Mindex = projectContent.indexOf("^");
                            if(Mindex>0){
                                String square  = projectContent.substring(Mindex+1, projectContent.length());
                                String disContent  = projectContent.substring(0,Mindex);
                                reportDto.setSquareFlag(true);
                                reportDto.setSquare(square);
                                reportDto.setDisSquareContent(disContent);
                            }else{
                                reportDto.setProjectContent(projectContent);
                                reportDto.setSquareFlag(false);
                            }
                            String unit = ExcelUtil.getCellValue(row.getCell(2),finalWorkbook);
                            reportDto.setUnit(unit);
                            String result = ExcelUtil.getCellValue(row.getCell(3),finalWorkbook);
                            reportDto.setResult(result);
                            sheetContentList.add(reportDto);
                    }
                    }
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭流
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reportDtoHashMap;
    }

}
