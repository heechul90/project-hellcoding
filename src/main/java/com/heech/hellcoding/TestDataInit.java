package com.heech.hellcoding;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.category.repository.CategoryRepository;
import com.heech.hellcoding.core.shop.item.album.domain.Album;
import com.heech.hellcoding.core.shop.item.album.repository.AlbumRepository;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.item.book.repository.BookRepository;
import com.heech.hellcoding.core.shop.item.movie.domain.Movie;
import com.heech.hellcoding.core.shop.item.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final BookRepository bookRepository;
    private final AlbumRepository albumRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    //@PostConstruct
    public void init() {
        Book book1 = new Book("연금술사", "2022년 베스트 셀러!", "재미있는 책입니다.", 10800, 100, "파울로 코엘료", UUID.randomUUID().toString().toUpperCase());
        bookRepository.save(book1);

        Book book2 = Book.createBuilder()
                .name("갈매기의 꿈")
                .title("2021년 베스트 셀러!")
                .content("갈매기 맛있다")
                .price(7200)
                .stockQuantity(200)
                .author("리처드 바크")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();
        bookRepository.save(book2);

        Album album1 = Album.builder()
                .name("서쪽하늘 7집")
                .title("타이틀 서쪽하늘")
                .content("서쪽하늘에")
                .price(20000)
                .stockQuantity(20)
                .artist("이승철")
                .build();
        albumRepository.save(album1);

        Movie movie1 = Movie.builder()
                .name("범죄도시2")
                .title("베트남에 가자")
                .content("죽고싶으면 와라")
                .price(14000)
                .stockQuantity(20)
                .director("show box")
                .actor("마동석")
                .build();
        movieRepository.save(movie1);

        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("12345", "Sejong", "hanuridaero");

        Member admin = Member.builder()
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

        memberRepository.save(admin);

        Member spring = Member.builder()
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
        memberRepository.save(spring);

        Category bookCategory = Category.createRootCategoryBuilder()
                .name("도서")
                .build();

        Category albumCategory = Category.createRootCategoryBuilder()
                .name("음반")
                .build();

        Category movieCategory = Category.createRootCategoryBuilder()
                .name("영화")
                .build();
        categoryRepository.save(bookCategory);
        categoryRepository.save(albumCategory);
        categoryRepository.save(movieCategory);
    }

}
