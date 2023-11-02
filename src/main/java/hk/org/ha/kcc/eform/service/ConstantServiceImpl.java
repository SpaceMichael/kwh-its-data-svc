package hk.org.ha.kcc.eform.service;

import hk.org.ha.kcc.eform.dto.ConstantDto;
import hk.org.ha.kcc.eform.mapper.ConstantMapper;
import hk.org.ha.kcc.eform.model.Constant;
import hk.org.ha.kcc.eform.repository.ConstantRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ConstantServiceImpl implements ConstantService{

    private final ConstantRepository constantRepository;

    private final ConstantMapper constantMapper;

    public ConstantServiceImpl(ConstantRepository constantRepository, ConstantMapper constantMapper) {
        this.constantRepository = constantRepository;
        this.constantMapper = constantMapper;
    }

    @Override
    public ConstantDto create(ConstantDto constantDto) {
        //Constant constant = this.constantMapper.ConstantDtoToConstant(constantDto);
        Constant constant =this.constantRepository.save(constantMapper.ConstantDtoToConstant(constantDto));
        return this.constantMapper.ConstantToConstantDto(constant);
    }

    @Override
    public List<ConstantDto> getAllDto() {
        return this.constantRepository.findAll().stream().map(this.constantMapper::ConstantToConstantDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ConstantDto getDtoById(Integer id) {
        Constant constant = this.constantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Constant not found"));
        return this.constantMapper.ConstantToConstantDto(constant);
    }

    @Override
    public ConstantDto update(Integer id, ConstantDto constantDto) {
        Constant constant = this.constantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Constant not found"));
        constant.setConstantType(constantDto.getConstantType());
        constant.setConstantValue(constantDto.getConstantValue());
        //this.constantRepository.save(constant);
        return this.constantMapper.ConstantToConstantDto(this.constantRepository.save(constant));
    }

    @Override
    public void delete(Integer id) {
        try {
            this.constantRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Constant not found");
        }
    }
}
