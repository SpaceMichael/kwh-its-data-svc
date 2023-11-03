package hk.org.ha.kcc.its.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import hk.org.ha.kcc.its.dto.ConstantDto;
import hk.org.ha.kcc.its.mapper.ConstantMapper;
import hk.org.ha.kcc.its.model.Constant;
import hk.org.ha.kcc.its.repository.ConstantRepository;

@Service
@Transactional
public class ConstantServiceImpl implements ConstantService {

  private final ConstantRepository constantRepository;

  private final ConstantMapper constantMapper;

  public ConstantServiceImpl(ConstantRepository constantRepository, ConstantMapper constantMapper) {
    this.constantRepository = constantRepository;
    this.constantMapper = constantMapper;
  }

  @Override
  public ConstantDto create(ConstantDto constantDto) {
    // Constant constant = this.constantMapper.ConstantDtoToConstant(constantDto);
    Constant constant =
        this.constantRepository.save(constantMapper.ConstantDtoToConstant(constantDto));
    return this.constantMapper.ConstantToConstantDto(constant);
  }

  @Override
  public List<ConstantDto> getAllDto() {
    return this.constantRepository.findAll().stream()
        .map(this.constantMapper::ConstantToConstantDto)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public ConstantDto getDtoById(Integer id) {
    Constant constant = this.constantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Constant not found"));
    return this.constantMapper.ConstantToConstantDto(constant);
  }

  @Override
  public ConstantDto update(Integer id, ConstantDto constantDto) {
    Constant constant = this.constantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Constant not found"));
    constant.setConstantType(constantDto.getConstantType());
    constant.setConstantValue(constantDto.getConstantValue());
    // this.constantRepository.save(constant);
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
