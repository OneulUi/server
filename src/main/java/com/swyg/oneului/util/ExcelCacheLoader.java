package com.swyg.oneului.util;

import com.swyg.oneului.model.AddressPosition;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Component
public class ExcelCacheLoader {
    private final Cache cache;

    public ExcelCacheLoader() {
        CacheManager cacheManager = CacheManager.getInstance();
        this.cache = cacheManager.getCache("addressCache");
    }

    public void extractExcelData() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/address.xlsx")) {
            ZipSecureFile.setMinInflateRatio(0);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // 첫 번째 행은 헤더이므로 스킵
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell keyCell = row.getCell(0);
                Cell nxCell = row.getCell(1);
                Cell nyCell = row.getCell(2);

                if (keyCell != null && nxCell != null && nyCell != null) {
                    String key = keyCell.getStringCellValue();
                    String nx = nxCell.getStringCellValue();
                    String ny = nyCell.getStringCellValue();

                    AddressPosition addressPosition = AddressPosition.builder()
                            .nx(nx)
                            .ny(ny)
                            .build();

                    Element element = new Element(key, addressPosition);
                    cache.put(element);
                }
            }
        }
    }

    public AddressPosition getPositionFromAddressCache(String key) {
        Element element = cache.get(key);
        if (element != null) {
            return (AddressPosition) element.getObjectValue();
        }
        return null;
    }
}
