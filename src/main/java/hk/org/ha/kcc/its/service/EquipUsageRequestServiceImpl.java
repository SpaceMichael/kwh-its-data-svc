package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.EquipUsageRequestDto;
import hk.org.ha.kcc.its.mapper.EquipUsageRequestMapper;
import hk.org.ha.kcc.its.model.EquipUsageRequest;
import hk.org.ha.kcc.its.repository.EquipUsageRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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
        //save
        equipUsageRequestRepository.save(equipUsageRequest);
        // EquipUsageRequest to EquipUsageRequestDto
        return equipUsageRequestMapper.EquipUsageRequestToEquipUsageRequestDto(equipUsageRequest);
    }

    @Override
    public List<EquipUsageRequestDto> getAllDto() {
        List<EquipUsageRequest> equipUsageRequestList = equipUsageRequestRepository.findAll()
                .stream().filter(EquipUsageRequest::getActiveFlag)
                .collect(Collectors.toList());
        return equipUsageRequestList.stream().map(equipUsageRequestMapper::EquipUsageRequestToEquipUsageRequestDto).collect(Collectors.toList());
    }

    @Override
    public EquipUsageRequestDto getDtoById(String id) {
        EquipUsageRequest equipUsageRequest = equipUsageRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("EquipUsageRequest not found"));
        return equipUsageRequestMapper.EquipUsageRequestToEquipUsageRequestDto(equipUsageRequest);
    }

    @Override
    public EquipUsageRequestDto updateById(String id, EquipUsageRequestDto equipUsageRequestDto) {
        // test id is exist or not
        EquipUsageRequest equipUsageRequest = equipUsageRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("EquipUsageRequest not found"));

        // Update by Dto
        if (equipUsageRequestDto.getEamNo() != null) {
            equipUsageRequest.setEamNo(equipUsageRequestDto.getEamNo());
        }
        if (equipUsageRequestDto.getSerNo() != null) {
            equipUsageRequest.setSerNo(equipUsageRequestDto.getSerNo());
        }
        if (equipUsageRequestDto.getModel() != null) {
            equipUsageRequest.setModel(equipUsageRequestDto.getModel());
        }
        if (equipUsageRequestDto.getBelongTo() != null) {
            equipUsageRequest.setBelongTo(equipUsageRequestDto.getBelongTo());
        }
        if (equipUsageRequestDto.getType() != null) {
            equipUsageRequest.setType(equipUsageRequestDto.getType());
        }
        if (equipUsageRequestDto.getCaseNo() != null) {
            equipUsageRequest.setCaseNo(equipUsageRequestDto.getCaseNo());
        }
        if (equipUsageRequestDto.getPatientName() != null) {
            equipUsageRequest.setPatientName(equipUsageRequestDto.getPatientName());
        }
        if (equipUsageRequestDto.getActiveFlag() != null) {
            equipUsageRequest.setActiveFlag(equipUsageRequestDto.getActiveFlag());
        }
        // save
        equipUsageRequestRepository.save(equipUsageRequest);
        return equipUsageRequestMapper.EquipUsageRequestToEquipUsageRequestDto(equipUsageRequest);
    }

    @Override
    public void deleteById(String id) {
        try {
            this.equipUsageRequestRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("EquipUsageRequest not found");
        }
    }
}
