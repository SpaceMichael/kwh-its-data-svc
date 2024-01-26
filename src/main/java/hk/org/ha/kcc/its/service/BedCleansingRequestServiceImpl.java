package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.BedCleansingDashBoardDto;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.its.mapper.BedCleansingRequestMapper;
import hk.org.ha.kcc.its.model.BedCleansingRequest;
import hk.org.ha.kcc.its.repository.BedCleansingServiceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;

@Service
@Transactional
public class BedCleansingRequestServiceImpl implements BedCleansingRequestService {

    private final BedCleansingServiceRepository bedCleansingServiceRepository;
    private final BedCleansingRequestMapper bedCleansingRequestMapper;


    public BedCleansingRequestServiceImpl(BedCleansingServiceRepository bedCleansingServiceRepository
            , BedCleansingRequestMapper bedCleansingRequestMapper

    ) {
        this.bedCleansingServiceRepository = bedCleansingServiceRepository;
        this.bedCleansingRequestMapper = bedCleansingRequestMapper;

    }

    @Override
    public BedCleansingRequestDto create(BedCleansingRequestDto bedCleansingRequestDto) {
        BedCleansingRequest bedCleansingRequest = this.bedCleansingRequestMapper.BedCleansingRequestDtoToBedCleansingRequest(bedCleansingRequestDto);

        if (bedCleansingRequestDto.getWholeBed() == null) {
            bedCleansingRequest.setWholeBed(false);
        }

        return this.bedCleansingRequestMapper.BedCleansingRequestToBedCleansingRequestDto(bedCleansingServiceRepository.save(bedCleansingRequest));
    }

    @Override
    public List<BedCleansingRequestDto> getAllDto(String ward, String cubicle, String bed, Integer period, Boolean completedStatus) {
        // use findAll() to get all the records from the table
        // and filter ward, cubicle, bed if they are not null, and request.ward, cubicle, bed !=null
        // and if period is not null, get the list of BedCleansingRequest in recent period hours  ,check by request.getCreatedDate()
        List<BedCleansingRequest> bedCleansingRequestList = bedCleansingServiceRepository.findAll().stream()
                .filter(request -> ward == null || (request.getWard() != null && request.getWard().equals(ward)))
                .filter(request -> cubicle == null || (request.getCubicle() != null && request.getCubicle().equals(cubicle)))
                .filter(request -> bed == null || (request.getBedNo() != null && request.getBedNo().equals(bed)))
                // if period is not null, get the list of BedCleansingRequest in recent period hours  ,check by request.getCreatedDate()
                .filter(request -> period == null || (request.getCreatedDate() != null && request.getCreatedDate().isAfter(java.time.LocalDateTime.now().minusHours(period))))
                .filter(request -> completedStatus == null || (request.getStatus() != null && completedStatus && request.getStatus().equalsIgnoreCase("COMPLETED")) || (request.getStatus() != null && !completedStatus && !request.getStatus().equalsIgnoreCase("COMPLETED")))
                .toList();

        // use stream and map and filter to get the list of BedCleansingRequestDto
        return bedCleansingRequestList.stream()
                .map(bedCleansingRequestMapper::BedCleansingRequestToBedCleansingRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public BedCleansingRequestDto getDtoById(String id) {
        // Use findById() to get the record from the table. If not found, throw
        // ResourceNotFoundException
        BedCleansingRequest bedCleansingRequest = this.bedCleansingServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));
        return this.bedCleansingRequestMapper.BedCleansingRequestToBedCleansingRequestDto(bedCleansingRequest);
    }

    @Override
    public BedCleansingRequestDto updateById(String id, BedCleansingRequestDto bedCleansingRequestDto) {
        // get by id , if is not exist, throw ResourceNotFoundException
        BedCleansingRequest bedCleansingRequest = this.bedCleansingServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));
        // update by dto
        BeanUtils.copyProperties(bedCleansingRequestDto, bedCleansingRequest, getNullPropertyNames(bedCleansingRequestDto));
        return this.bedCleansingRequestMapper.BedCleansingRequestToBedCleansingRequestDto(bedCleansingServiceRepository.save(bedCleansingRequest));
    }

    @Override
    public void deleteById(String id) {
        try {
            this.bedCleansingServiceRepository.deleteById(id);
        } catch (DataRetrievalFailureException e) {
            throw new ResourceNotFoundException("BedCleansingRequest not found");
        }
    }


    @Override
    public BedCleansingDashBoardDto getBedCleansingDashBoardDto(String ward, String cubicle, String bed, Integer period, Boolean completedStatus, Boolean total) {
        List<BedCleansingRequestDto> bedCleansingRequestDtoList = this.getAllDto(ward, cubicle, bed, period, completedStatus)
                .stream().filter(bedCleansingRequestDto -> bedCleansingRequestDto.getStatus() != null)
                .collect(Collectors.toList());
        long count = bedCleansingRequestDtoList.size();
        long pending = bedCleansingRequestDtoList.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("Pending")).count();
        long process = bedCleansingRequestDtoList.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("Process")).count();
        long completed = bedCleansingRequestDtoList.stream().filter(dto -> dto.getStatus().equalsIgnoreCase("Completed")).count();

        if (total != null && total) {
            return BedCleansingDashBoardDto.builder().total(count)
                    .Pending(pending)
                    .Process(process)
                    .Completed(completed)
                    .bedCleansingRequestList(bedCleansingRequestDtoList)
                    .build();
        } else {
            return BedCleansingDashBoardDto.builder().total(null).bedCleansingRequestList(bedCleansingRequestDtoList).build();
        }
    }
}
