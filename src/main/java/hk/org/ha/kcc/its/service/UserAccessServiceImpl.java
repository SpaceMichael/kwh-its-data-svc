package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.UserAccessDto;
import hk.org.ha.kcc.its.mapper.UserAccessMapper;
import hk.org.ha.kcc.its.model.UserAccess;
import hk.org.ha.kcc.its.repository.UserAcessRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hk.org.ha.kcc.its.util.BeanUtilsCustom.getNullPropertyNames;


@Service
@Transactional
public class UserAccessServiceImpl implements UserAccessService {

    private final UserAcessRespository userAcessRespository;

    private final UserAccessMapper userAccessMapper;

    public UserAccessServiceImpl(UserAcessRespository userAcessRespository, UserAccessMapper userAccessMapper) {
        this.userAcessRespository = userAcessRespository;
        this.userAccessMapper = userAccessMapper;
    }

    @Override
    public List<UserAccessDto> getAllDto() {
        // use findAll() to get all user access
        List<UserAccess> userAccessList = userAcessRespository.findAll();
        // use UserAccess mapper to List<Dto>
        return userAccessList.stream().map(userAccessMapper::UserAccessToUserAccessDto).collect(Collectors.toList());
    }

    @Override
    public UserAccessDto getDtoById(String id) {
        UserAccess userAccess = userAcessRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserAccess not found"));
        return userAccessMapper.UserAccessToUserAccessDto(userAccess);
    }

    @Override
    public UserAccessDto updateById(String id, UserAccessDto userAccessDto) {
        // get by id , if is not exist, throw ResourceNotFoundException
        UserAccess userAccess = userAcessRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserAccess not found"));
        // update by dto
        BeanUtils.copyProperties(userAccessDto, userAccess, getNullPropertyNames(userAccessDto));
        // save
        return userAccessMapper.UserAccessToUserAccessDto(userAcessRespository.save(userAccess));
    }

    @Override
    public void deleteById(String id) {
        try {
            this.userAcessRespository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("BedCleansingRequest not found");
        }
    }

    @Override
    public UserAccessDto create(UserAccessDto userAccessDto) {
        // create new user access
        UserAccess userAccess = userAccessMapper.UserAccessDtoToUserAccess(userAccessDto);
        // save and return
        return userAccessMapper.UserAccessToUserAccessDto(userAcessRespository.save(userAccess));
    }
}
