package com.example.spring_mvc_data_crud.service;

import com.example.spring_mvc_data_crud.domain.Item;
import com.example.spring_mvc_data_crud.exception.ObjectNotFoundException;
import com.example.spring_mvc_data_crud.repos.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepo itemRepo;

    public List<Item> findAllBySaleId(Long id) {
        return itemRepo.findAllBySale_Id(id);
    }

    public boolean addItem(Item item) {
        itemRepo.save(item);
        return true;
    }

    public Item findById(Long id) {
        return itemRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Item not found( id " + id + ")"));
    }

    public Item update(Item item, Long id) {
        return itemRepo.findById(id)
                .map(ItemRepo -> {
                    ItemRepo.setId(item.getId());
                    ItemRepo.setComment(item.getComment());
                    ItemRepo.setProduct(item.getProduct());
                    ItemRepo.setSale(item.getSale());
                    ItemRepo.setQuantity(item.getQuantity());
                    itemRepo.save(ItemRepo);
                    return ItemRepo;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Item not found( id " + id + ")"));
    }

    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }
}
