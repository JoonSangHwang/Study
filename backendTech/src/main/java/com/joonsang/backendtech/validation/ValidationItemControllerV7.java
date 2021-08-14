package com.joonsang.backendtech.validation;

import com.joonsang.backendtech.domain.item.ItemValid;
import com.joonsang.backendtech.repository.item.ItemValidRepository;
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
@RequestMapping("/validation/v7/items")
public class ValidationItemControllerV7 {

    private final ItemValidRepository itemValidRepository;
    private final ItemValidator itemValidator;

    @Autowired
    ValidationItemControllerV7(ItemValidRepository itemValidRepository, ItemValidator itemValidator) {
        this.itemValidRepository = itemValidRepository;
        this.itemValidator = itemValidator;
    }

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);        // 검증기 등록 supports(Item.class)
    }

    @GetMapping
    public String items(Model model) {
        List<ItemValid> items = itemValidRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v7/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        ItemValid item = itemValidRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v7/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemValid());
        return "validation/v7/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute ItemValid item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        // 검증 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "validation/v7/addForm";     // addForm.html
        }

        // 비즈니스 로직
        ItemValid savedItem = itemValidRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v7/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        ItemValid item = itemValidRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v7/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemValid item) {
        itemValidRepository.update(itemId, item);
        return "redirect:/validation/v7/items/{itemId}";
    }

    private boolean hasError(Map<String, String> errors) {
        return !errors.isEmpty();
    }
}
