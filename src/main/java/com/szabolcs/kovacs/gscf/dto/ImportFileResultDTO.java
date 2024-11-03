package com.szabolcs.kovacs.gscf.dto;

import lombok.*;

import java.util.List;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportFileResultDTO {

    private int sumOfTotalWallpaper;
    private List<RoomDTO> cubicRooms;
    private List<RoomDTO> duplicateRooms;
}
