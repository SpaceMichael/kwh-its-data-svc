package hk.org.ha.kcc.its.service;

import hk.org.ha.kcc.its.dto.UserAccessDto;

import java.util.List;

public interface UserAccessService {

    // get all user access dto
    List<UserAccessDto> getAllDto();
    // get by Id
    UserAccessDto getDtoById(String id);
    // update by Id
    UserAccessDto updateById(String id, UserAccessDto userAccessDto);
    // delete by Id
    void deleteById(String id);
    // create dto
    UserAccessDto create(UserAccessDto userAccessDto);
}
