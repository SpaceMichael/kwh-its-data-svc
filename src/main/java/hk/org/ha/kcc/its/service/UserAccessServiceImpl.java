package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.UserAccessDto;
import hk.org.ha.kcc.its.mapper.UserAccessMapper;
import hk.org.ha.kcc.its.model.UserAccess;
import hk.org.ha.kcc.its.repository.UserAccessRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;


@Service
@Transactional
public class UserAccessServiceImpl implements UserAccessService {

    private final UserAccessRepository userAccessRepository;

    private final UserAccessMapper userAccessMapper;

    public UserAccessServiceImpl(UserAccessRepository userAccessRepository, UserAccessMapper userAccessMapper) {
        this.userAccessRepository = userAccessRepository;
        this.userAccessMapper = userAccessMapper;
    }

    @Override
    public List<UserAccessDto> getAllDto() {
        // use findAll() to get all user access
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        // use UserAccess mapper to List<Dto>
        return userAccessList.stream().map(userAccessMapper::UserAccessToUserAccessDto).collect(Collectors.toList());
    }

    @Override
    public UserAccessDto getDtoById(String id) {
        UserAccess userAccess = userAccessRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserAccess not found: " + id));
        return userAccessMapper.UserAccessToUserAccessDto(userAccess);
    }

    @Override
    public UserAccessDto updateById(String id, UserAccessDto userAccessDto) {
        // get by id , if is not exist, throw ResourceNotFoundException
        UserAccess userAccess = userAccessRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserAccess not found"));
        BeanUtils.copyProperties(userAccessDto, userAccess, getNullPropertyNames(userAccessDto));
        // save and return
        return userAccessMapper.UserAccessToUserAccessDto(userAccessRepository.save(userAccess));
    }

    @Override
    public void deleteById(String id) {
        try {
            this.userAccessRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("BedCleansingRequest not found");
        }
    }

    @Override
    public UserAccessDto create(UserAccessDto userAccessDto) {
        // create new user access
        UserAccess userAccess = userAccessMapper.UserAccessDtoToUserAccess(userAccessDto);
        // save and return
        return userAccessMapper.UserAccessToUserAccessDto(userAccessRepository.save(userAccess));
    }
}
