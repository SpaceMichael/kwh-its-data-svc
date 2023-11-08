package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.model.Menu;
import hk.org.ha.kcc.its.repository.MenuRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.its.mapper.BedCleansingRequestMapper;
import hk.org.ha.kcc.its.model.BedCleansingRequest;
import hk.org.ha.kcc.its.repository.BedCleansingServiceRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BedCleansingRequestServiceImpl implements BedCleansingRequestService {


    private final BedCleansingServiceRepository bedCleansingServiceRepository;

    private final BedCleansingRequestMapper bedCleansingRequestMapper;

    private final MenuRepository menuRepository;

    public BedCleansingRequestServiceImpl(BedCleansingServiceRepository bedCleansingServiceRepository,
                                          BedCleansingRequestMapper bedCleansingRequestMapper, MenuRepository menuRepository) {
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
        } // check menu table and get teh bed cleansing , id and insert to the bed cleansing table menu id?

        if (bedCleansingRequestDto.getStatus() == null) {
            bedCleansingRequest.setStatus("Pending");
        }
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
    public List<BedCleansingRequestDto> getAllDto(String ward, String cubicle, String bed) {
        // use findAll() to get all the records from the table and filter ward, cubicle, bed if they are not null, and request.ward, cubicle, bed !=null and activeflag is true
        List<BedCleansingRequest> bedCleansingRequestList = bedCleansingServiceRepository.findAll().stream()
                .filter(BedCleansingRequest -> BedCleansingRequest.getActiveFlag() != null && BedCleansingRequest.getActiveFlag())
                .filter(request -> ward == null || (request.getWard() != null && request.getWard().equals(ward)))
                .filter(request -> cubicle == null || (request.getCubicle() != null && request.getCubicle().equals(cubicle)))
                .filter(request -> bed == null || (request.getBedNo() != null && request.getBedNo().equals(bed)))
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
        BedCleansingRequest bedCleansingRequest =
                this.bedCleansingServiceRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("BedCleansingRequest not found"));
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

        if (bedCleansingRequestDto.getStatus() != null) {
            bedCleansingRequest.setStatus(bedCleansingRequestDto.getStatus());
        }

  /*
    if (bedCleansingRequestDto.getRequestorName() != null) {
      bedCleansingRequest.setRequestorName(bedCleansingRequestDto.getRequestorName());
    }*/
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
}
