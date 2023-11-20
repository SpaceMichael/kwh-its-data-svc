package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.EformDto;
import hk.org.ha.kcc.its.mapper.EformMapper;
import hk.org.ha.kcc.its.model.Eform;
import hk.org.ha.kcc.its.repository.EformRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final EformRepository eformRepository;

    private final EformMapper eformMapper;

    public MenuServiceImpl(EformRepository eformRepository, EformMapper eformMapper) {
        this.eformRepository = eformRepository;
        this.eformMapper = eformMapper;
    }

    @Override
    public EformDto create(EformDto eformDto) {
        Eform eform = this.eformMapper.EformDtoToEform(eformDto);
        if (eformDto.getActiveFlag() == null) {
            eform.setActiveFlag(true);
        }
        if (eformDto.getEnable() == null) {
            eform.setEnable(true);
        }
        return this.eformMapper.EformToEformDto(this.eformRepository.save(eform));
    }

    @Override
    public List<EformDto> getAllDto() {
        // filter the active flag is true
        List<Eform> eformList = this.eformRepository.findAll();
        return eformList.stream().map(eformMapper::EformToEformDto).filter(EformDto::getActiveFlag)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public EformDto getDtoById(Integer id) {
        Eform eform = this.eformRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return this.eformMapper.EformToEformDto(eform);
    }

    @Override
    public EformDto updateById(Integer id, EformDto eformDto) {
        Eform eform = this.eformRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        if (eformDto.getTitle() != null) {
            eform.setTitle(eformDto.getTitle());
        }
        if (eformDto.getDescription() != null) {
            eform.setDescription(eformDto.getDescription());
        }
        if (eformDto.getRemarks() != null) {
            eform.setRemarks(eformDto.getRemarks());
        }
        if (eformDto.getIcon() != null) {
            eform.setIcon(eformDto.getIcon());
        }
        if (eformDto.getUrl() != null) {
            eform.setUrl(eformDto.getUrl());
        }
        if (eformDto.getActiveFlag() != null) {
            eform.setActiveFlag(eformDto.getActiveFlag());
        }
        if (eformDto.getEnable() != null) {
            eform.setEnable(eformDto.getEnable());
        }
        if (eformDto.getBarcodeKey() != null) {
            eform.setBarcodeKey(eformDto.getBarcodeKey());
        }
        if (eformDto.getUrl2() != null) {
            eform.setUrl2(eformDto.getUrl2());
        }
        if (eformDto.getTitle2() != null) {
            eform.setTitle2(eformDto.getTitle2());
        }
        //menu.setActiveFlag(menuDto.getActiveFlag());
        return this.eformMapper.EformToEformDto(this.eformRepository.save(eform));

        /*
         * private String title; // Bed Cleansing private String description; // e.g "Request form" ,
         * "Request form, Tracker" etc private String remarks; private String icon; // e. g
         * "https://kwh-its-eform-svc-kccclinical-dev.tstcld61.server.ha.org.hk/iconBedCleansing.png"
         * private String url; // e.g
         * "https://kwh-its-eform-app-kccclinical-dev.tstcld61.server.ha.org.hk/BedCleansingRequest
         * private Boolean activeFlag;
         */

    }

    @Override
    public void deleteById(Integer id) {
        try {
            this.eformRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Menu not found");
        }
    }
}
