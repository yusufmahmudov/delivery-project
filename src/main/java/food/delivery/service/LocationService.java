package food.delivery.service;

import food.delivery.dto.LocationDto;
import org.springframework.http.ResponseEntity;

public interface LocationService {


    /**
     * @params Location location -- Userning Location manzilini o'zida saqlovchi object
     * @return String data -- Saqlangan ekanligi haqida ma'lumot
     * */
    ResponseEntity<?> addLocation(LocationDto locationDto);


    /**
     * @params Long id -- Objectning idsi.
     * @return 1ta Location objecti qaytadi.
     * */
    ResponseEntity<?> getById(Long id);


    /**
     * @params Long userId -- Location bog'langan User objectining idsi.
     * @return Userga bog'langan hamma Location objectlarini qaytaradi
     * */
    ResponseEntity<?> getAllLocationByUserId();


    /**
     * @params Long id -- Location id
     * @return String data -- saqlanganligi haqida ma'lumot
     * */
    ResponseEntity<?> currentLocation(Long id);


    /**
     * @params Location location -- Userning Location manzilini o'zida saqlovchi object
     * @return Yangilangan Location objectini qaytaradi
     * */
    ResponseEntity<?> updateLocation(LocationDto locationDto);


    /**
     * @params Long id -- Locationning idsi
     * @return String data -- o'chirilganligi haqida ma'lumot
     * */
    ResponseEntity<?> deleteById(Long id);

}
