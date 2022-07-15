package com.heech.hellcoding.core.category.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Enumerated(EnumType.STRING)
    private ServiceSection serviceSection;

    @Column(name = "category_serial_number")
    private int serialNumber;
    @Column(name = "category_name")
    private String name;
    @Column(name = "category_content")
    private String content;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();
}
