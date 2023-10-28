package food.delivery.service.impl;

import food.delivery.dto.FilialDto;
import food.delivery.dto.response.GetResponse;
import food.delivery.dto.response.ValidatorDto;
import food.delivery.dto.template.ImageDto;
import food.delivery.helper.AppMessages;
import food.delivery.model.Filial;
import food.delivery.repository.FilialRepository;
import food.delivery.service.FilialService;
import food.delivery.service.ImageService;
import food.delivery.service.ValidatorService;
import food.delivery.service.mapper.interfaces.FilialMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class FilialServiceImpl implements FilialService {

    private final FilialRepository filialRepository;
    private final FilialMapper filialMapper;
    private final ValidatorService validatorService;
    private final ImageService imageService;
    @Value("${main.domain}")
    private String domain;


    @Override
    public ResponseEntity<?> addFilial(FilialDto filialDto) {
        try {
            if (filialDto.getId() != null){
                return ResponseEntity.internalServerError().body("ID bo'sh bo'lishi kerak");
            }
            if (filialDto.getActive() == null) {
                filialDto.setActive(false);
            }
            if (filialDto.getHumanCapacity() == null) {
                filialDto.setHumanCapacity(0);
            }

            Filial filial = filialMapper.toEntity(filialDto);
            filialRepository.save(filial);

            return ResponseEntity.ok().body(filial);
        }catch (DataAccessException | PersistenceException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> allFilial(Integer limit, Integer offset) {
        try {
            List<FilialDto> filialDtos = filialRepository.findAll()
                    .stream().map(filialMapper::toDto).toList();
            List<FilialDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/filial/all/?limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (filialDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(filialDtos.get(i));
                if (filialDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(filialDtos.size() >= offset+limit?domain + "/filial/all/?limit="+limit
                    +"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/filial/all/?limit="+limit+"&offset=" + (Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> allFilialIsActive(Boolean active, Integer limit, Integer offset) {
        try {
            List<FilialDto> filialDtos = filialRepository.findAllByActive(active)
                    .stream().map(filialMapper::toDto).toList();
            List<FilialDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/filial/all-active/?active"+active+"&limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (filialDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(filialDtos.get(i));
                if (filialDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(filialDtos.size() >= offset+limit?domain + "/filial/all-active/?" +
                    "active"+active+"&limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/filial/all-active/?active"+active+
                    "&limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> allOpenFilial(Integer limit, Integer offset) {
        try {
            List<FilialDto> filials = filialRepository.findAllByActive(true)
                    .stream().map(filialMapper::toDto).toList();

            LocalTime localTime = LocalTime.now();
            List<FilialDto> filialDtos = new ArrayList<>();

            for (FilialDto f : filials) {
                Filial filial = filialMapper.toEntity(f);
                LocalTime opening = filial.getOpeningTime().toLocalTime();
                LocalTime closing = filial.getClosingTime().toLocalTime();

                if (localTime.isAfter(opening) && localTime.isBefore(closing)) {
                    filialDtos.add(f);
                }
            }
            List<FilialDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/filial/all-open/?limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (filialDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(filialDtos.get(i));
                if (filialDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(filialDtos.size() >= offset+limit?domain + "/filial/all-open/?" +
                    "limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/filial/all-open/?" +
                    "limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        } catch (NullPointerException | DateTimeException | NumberFormatException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> sortingByDistance(Double lat, Double lon, Integer limit, Integer offset) {
        try {
            SortedMap<Double, FilialDto> map = checkTheDistanceForOrder(lat, lon);
            List<FilialDto> result = new ArrayList<>();
            List<FilialDto> filialDtos = new ArrayList<>(map.values());

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/filial/all-sorting/?lat="+lat+
                    "&lon="+lon+"&limit="+limit+"&offset=0");
            response.setData(result);

            if (filialDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(filialDtos.get(i));
                if (filialDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(filialDtos.size() >= offset+limit?domain + "/filial/all-sorting/?lat="+lat+
                    "&lon="+lon+"&limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/filial/all-sorting/?lat="+lat+
                    "&lon="+lon+"&limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        } catch (NullPointerException | NumberFormatException | ArithmeticException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getById(Integer id) {
        try {
            Optional<Filial> optional = filialRepository.findById(id);
            if (optional.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            FilialDto filialDto = filialMapper.toDto(optional.get());

            return ResponseEntity.ok().body(filialDto);
        }catch (DataAccessException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> updateFilial(FilialDto filialDto) {
        try {
            if (filialDto.getId() == null) {
                return ResponseEntity.internalServerError().body("ID " + AppMessages.EMPTY_FIELD);
            }

            Optional<Filial> optional = filialRepository.findById(filialDto.getId());
            if (optional.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            List<ValidatorDto> errors = validatorService.validateFilial(filialDto);
            if (!errors.isEmpty()) {
                return ResponseEntity.internalServerError().body(AppMessages.VALIDATOR_MESSAGE);
            }

            Filial filial1 = optional.get();
            Filial filial2 = filialMapper.toEntity(filialDto);

            filial1.setName(filial2.getName() == null ? filial1.getName() : filial2.getName());
            filial1.setAddress(filial2.getAddress() == null ? filial1.getAddress() : filial2.getAddress());
            filial1.setBuildingSize(filial2.getBuildingSize() == null ? filial1.getBuildingSize() : filial2.getBuildingSize());
            filial1.setHumanCapacity(filial2.getHumanCapacity() == null ? filial1.getHumanCapacity() : filial2.getHumanCapacity());
            filial1.setActive(filial2.getActive() == null ? filial1.getActive() : filial2.getActive());
            filial1.setAdminId(filial2.getAdminId() == null ? filial1.getAdminId() : filial2.getAdminId());
            filial1.setLatitude(filial2.getLatitude() == null ? filial1.getLatitude() : filial2.getLatitude());
            filial1.setLongitude(filial2.getLongitude() == null ? filial1.getLongitude() : filial2.getLongitude());
            filial1.setOpeningTime(filial2.getOpeningTime() == null ? filial1.getOpeningTime() : filial2.getOpeningTime());
            filial1.setClosingTime(filial2.getClosingTime() == null ? filial1.getClosingTime() : filial2.getClosingTime());
            filial1.setPhoneNum(filial2.getPhoneNum() == null ? filial1.getPhoneNum() : filial2.getPhoneNum());

            filialRepository.save(filial1);
            filialDto = filialMapper.toDto(filial1);

            return ResponseEntity.ok().body(filialDto);
        }catch (NullPointerException | IllegalArgumentException | DataAccessException | NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> deleteFilial(Integer id) {
        try {
            filialRepository.deleteById(id);

            return ResponseEntity.ok().body("Deleted!");
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> isActive(Integer id, Boolean active) {
        try {
            Filial filial = filialRepository.findById(id).get();
            filial.setActive(active);
            filialRepository.save(filial);

            return ResponseEntity.ok().body("Active " + active);
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    /**
     *  Userning locationga eng yaqin filialni aniqlash.
     * @param lat double
     * @param lon double
     *
     * @return distance km and filialDto
     * */
    @Override
    public SortedMap<Double, FilialDto> checkTheDistanceForOrder(Double lat, Double lon) {
        try {
            List<FilialDto> filials = filialRepository.findAllByActive(true)
                    .stream().map(filialMapper::toDto).toList();

            LocalTime localTime = LocalTime.now();
            List<FilialDto> filialDtos = new ArrayList<>();

            for (FilialDto f : filials) {
                Filial filial = filialMapper.toEntity(f);
                LocalTime opening = filial.getOpeningTime().toLocalTime();
                LocalTime closing = filial.getClosingTime().toLocalTime();

                if (localTime.isAfter(opening) && localTime.isBefore(closing)) {
                    filialDtos.add(f);
                }
            }

            SortedMap<Double, FilialDto> map = new TreeMap<>();

            for (FilialDto f : filialDtos) {
                Double filialLat = f.getLatitude();
                Double filialLon = f.getLongitude();

                Double distance = calculateDistance(lat, lon, filialLat, filialLon);
                map.put(distance, f);
            }

            return map;
        } catch (NullPointerException | NumberFormatException | ArithmeticException e) {
            log.error(e.getMessage());
            return null;
        }
    }


    private Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        try {
//            GreatCircleDistance
            final double EARTH_RADIUS = 6371.0; // Earth radius in kilometers

            double phi1 = Math.toRadians(lat1);
            double phi2 = Math.toRadians(lat2);
            double dPhi = Math.toRadians(lat2 - lat1);
            double dLambda = Math.toRadians(lon2 - lon1);

            double a = Math.pow(Math.sin(dPhi / 2), 2) + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(dLambda / 2), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            return EARTH_RADIUS * c; // Distance in kilometers
        } catch (ArithmeticException e){
            log.error(e.getMessage());
            return null;
        }
    }


    @Override
    public ResponseEntity<?> uploadImage(MultipartFile multipartFile, Integer filialId) {
        Filial filial = filialRepository.findById(filialId).get();

        Long imageId = filial.getImageId();

        ImageDto image = (ImageDto) imageService
                .addImage(multipartFile, "filial image").getBody();

        assert image != null;
        filial.setImagePath(image.getImagePath());
        filial.setImageId(image.getId());

        filialRepository.save(filial);

        if (imageId != null) {
            boolean check = (boolean) imageService.deleteImage(imageId).getBody();
            if (!check) {
                return ResponseEntity.internalServerError().body("Old image not deleted");
            }
        }

        return ResponseEntity.accepted().body(image);
    }

}