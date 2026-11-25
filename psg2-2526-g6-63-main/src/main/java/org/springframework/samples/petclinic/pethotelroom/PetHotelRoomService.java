package org.springframework.samples.petclinic.pethotelroom;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetHotelRoomService {

    private PetHotelRoomRepository roomRepository;

    @Autowired
    public PetHotelRoomService(PetHotelRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional(readOnly = true)
    public List<PetHotelRoom> findAll() throws DataAccessException {
        return roomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PetHotelRoom> findRoomsByUserId(int userId) throws DataAccessException {
        return roomRepository.findRoomsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public PetHotelRoom findRoomById(int roomId) throws DataAccessException {
        Optional<PetHotelRoom> room = roomRepository.findById(roomId);
        if(room.isPresent()) {
            return room.get();
        } else {
            return null;
        }
    }

    @Transactional
    public PetHotelRoom save(PetHotelRoom room) throws DataAccessException {
        roomRepository.save(room);
        return room;
    }

    @Transactional
    public PetHotelRoom update(PetHotelRoom room, int roomId) throws DataAccessException {
        PetHotelRoom roomToUpdate = roomRepository.findById(roomId).get();
        BeanUtils.copyProperties(room, roomToUpdate, "id");
        return save(roomToUpdate);
    }

    @Transactional
    public void delete(int roomId) throws DataAccessException {
        roomRepository.deleteById(roomId);
    }
}