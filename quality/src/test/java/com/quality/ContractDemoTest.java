/**
 * Copyright 2013-2015 JueYue (qrb.jueyue@gmail.com)
 *   
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quality;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ContractDemoTest {

    @Test
    public void testHasTotal() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curTime = format.format(new Date());

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("rowNumber", "1");
        map1.put("spareCode", "123");
        map1.put("spareDesc", "这是备注");

        map1.put("mytest", "这是图号111");
        map1.put("name", "我是名字");


        mapList.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("rowNumber", "2");
        map2.put("spareCode", "123");
        map2.put("spareDesc", "我要展示出来");

        map2.put("mytest", "这是图号2222222");
        map2.put("name", "我是名字222");

        mapList.add(map2);

        map.put("taxlist", mapList);

        map.put("totalpreyear", "2660");
        map.put("totalthisyear", "3400");


        Map<String, String> total = new HashMap<String, String>();
        total.put("orderId", "2660");
        total.put("orderCreateTime", "3400");
        total.put("supplyName", "3410");
        total.put("supplyLinkman", "李四");
        total.put("amount", "2301");
        map.put("order", total);
        try {
            XWPFDocument doc = WordExportUtil
                .exportWord07("word/contractDemo.docx", map);
            FileOutputStream fos = new FileOutputStream("D:/ContractDemo.docx");
            doc.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
