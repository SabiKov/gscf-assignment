package com.szabolcs.kovacs.gscf.controller;

import com.szabolcs.kovacs.gscf.dto.ImportFileResultDTO;
import com.szabolcs.kovacs.gscf.exception.GscfException.GscfException;
import com.szabolcs.kovacs.gscf.service.ImportFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/file/upload")
public class FileUploadController {

    private ImportFileService importFileService;

    @Autowired
    public FileUploadController(ImportFileService importFileService) {
        this.importFileService = importFileService;
    }

    @Operation(summary = "Upload a file",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "File to upload",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary"))
            ))
    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportFileResultDTO> uploadFile(
            @RequestParam("File") MultipartFile file) throws IOException, GscfException {

        log.debug("Received file: " + (file != null ? file.getOriginalFilename() : "No file"));

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        ImportFileResultDTO result = importFileService.readFile(file.getInputStream());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getHealthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
