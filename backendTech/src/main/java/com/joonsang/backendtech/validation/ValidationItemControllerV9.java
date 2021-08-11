package com.joonsang.backendtech.validation;

import com.joonsang.backendtech.domain.ItemForm;
import com.joonsang.backendtech.domain.SaveCheck;
import com.joonsang.backendtech.domain.UpdateCheck;
import com.joonsang.backendtech.repository.ItemFormRepository;
import com.joonsang.backendtech.validation.form.ItemSaveForm;
import com.joonsang.backendtech.validation.form.ItemUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v9/items")
public class ValidationItemControllerV9 {

    private final ItemFormRepository itemFormRepository;

    @Autowired
    ValidationItemControllerV9(ItemFormRepository itemFormRepository) {
        this.itemFormRepository = itemFormRepository;
    }

    @GetMapping
    public String items(Model model) {
        List<ItemForm> items = itemFormRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v9/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemForm item = itemFormRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v9/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemForm());
        return "validation/v9/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm itemSaveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (itemSaveForm.getPrice() != null && itemSaveForm.getQuantity() != null) {
            int resultPrice = itemSaveForm.getPrice() * itemSaveForm.getQuantity();
            if (resultPrice < 10000)
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
        }

        // 검증 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v9/addForm";     // addForm.html
        }

        ItemForm item = new ItemForm();
        item.setItemName(itemSaveForm.getItemName());
        item.setPrice(itemSaveForm.getPrice());
        item.setQuantity(itemSaveForm.getQuantity());

        // 비즈니스 로직
        ItemForm savedItem = itemFormRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v9/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemForm item = itemFormRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v9/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm itemUpdateForm, BindingResult bindingResult) {

        if (itemUpdateForm.getPrice() != null && itemUpdateForm.getQuantity() != null) {
            int resultPrice = itemUpdateForm.getPrice() * itemUpdateForm.getQuantity();
            if (resultPrice < 10000)
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
        }

        // 검증 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v9/addForm";     // addForm.html
        }

        ItemForm item = new ItemForm();
        item.setItemName(itemUpdateForm.getItemName());
        item.setPrice(itemUpdateForm.getPrice());
        item.setQuantity(itemUpdateForm.getQuantity());

        itemFormRepository.update(itemId, item);
        return "redirect:/validation/v9/items/{itemId}";
    }

    private boolean hasError(Map<String, String> errors) {
        return !errors.isEmpty();
    }
}
