package hk.org.ha.kcc.eform.service;

import hk.org.ha.kcc.eform.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.eform.mapper.BedCleansingRequestMapper;
import hk.org.ha.kcc.eform.model.BedCleansingRequest;
import hk.org.ha.kcc.eform.repository.BedCleansingServiceRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class BedCleansingRequestServiceImpl implements BedCleansingRequestService {


    private final BedCleansingServiceRepository bedCleansingServiceRepository;

    private final BedCleansingRequestMapper bedCleansingRequestMapper;

    public BedCleansingRequestServiceImpl(BedCleansingServiceRepository bedCleansingServiceRepository, BedCleansingRequestMapper bedCleansingRequestMapper) {
        this.bedCleansingServiceRepository = bedCleansingServiceRepository;
        this.bedCleansingRequestMapper = bedCleansingRequestMapper;
    }

    @Override
    public BedCleansingRequestDto create(BedCleansingRequestDto bedCleansingRequestDto) {
        BedCleansingRequest bedCleansingRequest = this.bedCleansingRequestMapper.BedCleansingRequestDtoToBedCleansingRequest(bedCleansingRequestDto);
        if (bedCleansingRequestDto.getBedChecked() != null) {
            bedCleansingRequest.setBedChecked(false);
        }
        if (bedCleansingRequestDto.getActiveFlag() != null) {
            bedCleansingRequest.setActiveFlag(true);
        }
        this.bedCleansingServiceRepository.save(bedCleansingRequest);
        return this.bedCleansingRequestMapper.BedCleansingRequestToBedCleansingRequestDto(bedCleansingRequest);
    }

    @Override
    public List<BedCleansingRequestDto> getAllDto() {
        // use findAll() to get all the records from the table
        List<BedCleansingRequest> bedCleansingRequestList = this.bedCleansingServiceRepository.findAll();
        //use stream and map and filter to get the list of BedCleansingRequestDto
        return bedCleansingRequestList.stream().map(bedCleansingRequestMapper::BedCleansingRequestToBedCleansingRequestDto).collect(Collectors.toList());
    }

    @Override
    public BedCleansingRequestDto getDtoById(String id) {
        // Use findById() to get the record from the table. If not found, throw ResourceNotFoundException
        BedCleansingRequest bedCleansingRequest = (BedCleansingRequest) this.bedCleansingServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));
        return this.bedCleansingRequestMapper.BedCleansingRequestToBedCleansingRequestDto(bedCleansingRequest);
    }

    @Override
    public BedCleansingRequestDto update(String id, BedCleansingRequestDto bedCleansingRequestDto) {
        //get by id , if is not exist, throw ResourceNotFoundException
        BedCleansingRequest bedCleansingRequest = (BedCleansingRequest) this.bedCleansingServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));

        //update by dto
        if (bedCleansingRequestDto.getHospitalCode() != null) {
            bedCleansingRequest.setHospitalCode(bedCleansingRequestDto.getHospitalCode());
        }

        if (bedCleansingRequestDto.getDept() != null) {
            bedCleansingRequest.setDept(bedCleansingRequestDto.getDept());
        }

        if (bedCleansingRequestDto.getWard() != null) {
            bedCleansingRequest.setWard(bedCleansingRequestDto.getWard());
        }

        if (bedCleansingRequestDto.getCubicle() != null) {
            bedCleansingRequest.setCubicle(bedCleansingRequestDto.getCubicle());
        }

        if (bedCleansingRequestDto.getBedChecked() != null) {
            bedCleansingRequest.setBedChecked(bedCleansingRequestDto.getBedChecked());
        }

        if (bedCleansingRequestDto.getBedNo() != null) {
            bedCleansingRequest.setBedNo(bedCleansingRequestDto.getBedNo());
        }

        if (bedCleansingRequestDto.getBedType() != null) {
            bedCleansingRequest.setBedType(bedCleansingRequestDto.getBedType());
        }

        if (bedCleansingRequestDto.getRemarks() != null) {
            bedCleansingRequest.setRemarks(bedCleansingRequestDto.getRemarks());
        }

        if (bedCleansingRequestDto.getStatus() != null) {
            bedCleansingRequest.setStatus(bedCleansingRequestDto.getStatus());
        }

        if (bedCleansingRequestDto.getRequestor() != null) {
            bedCleansingRequest.setRequestor(bedCleansingRequestDto.getRequestor());
        }

        if (bedCleansingRequestDto.getRequestorName() != null) {
            bedCleansingRequest.setRequestorName(bedCleansingRequestDto.getRequestorName());
        }

        if (bedCleansingRequestDto.getRequestorContactNo() != null) {
            bedCleansingRequest.setRequestorContactNo(bedCleansingRequestDto.getRequestorContactNo());
        }

        if (bedCleansingRequestDto.getCleaner() != null) {
            bedCleansingRequest.setCleaner(bedCleansingRequestDto.getCleaner());
        }

        if (bedCleansingRequestDto.getActiveFlag() != null) {
            bedCleansingRequest.setActiveFlag(bedCleansingRequestDto.getActiveFlag());
        }
        this.bedCleansingServiceRepository.save(bedCleansingRequest);
        return this.bedCleansingRequestMapper.BedCleansingRequestToBedCleansingRequestDto(bedCleansingRequest);
    }

    @Override
    public void delete(String id) {
        try {
            this.bedCleansingServiceRepository.deleteById(id);
        } catch (DataRetrievalFailureException e) {
            throw new ResourceNotFoundException("BedCleansingRequest not found");
        }
    }
}
