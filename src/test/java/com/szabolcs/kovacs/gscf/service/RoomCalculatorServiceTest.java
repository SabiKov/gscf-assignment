package com.szabolcs.kovacs.gscf.service;

import com.szabolcs.kovacs.gscf.dto.RoomDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RenovationServiceTest {

    private RenovationService renovationService;

    @BeforeEach
    public void setUp() {
        renovationService = new RenovationService();
    }
    @Test
    void shouldCalculateTotalRoomArea_WhenDimensionsArePositive() {
        RoomDTO roomDTO = RoomDTO.builder()
                .length(1)
                .width(2)
                .height(3)
                .build();

        int expected = 22;
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));

        roomDTO = RoomDTO.builder()
                .length(1)
                .width(1)
                .height(5)
                .build();
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));
    }

    @Test
    void shouldCalculateTotalRoomArea_WhenDimensionsAreNegativeAndPositive() {
        RoomDTO roomDTO = RoomDTO.builder()
                .length(-1)
                .width(2)
                .height(3)
                .build();

        int expected = 22;
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));

        roomDTO = RoomDTO.builder()
                .length(1)
                .width(1)
                .height(-5)
                .build();
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));
    }

    @Test
    void shouldCalculateTotalRoomArea_WhenDimensionsAreNegative() {
        RoomDTO roomDTO = RoomDTO.builder()
                .length(-1)
                .width(-2)
                .height(-3)
                .build();

        int expected = 22;
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));

        roomDTO = RoomDTO.builder()
                .length(-1)
                .width(-1)
                .height(-5)
                .build();
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));
    }

    @Test
    void shouldCalculateTotalRoomArea_WhenDimensionsAreZeroAndNegative() {
        RoomDTO roomDTO = RoomDTO.builder()
                .length(0)
                .width(2)
                .height(3)
                .build();

        int expected = 0;
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));

        roomDTO = RoomDTO.builder()
                .length(1)
                .width(0)
                .height(-5)
                .build();
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));
    }


    @Test
    void shouldCalculateTotalRoomArea_WhenDimensionsAreHugeAndPositive() {
        RoomDTO roomDTO = RoomDTO.builder()
                .length(Integer.MAX_VALUE)
                .width(2)
                .height(3)
                .build();

        int expected = 0;
        assertEquals(expected, renovationService.calculateTotalRoomArea(roomDTO));
    }

    @Test
    void shouldCalculateWallpaperForRoom_WhenRoomDTOIsValid() {
        RoomDTO roomDTO = RoomDTO.builder()
                .length(1)
                .width(2)
                .height(3)
                .build();
        int extraWallpaper = renovationService.findSmallestSideWall(
                roomDTO.getLength() * roomDTO.getWidth(),
                roomDTO.getWidth() * roomDTO.getHeight(),
                roomDTO.getLength() * roomDTO.getHeight());
        assertEquals(2, extraWallpaper);
        int expected = (2 * 2) + (2 * 2 * 3) + (2 * 3) + extraWallpaper;
        assertEquals(expected, renovationService.calculateWallpaperForRoom(roomDTO));
    }

    @Test
    void shouldCalculateSumOfTotalWallpaper_WhenRoomDTOsAreValid() {
        List<RoomDTO> rooms = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(2)
                        .height(3)
                        .build()
        );
        int expected = 47;
        assertEquals(expected, renovationService.calculateSumOfTotalWallpaper(rooms));
    }

    @Test
    void shouldFindCubicRooms_WhenRoomDTOsAreValid() {
        List<RoomDTO> rooms = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(2)
                        .height(3)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(1)
                        .build()
        );
        List<RoomDTO> expected = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(1)
                        .build());
        assertEquals(1, renovationService.findCubicRooms(rooms).size());
        assertEquals(expected, renovationService.findCubicRooms(rooms));
    }

    @Test
    void shouldFindCubicRooms_WhenRoomDTOsNotContainCubic() {
        List<RoomDTO> rooms = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(2)
                        .height(3)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(8)
                        .height(1)
                        .build()
        );
        List<RoomDTO> expected = new ArrayList<>();
        assertEquals(0, renovationService.findCubicRooms(rooms).size());
        assertEquals(expected, renovationService.findCubicRooms(rooms));
    }

    @Test
    void shouldFindCubicRooms_WhenRoomDTOsContainsMultipleCubic() {
        List<RoomDTO> rooms = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(8)
                        .width(8)
                        .height(8)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(2)
                        .height(3)
                        .build(),
                RoomDTO.builder()
                        .length(5)
                        .width(5)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(1)
                        .build()
        );
        List<RoomDTO> expected = List.of(
                RoomDTO.builder()
                        .length(8)
                        .width(8)
                        .height(8)
                        .build(),
                RoomDTO.builder()
                        .length(5)
                        .width(5)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(1)
                        .build());
        assertEquals(3, renovationService.findCubicRooms(rooms).size());
        assertEquals(expected, renovationService.findCubicRooms(rooms));
    }

    @Test
    void shouldFindDuplicateRooms_WhenRoomDTOsNOTContainDuplication() {
        List<RoomDTO> rooms = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(8)
                        .width(8)
                        .height(8)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(6)
                        .build(),
                RoomDTO.builder()
                        .length(5)
                        .width(5)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(1)
                        .build()
        );
        List<RoomDTO> expected = new ArrayList<>();
        assertEquals(0, renovationService.findDuplicateRooms(rooms).size());
        assertEquals(expected, renovationService.findDuplicateRooms(rooms));
    }

    @Test
    void shouldFindDuplicateRooms_WhenRoomDTOsContainsTwoDuplication() {
        List<RoomDTO> rooms = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(8)
                        .width(8)
                        .height(8)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(5)
                        .width(5)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(1)
                        .build()
        );
        List<RoomDTO> expected = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build());
        assertEquals(1, renovationService.findDuplicateRooms(rooms).size());
        assertEquals(expected, renovationService.findDuplicateRooms(rooms));
    }

    @Test
    void shouldFindDuplicateRooms_WhenRoomDTOsContainsMultipleDuplication() {
        List<RoomDTO> rooms = List.of(
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(8)
                        .width(8)
                        .height(8)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(5)
                        .width(5)
                        .height(5)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(1)
                        .build(),
                RoomDTO.builder()
                        .length(8)
                        .width(8)
                        .height(8)
                        .build()
        );
        List<RoomDTO> expected = List.of(
                RoomDTO.builder()
                        .length(8)
                        .width(8)
                        .height(8)
                        .build(),
                RoomDTO.builder()
                        .length(1)
                        .width(1)
                        .height(5)
                        .build());
        assertEquals(2, renovationService.findDuplicateRooms(rooms).size());
        assertEquals(expected, renovationService.findDuplicateRooms(rooms));
    }
}