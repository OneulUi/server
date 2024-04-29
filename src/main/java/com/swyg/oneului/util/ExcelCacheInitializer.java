package com.swyg.oneului.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExcelCacheInitializer {
    private final ExcelCacheLoader excelCacheLoader;

    @PostConstruct
    public void init() throws Exception {
        excelCacheLoader.extractExcelData();
    }
}