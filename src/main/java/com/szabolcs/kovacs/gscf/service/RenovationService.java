package com.szabolcs.kovacs.gscf.service;

import com.szabolcs.kovacs.gscf.dto.ImportFileResultDTO;
import com.szabolcs.kovacs.gscf.dto.RoomDTO;
import com.szabolcs.kovacs.gscf.exception.GscfException.GscfException;
import com.szabolcs.kovacs.gscf.service.base.RoomFinder;
import com.szabolcs.kovacs.gscf.service.base.SquareCalculator;
import com.szabolcs.kovacs.gscf.service.base.SurfaceCalculator;
import com.szabolcs.kovacs.gscf.service.base.WallpaperCalculator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RenovationService implements
        SquareCalculator,
        SurfaceCalculator,
        WallpaperCalculator,
        RoomFinder {

    private final int SQUARE_ROOM_WALL_MULTIPLIER = 2;


    public ImportFileResultDTO measurement(List<String> dimensions) {

        List<RoomDTO> rooms = createRoomDTOs(dimensions);

        return new ImportFileResultDTO(
                calculateSumOfTotalWallpaper(rooms),
                findCubicRooms(rooms),
                findDuplicateRooms(rooms));
    }

    public List<RoomDTO> createRoomDTOs(List<String> dimensions) {

        List<RoomDTO> rooms = new ArrayList<>();

        dimensions.forEach(d -> {
            String[] roomParameters = d.split("x");
            if (roomParameters.length == 3) {
                int length = Integer.parseInt(roomParameters[0]);
                int width = Integer.parseInt(roomParameters[1]);
                int height = Integer.parseInt(roomParameters[2]);

                rooms.add(RoomDTO.builder()
                        .length(length)
                        .width(width)
                        .height(height)
                        .build());
            }
        });
        return rooms;
    }

    @Override
    public int calculateTotalRoomArea(RoomDTO room) {
        if (room.getLength() == 0 || room.getWidth() == 0 || room.getHeight() == 0) {
            return 0;
        }
        long length = Math.abs((long) room.getLength());
        long width = Math.abs((long) room.getWidth());
        long height = Math.abs((long) room.getHeight());

        long totalArea = SQUARE_ROOM_WALL_MULTIPLIER *
                (length * width + length * height + height * width);

        if (totalArea > Integer.MAX_VALUE) {
            return 0;
        }
        return (int) totalArea;
    }

    @Override
    public int calculateWallpaperForRoom(RoomDTO room) {
        int totalSurface = calculateTotalRoomArea(room);
        int extraWallPaper = findSmallestSideWall(
                (room.getLength() * room.getWidth()),
                (room.getWidth() * room.getHeight()),
                (room.getLength() * room.getHeight())
        );
        return totalSurface + extraWallPaper;
    }

    @Override
    public int calculateSumOfTotalWallpaper(List<RoomDTO> rooms) {
        return rooms.stream()
                .mapToInt(this::calculateWallpaperForRoom)
                .sum();
    }

    @Override
    public int findSmallestSideWall(int wall1, int wall2, int wall3) {
        return Math.min(wall1, Math.min(wall2, wall3));
    }
   @Override
    public List<RoomDTO> findCubicRooms(List<RoomDTO> rooms) {
        return rooms.stream()
                .filter(SquareCalculator::isCubic)
                .sorted(Comparator.comparingInt(RoomDTO::getLength).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> findDuplicateRooms(List<RoomDTO> rooms) {
        Map<RoomDTO, Integer> roomCounts = new HashMap<>();

        rooms.stream().forEach(r -> {
            roomCounts.put(r, roomCounts.getOrDefault(r, 0) + 1);
        });
        List<RoomDTO> duplicateRoomList = roomCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return duplicateRoomList;
    }
}
