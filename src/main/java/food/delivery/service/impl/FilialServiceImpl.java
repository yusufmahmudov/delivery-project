package food.delivery.service.impl;

import food.delivery.dto.FilialDto;
import food.delivery.dto.response.ResponseDto;
import food.delivery.dto.response.ValidatorDto;
import food.delivery.dto.template.ImageDto;
import food.delivery.helper.AppCode;
import food.delivery.helper.AppMessages;
import food.delivery.model.Employee;
import food.delivery.model.Filial;
import food.delivery.repository.FilialRepository;
import food.delivery.security.SecurityUtil;
import food.delivery.service.FilialService;
import food.delivery.service.ImageService;
import food.delivery.service.ValidatorService;
import food.delivery.service.mapper.interfaces.FilialMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class FilialServiceImpl implements FilialService {

    private final FilialRepository filialRepository;
    private final FilialMapper filialMapper;
    private final ValidatorService validatorService;
    private final ImageService imageService;

    @Override
    public ResponseDto<String> addFilial(FilialDto filialDto) {
        try {
            if (filialDto.getId() != null){
                return ResponseDto.<String>builder()
                        .message("id bo'sh bo'ishi kerak")
                        .success(false)
                        .code(AppCode.VALIDATOR_ERROR)
                        .build();
            }
            if (filialDto.getActive() == null) {
                filialDto.setActive(false);
            }
            if (filialDto.getHumanCapacity() == null) {
                filialDto.setHumanCapacity(0);
            }

            Filial filial = filialMapper.toEntity(filialDto);
            filialRepository.save(filial);

            return ResponseDto.<String>builder()
                    .message(AppMessages.SAVED)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (DataAccessException | PersistenceException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .code(AppCode.ERROR)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<FilialDto>> allFilial() {
        try {
            List<FilialDto> filialDtos = filialRepository.findAll()
                    .stream().map(filialMapper::toDto).toList();

            return ResponseDto.<List<FilialDto>>builder()
                    .data(filialDtos)
                    .message(AppMessages.OK)
                    .success(true)
                    .code(AppCode.OK)
                    .build();
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.<List<FilialDto>>builder()
                    .message(e.getMessage())
                    .success(false)
                    .code(AppCode.ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<FilialDto>> allFilialIsActive(Boolean active) {
        try {
            List<FilialDto> filialDtos = filialRepository.findAllByActive(active)
                    .stream().map(filialMapper::toDto).toList();

            return ResponseDto.<List<FilialDto>>builder()
                    .data(filialDtos)
                    .message(AppMessages.OK)
                    .success(true)
                    .code(AppCode.OK)
                    .build();
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.<List<FilialDto>>builder()
                    .message(e.getMessage())
                    .success(false)
                    .code(AppCode.ERROR)
                    .build();
        }
    }


    @Override
    public ResponseDto<FilialDto> getById(Integer id) {
        try {
            Optional<Filial> optional = filialRepository.findById(id);
            if (optional.isEmpty()){
                return ResponseDto.<FilialDto>builder()
                        .message(AppMessages.NOT_FOUND)
                        .success(false)
                        .code(AppCode.NOT_FOUND)
                        .build();
            }
            FilialDto filialDto = filialMapper.toDto(optional.get());

            return ResponseDto.<FilialDto>builder()
                    .message(AppMessages.OK)
                    .data(filialDto)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (DataAccessException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseDto.<FilialDto>builder()
                    .code(AppCode.ERROR)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<FilialDto> updateFilial(FilialDto filialDto) {
        try {
            if (filialDto.getId() == null) {
                return ResponseDto.<FilialDto>builder()
                        .success(false)
                        .message("Id " + AppMessages.EMPTY_FIELD)
                        .code(AppCode.ERROR)
                        .build();
            }

            Optional<Filial> optional = filialRepository.findById(filialDto.getId());
            if (optional.isEmpty()){
                return ResponseDto.<FilialDto>builder()
                        .message(AppMessages.NOT_FOUND)
                        .success(false)
                        .code(AppCode.NOT_FOUND)
                        .build();
            }

            List<ValidatorDto> errors = validatorService.validateFilial(filialDto);
            if (!errors.isEmpty()) {
                return ResponseDto.<FilialDto>builder()
                        .message(AppMessages.VALIDATOR_MESSAGE)
                        .success(false)
                        .code(AppCode.VALIDATOR_ERROR)
                        .errors(errors)
                        .build();
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

            return ResponseDto.<FilialDto>builder()
                    .message(AppMessages.SAVED)
                    .code(AppCode.OK)
                    .data(filialDto)
                    .success(true)
                    .build();
        }catch (NullPointerException | IllegalArgumentException | DataAccessException | NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseDto.<FilialDto>builder()
                    .message(e.getMessage())
                    .success(false)
                    .code(AppCode.ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> deleteFilial(Integer id) {
        try {
            filialRepository.deleteById(id);

            return ResponseDto.<String>builder()
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .success(false)
                    .code(AppCode.ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> isActive(Integer id, Boolean active) {
        try {
            Filial filial = filialRepository.findById(id).get();
            filial.setActive(active);
            filialRepository.save(filial);

            return ResponseDto.<String>builder()
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .data(active.toString())
                    .success(true)
                    .build();
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .success(false)
                    .code(AppCode.ERROR)
                    .build();
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
    public SortedMap<Double, FilialDto> checkTheDistance(Double lat, Double lon) {
        try {
            List<FilialDto> filials = allOpenFilial().getData();
            SortedMap<Double, FilialDto> map = new TreeMap<>();

            for (FilialDto f : filials) {
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
    public ResponseDto<List<FilialDto>> allOpenFilial() {
        try {
            List<FilialDto> filials = allFilialIsActive(true).getData();
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

            return ResponseDto.<List<FilialDto>>builder()
                    .message(AppMessages.OK)
                    .data(filialDtos)
                    .success(true)
                    .code(AppCode.OK)
                    .build();
        } catch (NullPointerException | DateTimeException | NumberFormatException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<FilialDto>>builder()
                    .message(e.getMessage())
                    .success(false)
                    .code(AppCode.ERROR)
                    .build();
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