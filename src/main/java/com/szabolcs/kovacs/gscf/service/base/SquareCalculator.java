package com.szabolcs.kovacs.gscf.service.base;

import com.szabolcs.kovacs.gscf.dto.RoomDTO;

public interface SquareCalculator {

    static boolean isCubic(RoomDTO room) {
        return room.getLength() == room.getWidth() && room.getWidth() == room.getHeight();
    }
}
