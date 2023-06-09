package com.hms.service;

import com.hms.entity.Room;
import com.hms.repo.RoomRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepo roomRepo;

    @Override
    public List<Room> findByHostelName(String hostelName) {
        return this.roomRepo.findByHostelName(hostelName);
    }

    @Override
    public Room updateRoomByRoomNo(int roomNo, int isEmpty,String hostelName) {
        Room room = roomRepo.findByRoomNoAndHostelName(roomNo,hostelName);
        room.setIsEmpty(isEmpty);
        return roomRepo.save(room);
    }

    @Override
    public Room findByRoomAndHostel(int roomNo, String hostelName) {
        return this.roomRepo.findByRoomNoAndHostelName(roomNo, hostelName);
    }
}
