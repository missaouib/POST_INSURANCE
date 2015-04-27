package com.gdpost.utils.FileHandler.XlsxFileStreamHandler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XlsxStreamHandler {
    public static SheetDatasHandler read(Path file) throws Exception {
        SheetDatasHandler handler = new SheetDatasHandler((int) (Files.size(file) / 50));
        read(file, handler);
        return handler;
    }
    
    public static void read(Path file, RowMapper mapper) throws Exception {
    	int iSize = 1024*1024;
        final long size = Files.size(file);  
        try (InputStream in = new BufferedInputStream(new FileInputStream(file.toFile()), size > iSize ? iSize : (int) size)) { 
            read(in, mapper);
        }
    }

    public static void read(InputStream in, RowMapper mapper) throws Exception {
        XSSFReader reader = new XSSFReader(OPCPackage.open(in));
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

        mapper.setSharedStringsTable(reader.getSharedStringsTable());
        parser.setContentHandler(mapper);
        XSSFReader.SheetIterator sheetIter = (XSSFReader.SheetIterator)reader.getSheetsData();
        String strSheetName = "";
        
        while (sheetIter.hasNext()) {
        	try(InputStream stream = sheetIter.next()) {               
        		strSheetName = sheetIter.getSheetName();
        		mapper.getSheetName().add(strSheetName);
        		parser.parse(new InputSource(stream));
        	}
        }

        //for (Iterator<InputStream> iter = reader.getSheetsData(); iter.hasNext();) {
        //    try (InputStream sheetIn = iter.next()) {
        //        parser.parse(new InputSource(sheetIn));
        //    }
        //}
    }
}
