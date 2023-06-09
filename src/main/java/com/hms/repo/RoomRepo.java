package com.hms.repo;

import com.hms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room,Integer> {

    List<Room> findByHostelName(String hostelName);

    Room findByRoomNoAndHostelName(int roomNo,String hostelName);

}
