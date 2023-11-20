package hk.org.ha.kcc.its.mapper;

import hk.org.ha.kcc.its.dto.UserAccessDto;
import hk.org.ha.kcc.its.model.UserAccess;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface UserAccessMapper {

    UserAccessDto UserAccessToUserAccessDto(UserAccess userAccess);

    UserAccess UserAccessDtoToUserAccess(UserAccessDto userAccessDto);
}
