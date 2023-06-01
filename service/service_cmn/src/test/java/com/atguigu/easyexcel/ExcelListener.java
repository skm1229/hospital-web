package com.atguigu.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ExcelListener extends AnalysisEventListener<UserData> {
    //一行一行读取excel内容  从第二行开始读
    @Override
    public void invoke(UserData userData, AnalysisContext analysisContext) {
        log.info("userData: {}",userData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("表头信息: {}",headMap);
    }

    //读取之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
