package com.szabolcs.kovacs.gscf.service.base;

import com.szabolcs.kovacs.gscf.dto.RoomDTO;
import com.szabolcs.kovacs.gscf.exception.GscfException.GscfException;

import java.util.List;

public interface WallpaperCalculator {

    int calculateWallpaperForRoom(RoomDTO room) throws GscfException;

    int calculateSumOfTotalWallpaper(List<RoomDTO> rooms) throws GscfException;
}
