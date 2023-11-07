package hk.org.ha.kcc.its.service;

import java.util.List;


import hk.org.ha.kcc.its.dto.MenuServiceDto;
import hk.org.ha.kcc.its.mapper.MenuServiceMapper;
import org.springframework.stereotype.Service;
import hk.org.ha.kcc.its.repository.MenuRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuServiceImpl implements hk.org.ha.kcc.its.service.MenuService {

    private final MenuRepository menuRepository;

    private final MenuServiceMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, MenuServiceMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    public MenuServiceDto create(MenuServiceDto menuServiceDto) {
        hk.org.ha.kcc.its.model.MenuService menuService = this.menuMapper.MenuDtoToMenu(menuServiceDto);
        // this.menuRepository.save(menu);
        return this.menuMapper.MenuToMenuDto(this.menuRepository.save(menuService));
    }

    @Override
    public List<MenuServiceDto> getAllDto() {
        List<hk.org.ha.kcc.its.model.MenuService> menuServiceList = this.menuRepository.findAll();
        return menuServiceList.stream().map(menuMapper::MenuToMenuDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public MenuServiceDto getDtoById(Integer id) {
        hk.org.ha.kcc.its.model.MenuService menuService = this.menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return this.menuMapper.MenuToMenuDto(menuService);
    }

    @Override
    public MenuServiceDto updateById(Integer id, MenuServiceDto menuServiceDto) {
        hk.org.ha.kcc.its.model.MenuService menuService = this.menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        if (menuServiceDto.getTitle() != null) {
            menuService.setTitle(menuServiceDto.getTitle());
        }
        if (menuServiceDto.getDescription() != null) {
            menuService.setDescription(menuServiceDto.getDescription());
        }
        if (menuServiceDto.getRemarks() != null) {
            menuService.setRemarks(menuServiceDto.getRemarks());
        }
        if (menuServiceDto.getIcon() != null) {
            menuService.setIcon(menuServiceDto.getIcon());
        }
        if (menuServiceDto.getUrl() != null) {
            menuService.setUrl(menuServiceDto.getUrl());
        }
        if (menuServiceDto.getActiveFlag() != null) {
            menuService.setActiveFlag(menuServiceDto.getActiveFlag());
        }
        if (menuServiceDto.getEnable() != null) {
            menuService.setEnable(menuServiceDto.getEnable());
        }
        if (menuServiceDto.getBarcodeKey() != null) {
            menuService.setBarcodeKey(menuServiceDto.getBarcodeKey());
        }
        menuService.setActiveFlag(menuServiceDto.getActiveFlag());
        return this.menuMapper.MenuToMenuDto(this.menuRepository.save(menuService));

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
            this.menuRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Menu not found");
        }
    }
}
