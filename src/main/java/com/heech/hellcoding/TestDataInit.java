package com.heech.hellcoding;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.shop.category.domain.Category;
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

        private final EntityManager em;

        private static Category getRootCategory(String name, Integer order) {
            Category rootCategory = Category.createRootCategoryBuilder()
                    .name(name)
                    .categoryOrder(order)
                    .build();
            return rootCategory;
        }

        private static Category getChildCategory(Category parent, String name, Integer order) {
            Category childCategory = Category.createChildCategoryBuilder()
                    .parent(parent)
                    .name(name)
                    .categoryOrder(order)
                    .build();
            return childCategory;
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
                    .genderCode(GenderCode.M)
                    .mobile(mobile)
                    .address(address)
                    .signupDate(LocalDateTime.now())
                    .signinDate(LocalDateTime.now())
                    .build();

            Member spring = Member.createMemberBuilder()
                    .name("스프링유저")
                    .loginId("spring")
                    .password("1234")
                    .email("spring@spring.com")
                    .birthDate("19901009")
                    .genderCode(GenderCode.M)
                    .mobile(mobile)
                    .address(address)
                    .signupDate(LocalDateTime.now())
                    .signinDate(LocalDateTime.now())
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
            Category bookCategory = getRootCategory("도서", 1);
            Category albumCategory = getRootCategory("음반", 2);
            Category movieCategory = getRootCategory("영화", 3);
            Category developCategory = getChildCategory(bookCategory, "개발", 1);
            Category languageCategory = getChildCategory(bookCategory, "언어", 2);
            Category kpopCategory = getChildCategory(albumCategory, "케이팝", 1);
            Category balladCategory = getChildCategory(albumCategory, "발라드", 2);

            em.persist(bookCategory);
            em.persist(albumCategory);
            em.persist(movieCategory);
            em.persist(developCategory);
            em.persist(languageCategory);
            em.persist(kpopCategory);
            em.persist(balladCategory);
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
