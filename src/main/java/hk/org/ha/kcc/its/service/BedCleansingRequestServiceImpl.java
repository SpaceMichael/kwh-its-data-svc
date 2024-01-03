package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.BedCleansingDashBoardDto;
import hk.org.ha.kcc.its.dto.BedCleansingRequestAuditDto;
import hk.org.ha.kcc.its.model.Eform;
import hk.org.ha.kcc.its.repository.EformRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.its.mapper.BedCleansingRequestMapper;
import hk.org.ha.kcc.its.model.BedCleansingRequest;
import hk.org.ha.kcc.its.repository.BedCleansingServiceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BedCleansingRequestServiceImpl implements BedCleansingRequestService {

    private final BedCleansingServiceRepository bedCleansingServiceRepository;
    private final BedCleansingRequestMapper bedCleansingRequestMapper;
    private final EformRepository eformRepository;


    public BedCleansingRequestServiceImpl(BedCleansingServiceRepository bedCleansingServiceRepository
            , BedCleansingRequestMapper bedCleansingRequestMapper
            , EformRepository eformRepository
    ) {
        this.bedCleansingServiceRepository = bedCleansingServiceRepository;
        this.bedCleansingRequestMapper = bedCleansingRequestMapper;
        this.eformRepository = eformRepository;
    }

    @Override
    public BedCleansingRequestDto create(BedCleansingRequestDto bedCleansingRequestDto) {
        BedCleansingRequest bedCleansingRequest = this.bedCleansingRequestMapper.BedCleansingRequestDtoToBedCleansingRequest(bedCleansingRequestDto);

        if (bedCleansingRequest.getEformId() != null) {
            Eform eform = this.eformRepository.findById(bedCleansingRequest.getEformId())
                    .orElseThrow(() -> new ResourceNotFoundException("Id" + bedCleansingRequest.getEformId() + "eform not found"));
            bedCleansingRequest.assignEform(eform);
        }
        if (bedCleansingRequestDto.getWholeBed() == null) {
            bedCleansingRequest.setWholeBed(false);
        }
        if (bedCleansingRequestDto.getActiveFlag() == null) {
            bedCleansingRequest.setActiveFlag(true);
        }
        //BedCleansingRequest bedCleansingRequest1 = this.bedCleansingServiceRepository.save(bedCleansingRequest);
        return this.bedCleansingRequestMapper.BedCleansingRequestToBedCleansingRequestDto(bedCleansingServiceRepository.save(bedCleansingRequest));
    }

    @Override
    public List<BedCleansingRequestDto> getAllDto(String ward, String cubicle, String bed, Integer period, Boolean completedStatus) {
        // use findAll() to get all the records from the table
        // and filter ward, cubicle, bed if they are not null, and request.ward, cubicle, bed !=null
        // and activeflag is true
        // and if period is not null, get the list of BedCleansingRequest in recent period hours  ,check by request.getCreatedDate()
        List<BedCleansingRequest> bedCleansingRequestList = bedCleansingServiceRepository.findAll().stream()
                .filter(BedCleansingRequest -> BedCleansingRequest.getActiveFlag() != null && BedCleansingRequest.getActiveFlag())
                .filter(request -> ward == null || (request.getWard() != null && request.getWard().equals(ward)))
                .filter(request -> cubicle == null || (request.getCubicle() != null && request.getCubicle().equals(cubicle)))
                .filter(request -> bed == null || (request.getBedNo() != null && request.getBedNo().equals(bed)))
                // if period is not null, get the list of BedCleansingRequest in recent period hours  ,check by request.getCreatedDate()
                .filter(request -> period == null || (request.getCreatedDate() != null && request.getCreatedDate().isAfter(java.time.LocalDateTime.now().minusHours(period))))
                .filter(request -> completedStatus == null || (request.getStatus() != null && completedStatus && request.getStatus().equalsIgnoreCase("COMPLETED")) || (request.getStatus() != null && !completedStatus && !request.getStatus().equalsIgnoreCase("COMPLETED")))
                .collect(Collectors.toList());

        // use stream and map and filter to get the list of BedCleansingRequestDto
        return bedCleansingRequestList.stream()
                .map(bedCleansingRequestMapper::BedCleansingRequestToBedCleansingRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public BedCleansingRequestDto getDtoById(String id) {
        // Use findById() to get the record from the table. If not found, throw
        // ResourceNotFoundException
        BedCleansingRequest bedCleansingRequest = this.bedCleansingServiceRepository.findById(id).filter(BedCleansingRequest::getActiveFlag).orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));
        return this.bedCleansingRequestMapper.BedCleansingRequestToBedCleansingRequestDto(bedCleansingRequest);
    }

    @Override
    public BedCleansingRequestDto updateById(String id, BedCleansingRequestDto bedCleansingRequestDto) {
        // get by id , if is not exist, throw ResourceNotFoundException
        BedCleansingRequest bedCleansingRequest = this.bedCleansingServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));

        // update by dto
        bedCleansingRequest.setHospitalCode(Optional.ofNullable(bedCleansingRequestDto.getHospitalCode()).orElse(bedCleansingRequest.getHospitalCode()));
        bedCleansingRequest.setDept(Optional.ofNullable(bedCleansingRequestDto.getDept()).orElse(bedCleansingRequest.getDept()));
        bedCleansingRequest.setWard(Optional.ofNullable(bedCleansingRequestDto.getWard()).orElse(bedCleansingRequest.getWard()));
        bedCleansingRequest.setCubicle(Optional.ofNullable(bedCleansingRequestDto.getCubicle()).orElse(bedCleansingRequest.getCubicle()));
        bedCleansingRequest.setWholeBed(Optional.ofNullable(bedCleansingRequestDto.getWholeBed()).orElse(bedCleansingRequest.getWholeBed()));
        bedCleansingRequest.setBedNo(Optional.ofNullable(bedCleansingRequestDto.getBedNo()).orElse(bedCleansingRequest.getBedNo()));
        bedCleansingRequest.setBedType(Optional.ofNullable(bedCleansingRequestDto.getBedType()).orElse(bedCleansingRequest.getBedType()));
        bedCleansingRequest.setRemarks(Optional.ofNullable(bedCleansingRequestDto.getRemarks()).orElse(bedCleansingRequest.getRemarks()));
        bedCleansingRequest.setStatus(Optional.ofNullable(bedCleansingRequestDto.getStatus()).orElse(bedCleansingRequest.getStatus()));
        bedCleansingRequest.setRequestorContactNo(Optional.ofNullable(bedCleansingRequestDto.getRequestorContactNo()).orElse(bedCleansingRequest.getRequestorContactNo()));
        bedCleansingRequest.setCleaner(Optional.ofNullable(bedCleansingRequestDto.getCleaner()).orElse(bedCleansingRequest.getCleaner()));
        bedCleansingRequest.setActiveFlag(Optional.ofNullable(bedCleansingRequestDto.getActiveFlag()).orElse(bedCleansingRequest.getActiveFlag()));
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
    public List<BedCleansingRequestAuditDto> getDtlByBCId(String Id) {
        List<Object[]> objectList = this.bedCleansingServiceRepository.getAllByBCId(Id);
        // map the objectList to BedCleansingRequestAuditDto
        List<BedCleansingRequestAuditDto> bedCleansingRequestAuditDtoList = objectList.stream()
                .filter(Objects::nonNull)
                .map(objects -> {
                    BedCleansingRequestAuditDto dto = new BedCleansingRequestAuditDto();
                    dto.setBcId(Objects.toString(objects[0], null));
                    dto.setRevId(Objects.toString(objects[1], null));
                    dto.setRevType(Objects.toString(objects[2], null));
                    dto.setCleaner(Objects.toString(objects[3], null));
                    dto.setActiveFlag(Boolean.parseBoolean(Objects.toString(objects[4], "false")));
                    dto.setBedNo(Objects.toString(objects[5], null));
                    dto.setBedType(Objects.toString(objects[6], null));
                    dto.setCleaningProcess(Objects.toString(objects[7], null));
                    dto.setCubicleNo(Objects.toString(objects[8], null));
                    dto.setDeptCode(Objects.toString(objects[9], null));
                    dto.setDetergent(Objects.toString(objects[10], null));
                    dto.setHospitalCode(Objects.toString(objects[11], null));
                    dto.setEformId(Integer.parseInt(Objects.toString(objects[12], "0")));
                    dto.setRemarks(Objects.toString(objects[13], null));
                    dto.setRequestorContactNo(Integer.parseInt(Objects.toString(objects[14], "0")));
                    dto.setStatus(Objects.toString(objects[15], null));
                    dto.setWardCode(Objects.toString(objects[16], null));
                    dto.setWholeBedCleansing(Boolean.parseBoolean(Objects.toString(objects[17], "false")));
                    dto.setRequestorId(Objects.toString(objects[18], null));
                    dto.setRevDetailId(Objects.toString(objects[19], null));
                    dto.setAction(Objects.toString(objects[20], null));
                    dto.setActionDateTime(Objects.toString(objects[21], null));
                    dto.setRecordId(Objects.toString(objects[22], null));
                    dto.setTableName(Objects.toString(objects[23], null));
                    dto.setUsername(Objects.toString(objects[24], null));
                    dto.setRevisionId(Objects.toString(objects[25], null));
                    return dto;
                })
                .collect(Collectors.toList());
        return bedCleansingRequestAuditDtoList;
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
