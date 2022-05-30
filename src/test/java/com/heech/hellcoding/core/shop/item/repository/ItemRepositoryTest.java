package com.heech.hellcoding.core.shop.item.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.movie.domain.Movie;
import com.heech.hellcoding.core.shop.item.dto.ItemSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void findItemsTest() throws Exception{
        //given
        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("연금");

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<Item> content = itemRepository.findItems(condition, pageRequest);

        //then
        assertThat(content.getContent()).extracting("name").containsExactly("연금술사");
    }

    @Test
    public void saveBookTest() throws Exception{
        //given
        Book book = Book.builder()
                .author("자청")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();
        book.createItem(
                "역행자",
                "라이프해커 자청의 인생 역주행 공식",
                "돈·시간·운명으로부터 완전한 자유를 얻는 7단계 인생 공략집",
                15750,
                200
        );

        //when
        Book savedBook = itemRepository.save(book);
        em.flush();
        em.clear();

        //then
        Book findBook = (Book) itemRepository.findById(savedBook.getId()).orElse(null);
        assertThat(findBook.getAuthor()).isEqualTo("자청");
    }

    @Test
    public void saveAlbumTest() throws Exception{
        //given
        /*Album album = Album.builder()
                .artist("이승철")
                .build();
        album.createItem(
                "이승철 7집",
                "서쪽하늘",
                "타이틀곡이 서쪽하늘입니다.",
                5000,
                15
        );

        //when
        Album savedAlbum = itemRepository.save(album);
        em.flush();
        em.clear();*/

        //then
        /*Album findAlbum = (Album) itemRepository.findById(savedAlbum.getId()).orElse(null);
        assertThat(findAlbum.getTitle()).isEqualTo("서쪽하늘");*/
    }

    @Test
    public void saveMovieTest() throws Exception{
        //given
        Movie movie = Movie.builder()
                .director("쇼박스")
                .actor("마동석")
                .build();
        movie.createItem(
                "범죄도시2",
                "범인 잡으러 베트남에 가다",
                "죽고 싶으면 와라",
                21000,
                50
        );

        //when
        Movie savedMovie = itemRepository.save(movie);

        //then
        Movie findMovie = (Movie) itemRepository.findById(savedMovie.getId()).orElse(null);
        assertThat(findMovie.getActor()).isEqualTo("마동석");
    }

    @Test
    public void findByCustomTest() throws Exception{
        //given
        Book book = Book.builder().author("리처드 바크").isbn(UUID.randomUUID().toString().toUpperCase()).build();
        book.createItem(
                "갈매기의 꿈2",
                "2022년 베스트 셀러!",
                "300만이 선택한 책입니다.",
                7200,
                300
        );
        Book savedBook = itemRepository.save(book);

        //when
        List<Item> resultList1 = itemRepository.findByName("갈매기의 꿈");
        List<Book> resultList2 = itemRepository.findByAuthor("리처드 바크");

        //then
        assertThat(resultList1.size()).isEqualTo(1);
        assertThat(resultList2).extracting("name").containsExactly("갈매기의 꿈", "갈매기의 꿈2");
    }

}