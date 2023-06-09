package com.hms;

import com.hms.entity.Room;
import com.hms.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HostelManagementApplication implements CommandLineRunner {

	@Autowired
	private RoomRepo roomRepo;

	public static void main(String[] args) {
		SpringApplication.run(HostelManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*int count=101;
		for(int i=0;i<100;i++){
			Room room  = new Room();
			room.setRoomNo(count);
			room.setIsEmpty(0);
			room.setHostelName("Vivekananda");
			Room save = roomRepo.save(room);
			count++;
		}

		count=101;
		for(int i=0;i<100;i++){
			Room room  = new Room();
			room.setRoomNo(count);
			room.setIsEmpty(0);
			room.setHostelName("APJ");
			Room save = roomRepo.save(room);
			count++;
		}

		count=101;
		for(int i=0;i<100;i++){
			Room room  = new Room();
			room.setRoomNo(count);
			room.setIsEmpty(0);
			room.setHostelName("JCBose");
			Room save = roomRepo.save(room);
			count++;
		}

		count=101;
		for(int i=0;i<100;i++){
			Room room  = new Room();
			room.setRoomNo(count);
			room.setIsEmpty(0);
			room.setHostelName("Sarojini");
			Room save = roomRepo.save(room);
			count++;
		}*/
	}
}
