package com.joonsang.backendtech.repository.item;

import com.joonsang.backendtech.domain.item.ItemForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemFormRepository {

    private static final Map<Long, ItemForm> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public ItemForm save(ItemForm itemForm) {
        itemForm.setId(++sequence);
        store.put(itemForm.getId(), itemForm);
        return itemForm;
    }

    public ItemForm findById(Long id) {
        return store.get(id);
    }

    public List<ItemForm> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemFormId, ItemForm updateParam) {
        ItemForm findItemForm = findById(itemFormId);
        findItemForm.setItemName(updateParam.getItemName());
        findItemForm.setPrice(updateParam.getPrice());
        findItemForm.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
