package com.heech.hellcoding.item.service;

import com.heech.hellcoding.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class itemService {

    private final ItemRepository itemRepository;
}
