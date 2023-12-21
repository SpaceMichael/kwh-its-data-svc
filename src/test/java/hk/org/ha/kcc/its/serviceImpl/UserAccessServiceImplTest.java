package hk.org.ha.kcc.its.serviceImpl;

import hk.org.ha.kcc.its.dto.UserAccessDto;
import hk.org.ha.kcc.its.model.UserAccess;
import hk.org.ha.kcc.its.repository.UserAcessRespository;
import hk.org.ha.kcc.its.service.UserAccessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UserAccessServiceImplTest {

    @Autowired
    private UserAccessService userAccessService;

    @MockBean
    private UserAcessRespository userAcessRespository;

    @Test
    void getDtoByIdMock() {
        UserAccess mockUserAcess = new UserAccess();
        mockUserAcess.setCorpId("ttk799");
        mockUserAcess.setFormId("99");
        mockUserAcess.setActiveFlag(true);

        when(userAcessRespository.findById("ttk799")).thenReturn(java.util.Optional.of(mockUserAcess));
        // service check and get dto
        UserAccessDto userAccessDto = userAccessService.getDtoById("ttk799");
        // notnull check and assert
        assertNotNull(userAccessDto);
        assertEquals("ttk799", userAccessDto.getCorpId());
        assertEquals("99", userAccessDto.getFormId());
    }

/*
    @Test
    void getAllDto(){
        List<UserAccessDto> userAccessDtoList = userAccessService.getAllDto();
        System.out.println(userAccessDtoList); // why is zero?
    }
*/

    @Test
    void createTestMock() {
        UserAccessDto mockUserAcessDto = new UserAccessDto();
        mockUserAcessDto.setCorpId("ttk799");
        mockUserAcessDto.setFormId("99");
        mockUserAcessDto.setActiveFlag(true);
        // Mock the behavior of userAcessRespository.save()
        when(userAcessRespository.save(any(UserAccess.class))).thenAnswer(invocation -> {
            UserAccess savedUserAccess = invocation.getArgument(0);
            savedUserAccess.setCorpId("ttk799");
            savedUserAccess.setFormId("99");
            savedUserAccess.setActiveFlag(true);
            return savedUserAccess;
        });

        UserAccessDto userAccessDto = userAccessService.create(mockUserAcessDto);
        // check and assert
        assertNotNull(userAccessDto);
        assertEquals("ttk799", userAccessDto.getCorpId());
        assertEquals("99", userAccessDto.getFormId());
    }

  /*  @Test
    void createTestNoMock(){
        // given
        UserAccessDto userAccessDto = new UserAccessDto();
        userAccessDto.setCorpId("abc123");
        userAccessDto.setFormId("88");
        userAccessDto.setActiveFlag(true);
        // when real save
        UserAccessDto createdUserAccessDto = userAccessService.create(userAccessDto);
        // then
        assertNotNull(createdUserAccessDto);
        assertEquals("abc123", createdUserAccessDto.getCorpId());
        assertEquals("88", createdUserAccessDto.getFormId());
    }
*/
}
