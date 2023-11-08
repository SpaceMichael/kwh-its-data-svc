package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.MenuDto;
import hk.org.ha.kcc.its.mapper.MenuMapper;
import hk.org.ha.kcc.its.model.Menu;
import hk.org.ha.kcc.its.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    public MenuServiceImpl(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    @Override
    public MenuDto create(MenuDto menuDto) {
        Menu menu = this.menuMapper.MenuDtoToMenu(menuDto);
        if (menuDto.getActiveFlag() == null) {
            menu.setActiveFlag(true);
        }
        if (menuDto.getEnable() == null) {
            menu.setEnable(true);
        }
        return this.menuMapper.MenuToMenuDto(this.menuRepository.save(menu));
    }

    @Override
    public List<MenuDto> getAllDto() {
        // filter the active flag is true
        List<Menu> menuList = this.menuRepository.findAll();
        return menuList.stream().map(menuMapper::MenuToMenuDto).filter(MenuDto::getActiveFlag)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public MenuDto getDtoById(Integer id) {
        Menu menu = this.menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return this.menuMapper.MenuToMenuDto(menu);
    }

    @Override
    public MenuDto updateById(Integer id, MenuDto menuDto) {
        Menu menu = this.menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        if (menuDto.getTitle() != null) {
            menu.setTitle(menuDto.getTitle());
        }
        if (menuDto.getDescription() != null) {
            menu.setDescription(menuDto.getDescription());
        }
        if (menuDto.getRemarks() != null) {
            menu.setRemarks(menuDto.getRemarks());
        }
        if (menuDto.getIcon() != null) {
            menu.setIcon(menuDto.getIcon());
        }
        if (menuDto.getUrl() != null) {
            menu.setUrl(menuDto.getUrl());
        }
        if (menuDto.getActiveFlag() != null) {
            menu.setActiveFlag(menuDto.getActiveFlag());
        }
        if (menuDto.getEnable() != null) {
            menu.setEnable(menuDto.getEnable());
        }
        if (menuDto.getBarcodeKey() != null) {
            menu.setBarcodeKey(menuDto.getBarcodeKey());
        }
        if (menuDto.getUrl2() != null) {
            menu.setUrl2(menuDto.getUrl2());
        }
        if (menuDto.getTitle2() != null) {
            menu.setTitle2(menuDto.getTitle2());
        }
        menu.setActiveFlag(menuDto.getActiveFlag());
        return this.menuMapper.MenuToMenuDto(this.menuRepository.save(menu));

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
