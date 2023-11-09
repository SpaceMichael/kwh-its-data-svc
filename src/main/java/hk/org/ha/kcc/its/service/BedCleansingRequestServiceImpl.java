package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.BedCleansingRequestAuditDto;
import hk.org.ha.kcc.its.model.Menu;
import hk.org.ha.kcc.its.repository.MenuRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.its.mapper.BedCleansingRequestMapper;
import hk.org.ha.kcc.its.model.BedCleansingRequest;
import hk.org.ha.kcc.its.repository.BedCleansingServiceRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BedCleansingRequestServiceImpl implements BedCleansingRequestService {

    private final BedCleansingServiceRepository bedCleansingServiceRepository;
    private final BedCleansingRequestMapper bedCleansingRequestMapper;
    private final MenuRepository menuRepository;

    public BedCleansingRequestServiceImpl(BedCleansingServiceRepository bedCleansingServiceRepository
            , BedCleansingRequestMapper bedCleansingRequestMapper
            , MenuRepository menuRepository) {
        this.bedCleansingServiceRepository = bedCleansingServiceRepository;
        this.bedCleansingRequestMapper = bedCleansingRequestMapper;
        this.menuRepository = menuRepository;
    }

    @Override
    public BedCleansingRequestDto create(BedCleansingRequestDto bedCleansingRequestDto) {
        BedCleansingRequest bedCleansingRequest = this.bedCleansingRequestMapper
                .BedCleansingRequestDtoToBedCleansingRequest(bedCleansingRequestDto);
        // check menu id is correct or not
        if (bedCleansingRequestDto.getMenuId() != null) {
            Menu menu = this.menuRepository.findById(bedCleansingRequestDto.getMenuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Id" + bedCleansingRequestDto.getMenuId() + "Menu not found"));
            bedCleansingRequest.assignMenu(menu);
        } // this id can check menu table and get the bed cleansing , id and insert to the bed cleansing table menu id?

        // the status should be "PENDING" when create the request
        bedCleansingRequest.setStatus("PENDING");

        if (bedCleansingRequestDto.getWholeBed() == null) {
            bedCleansingRequest.setWholeBed(false);
        }
        if (bedCleansingRequestDto.getActiveFlag() == null) {
            bedCleansingRequest.setActiveFlag(true);
        }
        BedCleansingRequest bedCleansingRequest1 = this.bedCleansingServiceRepository.save(bedCleansingRequest);
        return this.bedCleansingRequestMapper
                .BedCleansingRequestToBedCleansingRequestDto(bedCleansingRequest1);
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

        /*List<BedCleansingRequest> bedCleansingRequestList2 = bedCleansingRequestList.stream()
                // if completedStatus is true , filter status = "COMPLETED", else filter status != "COMPLETED"
                .filter(request -> completedStatus == null || (request.getStatus() != null && completedStatus && request.getStatus().equalsIgnoreCase("COMPLETED")) || (request.getStatus() != null && !completedStatus && !request.getStatus().equalsIgnoreCase("COMPLETED")))
                .collect(Collectors.toList());*/

        // use stream and map and filter to get the list of BedCleansingRequestDto
        return bedCleansingRequestList.stream()
                .map(bedCleansingRequestMapper::BedCleansingRequestToBedCleansingRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public BedCleansingRequestDto getDtoById(String id) {
        // Use findById() to get the record from the table. If not found, throw
        // ResourceNotFoundException
        BedCleansingRequest bedCleansingRequest =
                this.bedCleansingServiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));
        return this.bedCleansingRequestMapper
                .BedCleansingRequestToBedCleansingRequestDto(bedCleansingRequest);
    }

    @Override
    public BedCleansingRequestDto updateById(String id, BedCleansingRequestDto bedCleansingRequestDto) {
        // get by id , if is not exist, throw ResourceNotFoundException
        BedCleansingRequest bedCleansingRequest =
                this.bedCleansingServiceRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));

        // update by dto
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

        if (bedCleansingRequestDto.getWholeBed() != null) {
            bedCleansingRequest.setWholeBed(bedCleansingRequestDto.getWholeBed());
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
        // set all status to upperCase
        if (bedCleansingRequestDto.getStatus() != null) {
            bedCleansingRequest.setStatus(bedCleansingRequestDto.getStatus().toUpperCase());
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
        return this.bedCleansingRequestMapper
                .BedCleansingRequestToBedCleansingRequestDto(bedCleansingRequest);
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
    public List<BedCleansingRequestAuditDto> getAllDtoByBCId(String Id) {
        List<Object[]> objectList = this.bedCleansingServiceRepository.getAllDtoByBCId(Id);
        // mapp the objectList to BedCleansingRequestAuditDto
        List<BedCleansingRequestAuditDto> bedCleansingRequestAuditDtoList = new ArrayList<>();
        for (Object[] object : objectList) {
            BedCleansingRequestAuditDto bedCleansingRequestAuditDto = new BedCleansingRequestAuditDto();
            if (object[0] != null) { // if bcId is null, set it to false
                bedCleansingRequestAuditDto.setBcId(object[0].toString());
            }
            //bedCleansingRequestAuditDto.setBcId(object[0].toString());
            if (object[1] != null) { // if revId is null, set it to false
                bedCleansingRequestAuditDto.setRevId(object[1].toString());
            }
            //bedCleansingRequestAuditDto.setRevId(object[1].toString());
            if (object[2] != null) { // if revType is null, set it to false
                bedCleansingRequestAuditDto.setRevType(object[2].toString());
            }
            //bedCleansingRequestAuditDto.setRevType(object[2].toString());
            if (object[3] != null) { // if cleaner is null, set it to false
                bedCleansingRequestAuditDto.setCleaner(object[3].toString());
            }
            //bedCleansingRequestAuditDto.setCleaner(object[3].toString());
            if (object[4] != null) { // if activeFlag is null, set it to false
                bedCleansingRequestAuditDto.setActiveFlag(Boolean.parseBoolean(object[4].toString()));
            }
            //bedCleansingRequestAuditDto.setActiveFlag(Boolean.parseBoolean(object[4].toString()));
            if (object[5] != null) { // if bedNo is null, set it to false
                bedCleansingRequestAuditDto.setBedNo(object[5].toString());
            }
            //bedCleansingRequestAuditDto.setBedNo(object[5].toString());
            if (object[6] != null) { // if bedType is null, set it to false
                bedCleansingRequestAuditDto.setBedType(object[6].toString());
            }
            if (object[7] != null) { // if cleaningProcess is null, set it to false
                bedCleansingRequestAuditDto.setCleaningProcess(object[7].toString());
            }
            if (object[8] != null) { // if cubicleNo is null, set it to false
                bedCleansingRequestAuditDto.setCubicleNo(object[8].toString());
            }
            //bedCleansingRequestAuditDto.setBedType(object[6].toString());
            //bedCleansingRequestAuditDto.setCleaningProcess(object[7].toString());
            //bedCleansingRequestAuditDto.setCubicleNo(object[8].toString());
            if (object[9] != null) { // if deptCode is null, set it to false
                bedCleansingRequestAuditDto.setDeptCode(object[9].toString());
            }
            //bedCleansingRequestAuditDto.setDeptCode(object[9].toString());
            if (object[10] != null) { // if detergent is null, set it to false
                bedCleansingRequestAuditDto.setDetergent(object[10].toString());
            }
            if (object[11] != null) { // if hospitalCode is null, set it to false
                bedCleansingRequestAuditDto.setHospitalCode(object[11].toString());
            }
            //bedCleansingRequestAuditDto.setDetergent(object[10].toString());
            //bedCleansingRequestAuditDto.setHospitalCode(object[11].toString());
            if (object[12] != null) { // if menuId is null, set it to false
                bedCleansingRequestAuditDto.setMenuId(Integer.parseInt(object[12].toString()));
            }
            //bedCleansingRequestAuditDto.setMenuId(Integer.parseInt(object[12].toString()));
            if (object[13] != null) { // if remarks is null, set it to false
                bedCleansingRequestAuditDto.setRemarks(object[13].toString());
            }
            //bedCleansingRequestAuditDto.setRemarks(object[13].toString());
            if (object[14] != null) { // if requestorContactNo is null, set it to false
                bedCleansingRequestAuditDto.setRequestorContactNo(Integer.parseInt(object[14].toString()));
            }
            //bedCleansingRequestAuditDto.setRequestorContactNo(Integer.parseInt(object[14].toString()));
            if (object[15] != null) { // if status is null, set it to false
                bedCleansingRequestAuditDto.setStatus(object[15].toString());
            }
            //bedCleansingRequestAuditDto.setStatus(object[15].toString());
            if (object[16] != null) { // if wardCode is null, set it to false
                bedCleansingRequestAuditDto.setWardCode(object[16].toString());
            }
            bedCleansingRequestAuditDto.setWardCode(object[16].toString());
            if (object[17] != null) { // if wholeBedCleansing is null, set it to false
                bedCleansingRequestAuditDto.setWholeBedCleansing(Boolean.parseBoolean(object[17].toString()));
            }
            //bedCleansingRequestAuditDto.setWholeBedCleansing(Boolean.parseBoolean(object[17].toString()));
            if (object[18] != null) { // if requestorId is null, set it to false
                bedCleansingRequestAuditDto.setRequestorId(object[18].toString());
            }
            //bedCleansingRequestAuditDto.setRequestorId(object[18].toString());
            if (object[19] != null) { // if revDetailId is null, set it to false
                bedCleansingRequestAuditDto.setRevDetailId(object[19].toString());
            }
            //bedCleansingRequestAuditDto.setRevDetailId(object[19].toString());
            if (object[20] != null) { // if action is null, set it to false
                bedCleansingRequestAuditDto.setAction(object[20].toString());
            }
            //bedCleansingRequestAuditDto.setAction(object[20].toString());
            if (object[21] != null) { // if actionDateTime is null, set it to false
                bedCleansingRequestAuditDto.setActionDateTime(object[21].toString());
            }
            //bedCleansingRequestAuditDto.setActionDateTime(object[21].toString());
            if (object[22] != null) { // if recordId is null, set it to false
                bedCleansingRequestAuditDto.setRecordId(object[22].toString());
            }
            //bedCleansingRequestAuditDto.setRecordId(object[22].toString());
            if (object[23] != null) { // if tableName is null, set it to false
                bedCleansingRequestAuditDto.setTableName(object[23].toString());
            }
            //bedCleansingRequestAuditDto.setTableName(object[23].toString());
            if (object[24] != null) { // if username is null, set it to false
                bedCleansingRequestAuditDto.setUsername(object[24].toString());
            }
            //bedCleansingRequestAuditDto.setUsername(object[24].toString());
            if (object[25] != null) { // if revisionId is null, set it to false
                bedCleansingRequestAuditDto.setRevisionId(object[25].toString());
            }
            //bedCleansingRequestAuditDto.setRevisionId(object[25].toString());
            // add bedCleansingRequestAuditDtoList
            bedCleansingRequestAuditDtoList.add(bedCleansingRequestAuditDto);
        }
        //return bedCleansingRequestAuditDtoList;
        return bedCleansingRequestAuditDtoList;
    }
}
