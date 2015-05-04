package com.gdpost.utils.FileHandler.XlsxFileStreamHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SheetDatasHandler extends RowMapper {
    private int bufRowSize, curSheetIndex = -1;
    private List<List<Map<Integer, Object>>> sheetDatas = new ArrayList<List<Map<Integer, Object>>>();
    private List<Map<Integer, Object>> sheetData;

    public List<List<Map<Integer, Object>>> getSheetDatas() {
        return sheetDatas;
    }
    
    public List<Map<Integer, Object>> getSheetData(int sheetIndex) {
        return sheetDatas.get(sheetIndex);
    }

    SheetDatasHandler(int bufRowSize) {
        this.bufRowSize = bufRowSize;
    }

    @Override
    void mapRow(int sheetIndex, int rowIndex, Map<Integer, Object> row) {
        if (curSheetIndex != sheetIndex) {
            sheetData = new ArrayList<Map<Integer, Object>>(sheetIndex == 0 ? bufRowSize : sheetData.size() / 2);
            sheetDatas.add(sheetData);
            curSheetIndex = sheetIndex;
        }
        
        sheetData.add(row);
    }
}