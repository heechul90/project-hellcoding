package com.heech.hellcoding;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
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
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final BookRepository bookRepository;
    private final AlbumRepository albumRepository;
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Book book1 = new Book("연금술사", "2022년 베스트 셀러!", "재미있는 책입니다.", 10800, 100, "파울로 코엘료", UUID.randomUUID().toString().toUpperCase());
        bookRepository.save(book1);

        Book book2 = Book.builder()
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

        Member admin = new Member(
                "admin",
                "1234",
                "스프링관리자",
                "spring2@spring.com",
                "19901009",
                GenderCode.M,
                mobile,
                address,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        memberRepository.save(admin);

        Member spring = new Member(
                "spring",
                "1234",
                "스프링유저",
                "spring2@spring.com",
                "19901009",
                GenderCode.M,
                mobile,
                address,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        memberRepository.save(spring);
    }

}
