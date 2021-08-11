package com.joonsang.backendtech.validation;

import com.joonsang.backendtech.domain.Item;
import com.joonsang.backendtech.domain.ItemValid;
import com.joonsang.backendtech.domain.SaveCheck;
import com.joonsang.backendtech.domain.UpdateCheck;
import com.joonsang.backendtech.repository.ItemValidRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v8/items")
public class ValidationItemControllerV8 {

    private final ItemValidRepository itemValidRepository;

    @Autowired
    ValidationItemControllerV8(ItemValidRepository itemValidRepository, ItemValidator itemValidator) {
        this.itemValidRepository = itemValidRepository;
    }

    @GetMapping
    public String items(Model model) {
        List<ItemValid> items = itemValidRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v8/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemValid item = itemValidRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v8/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemValid());
        return "validation/v8/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated(value = SaveCheck.class) @ModelAttribute ItemValid item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000)
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
        }

        // 검증 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v8/addForm";     // addForm.html
        }

        // 비즈니스 로직
        ItemValid savedItem = itemValidRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v8/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemValid item = itemValidRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v8/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated(value = UpdateCheck.class) @ModelAttribute ItemValid item, BindingResult bindingResult) {

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000)
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
        }

        // 검증 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v8/addForm";     // addForm.html
        }

        itemValidRepository.update(itemId, item);
        return "redirect:/validation/v8/items/{itemId}";
    }

    private boolean hasError(Map<String, String> errors) {
        return !errors.isEmpty();
    }
}
