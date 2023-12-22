package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.EamDto;
import hk.org.ha.kcc.its.mapper.EamMapper;
import hk.org.ha.kcc.its.model.Eam;
import hk.org.ha.kcc.its.repository.EamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EamServiceImpl implements EamService {
    private final EamRepository eamRepository;
    private final EamMapper eamMapper;

    public EamServiceImpl(EamRepository eamRepository, EamMapper eamMapper) {
        this.eamRepository = eamRepository;
        this.eamMapper = eamMapper;
    }

    @Override
    public List<EamDto> getAllDto() {
        List<Eam> eamList = eamRepository.findAll().stream().filter(Eam::getActiveFlag).collect(java.util.stream.Collectors.toList());
        return eamList.stream().map(eamMapper::EamToEamDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public EamDto getDtoById(Integer id) {
        Eam eam = eamRepository.findById(id).filter(Eam::getActiveFlag).orElseThrow(() -> new ResourceNotFoundException("Eam not found"));
        return eamMapper.EamToEamDto(eam);
    }

    @Override
    public EamDto create(EamDto eamDto) {
        Eam eam = eamMapper.EamDtoToEam(eamDto);
        // check activeFlag
        if (eam.getActiveFlag() == null) {
            eam.setActiveFlag(true);
        }
        //eamRepository.save(eam);
        return eamMapper.EamToEamDto(eamRepository.save(eam));
    }

    @Override
    public EamDto updateById(Integer id, EamDto eamDto) {
        Eam eam = eamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Eam not found"));
        // set value if not null
        if (eamDto.getSerNo() != null) {
            eam.setSerNo(eamDto.getSerNo());
        }
        if (eamDto.getModel() != null) {
            eam.setModel(eamDto.getModel());
        }
        if (eamDto.getBelongTo() != null) {
            eam.setBelongTo(eamDto.getBelongTo());
        }
        if (eamDto.getType() != null) {
            eam.setType(eamDto.getType());
        }
        // save and return
        return eamMapper.EamToEamDto(eamRepository.save(eam));

    }

    @Override
    public void deleteById(Integer id) {
        try {
            this.eamRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Eam not found");
        }
    }
}
