package com.hms.service;

import com.hms.entity.Room;

import java.util.List;

public interface RoomService {

    List<Room> findByHostelName(String hostelName);

    Room updateRoomByRoomNo(int roomNo,int isEmpty,String hostelName);

    Room findByRoomAndHostel(int roomNo,String hostelName);

}
