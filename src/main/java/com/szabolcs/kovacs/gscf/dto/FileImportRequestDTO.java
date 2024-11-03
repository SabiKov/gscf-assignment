package com.szabolcs.kovacs.gscf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record FileImportRequestDTO(
        @Schema(type = "string", format = "binary", description = "File to be uploaded")
        @NotNull
        MultipartFile file
) {
}
