package com.szabolcs.kovacs.gscf.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.szabolcs.kovacs.gscf.dto.ImportFileResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ImportFileServiceTest {

    @Mock
    private RenovationService renovationService;

    @InjectMocks
    private ImportFileService importFileService;

    @BeforeEach
    void setUp() {
        importFileService = new ImportFileService(renovationService);
    }

    @Test
    void shouldReadFile_WhenLinesContainsOnlyValidData() throws Exception {

        String fileContent = "12x34x56\n78x90x12\n1x2x3\n23x45x67";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());

        List<String> validLines = Arrays.asList("12x34x56", "78x90x12", "1x2x3", "23x45x67");
        ImportFileResultDTO expectedResult = new ImportFileResultDTO();

        when(renovationService.measurement(validLines)).thenReturn(expectedResult);

        ImportFileResultDTO result = importFileService.readFile(inputStream);

        assertEquals(expectedResult, result);
        verify(renovationService).measurement(validLines);
    }

    @Test
    void shouldReadFile_WhenLinesContainsValidAndInvalidData() throws Exception {

        String fileContent = "12x34x56\n78x90x12\ninvalidLine\n23x45x67";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());

        List<String> validLines = Arrays.asList("12x34x56", "78x90x12", "23x45x67");
        ImportFileResultDTO expectedResult = new ImportFileResultDTO();

        when(renovationService.measurement(validLines)).thenReturn(expectedResult);

        ImportFileResultDTO result = importFileService.readFile(inputStream);

        assertEquals(expectedResult, result);
        verify(renovationService).measurement(validLines);
    }

    @Test
    void shouldReadFile_WhenLinesContainsOnlyNoValidData() throws Exception {
        String fileContent = "invalidLine1\nanotherInvalidLine\nnotEvenClose";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());

        List<String> emptyList = List.of();
        ImportFileResultDTO expectedResult = new ImportFileResultDTO();

        when(renovationService.measurement(emptyList)).thenReturn(expectedResult);

        ImportFileResultDTO result = importFileService.readFile(inputStream);

        assertEquals(expectedResult, result);
        verify(renovationService).measurement(emptyList);
    }
}