package com.heech.hellcoding.core.shop;

import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.delivery.repository.DeliveryQueryRepository;
import com.heech.hellcoding.core.shop.delivery.repository.DeliveryRepository;
import com.heech.hellcoding.core.shop.delivery.service.DeliveryService;
import com.heech.hellcoding.core.shop.item.album.repository.AlbumQueryRepository;
import com.heech.hellcoding.core.shop.item.album.repository.AlbumRepository;
import com.heech.hellcoding.core.shop.item.album.service.AlbumService;
import com.heech.hellcoding.core.shop.item.book.repository.BookQueryRepository;
import com.heech.hellcoding.core.shop.item.book.repository.BookRepository;
import com.heech.hellcoding.core.shop.item.book.service.BookService;
import com.heech.hellcoding.core.shop.item.info.repository.ItemRepository;
import com.heech.hellcoding.core.shop.item.movie.repository.MovieQueryRepository;
import com.heech.hellcoding.core.shop.item.movie.repository.MovieRepository;
import com.heech.hellcoding.core.shop.item.movie.service.MovieService;
import com.heech.hellcoding.core.shop.order.repository.OrderQueryRepository;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import com.heech.hellcoding.core.shop.order.service.OrderService;
import com.heech.hellcoding.core.shop.orderItem.repository.OrderItemQueryRepository;
import com.heech.hellcoding.core.shop.orderItem.repository.OrderItemRepository;
import com.heech.hellcoding.core.shop.orderItem.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class ShopTestConfig {

    @PersistenceContext EntityManager em;

    @Autowired DeliveryRepository deliveryRepository;

    @Autowired OrderRepository orderRepository;

    @Autowired MemberRepository memberRepository;

    @Autowired ItemRepository itemRepository;

    @Autowired OrderItemRepository orderItemRepository;

    @Autowired AlbumRepository albumRepository;

    @Autowired BookRepository bookRepository;

    @Autowired MovieRepository movieRepository;

    @Bean
    public DeliveryQueryRepository deliveryQueryRepository() {
        return new DeliveryQueryRepository(em);
    }

    @Bean
    public DeliveryService deliveryService() {
        return new DeliveryService(deliveryRepository, deliveryQueryRepository());
    }

    @Bean
    public OrderQueryRepository orderQueryRepository() {
        return new OrderQueryRepository(em);
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(orderRepository, orderQueryRepository(), memberRepository, itemRepository);
    }

    @Bean
    public OrderItemQueryRepository orderItemQueryRepository() {
        return new OrderItemQueryRepository(em);
    }

    @Bean
    public OrderItemService orderItemService() {
        return new OrderItemService(orderItemRepository, orderItemQueryRepository(), orderRepository, itemRepository);
    }

    @Bean
    public AlbumQueryRepository albumQueryRepository() {
        return new AlbumQueryRepository(em);
    }

    @Bean
    public AlbumService albumService() {
        return new AlbumService(albumRepository, albumQueryRepository());
    }

    @Bean
    public BookQueryRepository bookQueryRepository() {
        return new BookQueryRepository(em);
    }

    @Bean
    public BookService bookService() {
        return new BookService(bookRepository, bookQueryRepository());
    }

    @Bean
    public MovieQueryRepository movieQueryRepository() {
        return new MovieQueryRepository(em);
    }

    @Bean
    public MovieService movieService() {
        return new MovieService(movieRepository, movieQueryRepository());
    }
}
