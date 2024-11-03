package com.szabolcs.kovacs.gscf.service.base;

import com.szabolcs.kovacs.gscf.dto.RoomDTO;
import com.szabolcs.kovacs.gscf.exception.GscfException.GscfException;

import java.util.List;

public interface RoomFinder {

    int findSmallestSideWall(int wall1, int wall2, int wall3) throws GscfException;
    List<RoomDTO> findDuplicateRooms(List<RoomDTO> rooms) throws GscfException;

    List<RoomDTO> findCubicRooms(List<RoomDTO> rooms) throws GscfException;
}
