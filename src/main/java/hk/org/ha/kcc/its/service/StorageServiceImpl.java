package hk.org.ha.kcc.its.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import hk.org.ha.kcc.its.dto.ResultDto;
import hk.org.ha.kcc.its.dto.StorageListRespDto;
import hk.org.ha.kcc.its.dto.TimeDto;

@Service
public class StorageServiceImpl implements hk.org.ha.kcc.its.service.StorageService {
    @Override
    public StorageListRespDto getStorageList() {

        StorageListRespDto storageListRespDto = new StorageListRespDto();
        storageListRespDto.setTotal(2);
        TimeDto timeDto = new TimeDto();
        timeDto.setStart(1692758526.195635);
        timeDto.setFinish(1692758526.320605);
        timeDto.setDuration(0.1249699592590332);
        timeDto.setProcessing(0.10502386093139648);
        timeDto.setDate_start("2023-08-23T10:42:06+08:00");
        timeDto.setDate_finish("2023-08-23T10:42:06+08:00");
        storageListRespDto.setTime(timeDto);
        List<ResultDto> results = new ArrayList<>();
        results.add(ResultDto.builder().ID("95691").NAME("23-08-2023 Britix24 Testing").CODE(null)
                .MODULE_ID("disk").ENTITY_TYPE("group").ENTITY_ID("2917").ROOT_OBJECT_ID("472279").build());
        results.add(ResultDto.builder().ID("95572").NAME("KWH @Work Britix24 API Testing").CODE(null)
                .MODULE_ID("disk").ENTITY_TYPE("group").ENTITY_ID("2823").ROOT_OBJECT_ID("471066").build());
        storageListRespDto.setResults(results);
        /*
         * { "ID": "95691", "NAME": "23-08-2023 Britix24 Testing", "CODE": null, "MODULE_ID": "disk",
         * "ENTITY_TYPE": "group", "ENTITY_ID": "2917", "ROOT_OBJECT_ID": "472279" }, { "ID": "95572",
         * "NAME": "KWH @Work Britix24 API Testing", "CODE": null, "MODULE_ID": "disk", "ENTITY_TYPE":
         * "group", "ENTITY_ID": "2823", "ROOT_OBJECT_ID": "471066" }
         */
        return storageListRespDto;
    }
}
