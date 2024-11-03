package com.szabolcs.kovacs.gscf.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class RoomDTO {
    @Min(value = 1, message = "Length of room minimum 1 foot at least")
    @Max(value = Integer.MAX_VALUE, message = "Length is too long")
    private int length;
    @Min(value = 1, message = "Width of room minimum 1 foot at least")
    @Max(value = Integer.MAX_VALUE, message = "Width is too long")
    private int width;
    @Min(value = 1, message = "Height of room minimum 1 foot at least")
    @Max(value = Integer.MAX_VALUE, message = "Height is too long")
    private int height;
}
