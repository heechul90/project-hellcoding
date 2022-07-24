package com.heech.hellcoding;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.AuthorCode;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.shop.item.album.domain.Album;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.item.movie.domain.Movie;
import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final InitService initService;

    @PostConstruct
    public void init() {

        initService.categoryInit();
        initService.memberInit();
        initService.itemInit();
        initService.questionnaireInit();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        @PersistenceContext
        private final EntityManager em;

        private static Category getCategory(Category parent, ServiceName serviceName, Integer serialNumber, String name, String content) {
            Category rootCategory = Category.createCategoryBuilder()
                    .parent(parent)
                    .serviceName(serviceName)
                    .serialNumber(serialNumber)
                    .name(name)
                    .content(content)
                    .build();
            return rootCategory;
        }

        public void memberInit() {
            Mobile mobile = new Mobile("010", "4250", "4296");
            Address address = new Address("12345", "Sejong", "hanuridaero");

            Member admin = Member.createMemberBuilder()
                    .name("스프링관리자")
                    .loginId("admin")
                    .password("1234")
                    .email("admin@spring.com")
                    .birthDate("19901009")
                    .authorCode(AuthorCode.ROLE_ADMIN)
                    .genderCode(GenderCode.M)
                    .mobile(mobile)
                    .address(address)
                    .build();

            Member spring = Member.createMemberBuilder()
                    .name("스프링유저")
                    .loginId("spring")
                    .password("1234")
                    .email("spring@spring.com")
                    .birthDate("19901009")
                    .authorCode(AuthorCode.ROLE_USER)
                    .genderCode(GenderCode.M)
                    .mobile(mobile)
                    .address(address)
                    .build();

            em.persist(admin);
            em.persist(spring);
        }

        public void itemInit() {
            Book book1 = Book.createBookBuilder()
                    .name("연금술사")
                    .title("2022년 베스트 셀러!")
                    .content("재미있는 책입니다.")
                    .price(10800)
                    .stockQuantity(100)
                    .category(em.find(Category.class, 1L))
                    .author("파울로 코엘료")
                    .isbn(UUID.randomUUID().toString().toUpperCase())
                    .build();
            em.persist(book1);

            Book book2 = Book.createBookBuilder()
                    .name("갈매기의 꿈")
                    .title("2021년 베스트 셀러!")
                    .content("갈매기 맛있다")
                    .price(7200)
                    .stockQuantity(200)
                    .category(em.find(Category.class, 1L))
                    .author("리처드 바크")
                    .isbn(UUID.randomUUID().toString().toUpperCase())
                    .build();
            em.persist(book2);

            Album album1 = Album.builder()
                    .name("서쪽하늘 7집")
                    .title("타이틀 서쪽하늘")
                    .content("서쪽하늘에")
                    .price(20000)
                    .stockQuantity(20)
                    .category(em.find(Category.class, 2L))
                    .artist("이승철")
                    .build();
            em.persist(album1);

            Movie movie1 = Movie.builder()
                    .name("범죄도시2")
                    .title("베트남에 가자")
                    .content("죽고싶으면 와라")
                    .price(14000)
                    .stockQuantity(20)
                    .category(em.find(Category.class, 3L))
                    .director("show box")
                    .actor("마동석")
                    .build();
            em.persist(movie1);
        }

        public void categoryInit() {
            Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "도서", "도서 카테고리");
            Category albumCategory = getCategory(null, ServiceName.SHOP, 1, "음반", "음반 카테고리");
            Category movieCategory = getCategory(null, ServiceName.SHOP, 2, "영화", "영화 카테고리");
            Category developCategory = getCategory(bookCategory, ServiceName.SHOP, 0, "개발", "개발 카테고리");
            Category languageCategory = getCategory(bookCategory, ServiceName.SHOP, 1, "언어", "언어 카테고리");
            Category kpopCategory = getCategory(albumCategory, ServiceName.SHOP, 0, "케이팝", "케이팝 카테고리");
            Category balladCategory = getCategory(albumCategory, ServiceName.SHOP, 1, "발라드", "발라드 카테고리");
            Category javaCategory = getCategory(developCategory, ServiceName.SHOP, 0, "JAVA", "JAVA 카테고리");

            em.persist(bookCategory);
            em.persist(albumCategory);
            em.persist(movieCategory);
            em.persist(developCategory);
            em.persist(languageCategory);
            em.persist(kpopCategory);
            em.persist(balladCategory);
            em.persist(javaCategory);
        }

        public void questionnaireInit() {
            List<Option> options1 = new ArrayList<>();
            Option option1 = new Option(1, "test_content1");
            Option option2 = new Option(2, "test_content2");
            options1.add(option1);
            options1.add(option2);

            List<Option> options2 = new ArrayList<>();
            Option option3 = new Option(3, "test_content3");
            Option option4 = new Option(4, "test_content4");
            options2.add(option3);
            options2.add(option4);

            List<Question> questions = new ArrayList<>();
            Question question1 = Question.createQuestionBuilder()
                    .title("test_title1")
                    .questionOrder(1)
                    .setting(Setting.OBJECTIVE)
                    .options(options1)
                    .build();

            Question question2 = Question.createQuestionBuilder()
                    .title("test_title2")
                    .questionOrder(2)
                    .setting(Setting.OBJECTIVE)
                    .options(options2)
                    .build();
            questions.add(question1);
            questions.add(question2);

            Questionnaire questionnaire = new Questionnaire(
                    "test_title",
                    "test_description",
                    "Y",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    questions
            );
            em.persist(questionnaire);
        }
    }
}
