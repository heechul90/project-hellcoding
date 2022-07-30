package com.heech.hellcoding.api.member;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.AuthorCode;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiMemberControllerIntegrationTest {

    //REQUEST_URL
    public static final String API_FIND_MEMBERS = "/api/members";
    public static final String API_FIND_MEMBER = "/api/members/{memberId}";
    public static final String API_SAVE_MEMBER = "/api/members";
    public static final String API_UPDATE_MEMBER = "/api/members/{memberId}";
    public static final String API_DELETE_MEMBER = "/api/members/{memberId}";

    //MEMBER_DATA
    public static final String NAME = "test_name";
    public static final String LOGIN_ID = "test_loginId";
    public static final String PASSWORD = "test_password";
    public static final String EMAIL = "test_email@mail.com";
    public static final String BIRTH_DATE = "19901009";
    public static final AuthorCode AUTHOR_CODE = AuthorCode.ROLE_USER;
    public static final GenderCode GENDER_CODE = GenderCode.F;
    public static final Mobile MOBILE = new Mobile("010", "4250", "4296");
    public static final Address ADDRESS = new Address("11111", "seoul", "601");

    //UPDATE_MEMBER
    public static final String UPDATE_NAME = "update_" + NAME;
    public static final String UPDATE_EMAIL = "update_" + EMAIL;
    public static final String UPDATE_BIRTH_DATE = "20001009";
    public static final String UPDATE_PHONE_NUMBER = "01064884296";
}
