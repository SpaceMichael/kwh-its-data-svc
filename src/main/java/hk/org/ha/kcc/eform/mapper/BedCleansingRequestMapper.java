package hk.org.ha.kcc.eform.mapper;

import hk.org.ha.kcc.eform.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.eform.model.BedCleansingRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(builder = @Builder(disableBuilder = true))
public interface BedCleansingRequestMapper {

    BedCleansingRequestDto BedCleansingRequestToBedCleansingRequestDto(BedCleansingRequest bedCleansingRequest);

    BedCleansingRequest BedCleansingRequestDtoToBedCleansingRequest(BedCleansingRequestDto bedCleansingRequestDto);
}
