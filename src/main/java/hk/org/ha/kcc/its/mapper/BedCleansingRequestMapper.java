package hk.org.ha.kcc.its.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import hk.org.ha.kcc.its.dto.BedCleansingRequestDto;
import hk.org.ha.kcc.its.model.BedCleansingRequest;


@Mapper(builder = @Builder(disableBuilder = true))
public interface BedCleansingRequestMapper {

    BedCleansingRequestDto BedCleansingRequestToBedCleansingRequestDto(
            BedCleansingRequest bedCleansingRequest);

    BedCleansingRequest BedCleansingRequestDtoToBedCleansingRequest(
            BedCleansingRequestDto bedCleansingRequestDto);
}
