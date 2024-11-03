package com.szabolcs.kovacs.gscf.service.base;

import com.szabolcs.kovacs.gscf.dto.RoomDTO;
import com.szabolcs.kovacs.gscf.exception.GscfException.GscfException;

public interface SurfaceCalculator {

    int calculateTotalRoomArea(RoomDTO room) throws GscfException;
}
