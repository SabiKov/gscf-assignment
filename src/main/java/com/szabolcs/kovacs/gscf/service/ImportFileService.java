package com.szabolcs.kovacs.gscf.service;

import com.szabolcs.kovacs.gscf.dto.ImportFileResultDTO;
import com.szabolcs.kovacs.gscf.exception.GscfException.GscfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ImportFileService {

    private final String regex = "[1-9]\\d*x[1-9]\\d*x[1-9]\\d*";

    private RenovationService renovationService;

    @Autowired
    public ImportFileService(RenovationService renovationService) {
        this.renovationService = renovationService;
    }

    public ImportFileResultDTO readFile(InputStream file) throws GscfException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (isValidInputLine(line)) {
                    lines.add(line);  // Add each line to the list (or process it as needed)
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return renovationService.measurement(lines);
    }

    private boolean isValidInputLine(String inputLine) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(inputLine).matches();
    }
}
