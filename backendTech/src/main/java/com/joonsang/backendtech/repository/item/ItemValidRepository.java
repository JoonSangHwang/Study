package com.joonsang.backendtech.repository.item;

import com.joonsang.backendtech.domain.item.ItemValid;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemValidRepository {

    private static final Map<Long, ItemValid> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public ItemValid save(ItemValid itemValid) {
        itemValid.setId(++sequence);
        store.put(itemValid.getId(), itemValid);
        return itemValid;
    }

    public ItemValid findById(Long id) {
        return store.get(id);
    }

    public List<ItemValid> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemValidId, ItemValid updateParam) {
        ItemValid findItemValid = findById(itemValidId);
        findItemValid.setItemName(updateParam.getItemName());
        findItemValid.setPrice(updateParam.getPrice());
        findItemValid.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
