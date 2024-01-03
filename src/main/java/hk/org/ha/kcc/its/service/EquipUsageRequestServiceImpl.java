package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.EquipUsageRequestDto;
import hk.org.ha.kcc.its.mapper.EquipUsageRequestMapper;
import hk.org.ha.kcc.its.model.EquipUsageRequest;
import hk.org.ha.kcc.its.repository.EquipUsageRequestRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;


@Service
@Transactional
public class EquipUsageRequestServiceImpl implements EquipUsageRequestService {

    private final EquipUsageRequestRepository equipUsageRequestRepository;
    private final EquipUsageRequestMapper equipUsageRequestMapper;

    public EquipUsageRequestServiceImpl(EquipUsageRequestRepository equipUsageRequestRepository, EquipUsageRequestMapper equipUsageRequestMapper) {
        this.equipUsageRequestRepository = equipUsageRequestRepository;
        this.equipUsageRequestMapper = equipUsageRequestMapper;
    }

    @Override
    public EquipUsageRequestDto create(EquipUsageRequestDto equipUsageRequestDto) {
        // create a new EquipUsageRequest
        // EquipUsageRequestDto to EquipUsageRequest
        EquipUsageRequest equipUsageRequest = equipUsageRequestMapper.EquipUsageRequestDtoToEquipUsageRequest(equipUsageRequestDto);
        if (equipUsageRequestDto.getActiveFlag() != null) {
            equipUsageRequest.setActiveFlag(equipUsageRequestDto.getActiveFlag());
        } else {
            equipUsageRequest.setActiveFlag(true);
        }
        // if date is null , get current date
        if (equipUsageRequestDto.getDate() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime dateTime = LocalDateTime.now();
            equipUsageRequest.setDate(dateTime.format(formatter));
        }
        //if time is null , get current time
        if (equipUsageRequestDto.getTime() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime dateTime = LocalDateTime.now();
            equipUsageRequest.setTime(dateTime.format(formatter));
        }

        //save
        equipUsageRequestRepository.save(equipUsageRequest);
        // EquipUsageRequest to EquipUsageRequestDto
        return equipUsageRequestMapper.EquipUsageRequestToEquipUsageRequestDto(equipUsageRequest);
    }

    @Override
    public List<EquipUsageRequestDto> getAllDto(Integer eamNo, String caseNo, Date dateStart, Date dateEnd, String year, String month) {
        List<EquipUsageRequest> equipUsageRequestList = equipUsageRequestRepository.findAll()
                .stream().filter(EquipUsageRequest::getActiveFlag)
                .collect(Collectors.toList());
        // if Integer eamNo, String caseNo, LocalDateTime dateStart, LocalDateTime dateEnd, String year, String month all null ,return equipUsageRequestDtoList
        if (eamNo == null && caseNo == null && dateStart == null && dateEnd == null && year == null && month == null) {
            return equipUsageRequestList.stream().map(equipUsageRequestMapper::EquipUsageRequestToEquipUsageRequestDto).collect(Collectors.toList());
        }

        List<EquipUsageRequestDto> equipUsageRequestDtoList = equipUsageRequestList.stream()
                .map(equipUsageRequestMapper::EquipUsageRequestToEquipUsageRequestDto)
                .filter(equipUsageRequestDto -> equipUsageRequestDto.getDate() != null && equipUsageRequestDto.getTime() != null)
                .peek(equipUsageRequestDto -> {
                    String tempDate = equipUsageRequestDto.getDate() + " " + equipUsageRequestDto.getTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                    LocalDateTime dateTime = LocalDateTime.parse(tempDate, formatter);
                    equipUsageRequestDto.setUserDateTime(dateTime);
                })
                .collect(Collectors.toList());

        // from date to localDateTime dateStart and dateEnd
        LocalDateTime localDateTimeStart = dateStart != null ? dateStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay() : null;
        LocalDateTime localDateTimeEnd = dateEnd != null ? dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(23, 59) : null;

        equipUsageRequestDtoList = equipUsageRequestDtoList.stream()
                .filter(equipUsageRequest -> eamNo == null || eamNo.equals(equipUsageRequest.getEamNo()))
                .filter(equipUsageRequest -> caseNo == null || caseNo.equals(equipUsageRequest.getCaseNo()))
                .filter(equipUsageRequest -> dateStart == null || equipUsageRequest.getUserDateTime().isAfter(localDateTimeStart))
                .filter(equipUsageRequest -> dateEnd == null || equipUsageRequest.getUserDateTime().isBefore(localDateTimeEnd))
                .filter(equipUsageRequest -> year == null || Integer.parseInt(year) == equipUsageRequest.getUserDateTime().getYear())
                .filter(equipUsageRequest -> month == null || Integer.parseInt(month) == equipUsageRequest.getUserDateTime().getMonthValue())
                .collect(Collectors.toList());

        // use Map and Collectors.groupingBy to get total number of records by eamNo
        Map<Integer, Long> eamNoCountMap = equipUsageRequestDtoList.stream()
                .collect(Collectors.groupingBy(EquipUsageRequestDto::getEamNo, Collectors.counting()));

        return eamNoCountMap.entrySet().stream()
                .map(entry -> {
                    EquipUsageRequestDto dto = new EquipUsageRequestDto();
                    dto.setEamNo(entry.getKey());
                    dto.setTotal(entry.getValue().intValue());
                    return dto;
                })
                .collect(Collectors.toList());

    }

    @Override
    public EquipUsageRequestDto getDtoById(String id) {
        EquipUsageRequest equipUsageRequest = equipUsageRequestRepository.findById(id).filter(EquipUsageRequest::getActiveFlag).orElseThrow(() -> new ResourceNotFoundException("EquipUsageRequest not found"));
        return equipUsageRequestMapper.EquipUsageRequestToEquipUsageRequestDto(equipUsageRequest);
    }

    @Override
    public EquipUsageRequestDto updateById(String id, EquipUsageRequestDto equipUsageRequestDto) {
        EquipUsageRequest equipUsageRequest = equipUsageRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EquipUsageRequest not found"));
        BeanUtils.copyProperties(equipUsageRequestDto, equipUsageRequest, getNullPropertyNames(equipUsageRequestDto));
        equipUsageRequestRepository.save(equipUsageRequest);
        return equipUsageRequestMapper.EquipUsageRequestToEquipUsageRequestDto(equipUsageRequest);
    }


    @Override
    public void deleteById(String id) {
        if (!equipUsageRequestRepository.existsById(id)) {
            throw new ResourceNotFoundException("EquipUsageRequest not found");
        }
        equipUsageRequestRepository.deleteById(id);
    }
}
