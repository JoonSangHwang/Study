package com.joonsang.backendtech.validation;

import com.joonsang.backendtech.domain.item.Item;
import com.joonsang.backendtech.repository.item.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v1/items")
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    ValidationItemControllerV1(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    /**
     * v1 의 문제점
     * - 뷰 템플릿에서의 중복 처리가 많다.
     * - 타입 오류 처리가 안된다. 숫자 타입에 문자가 들어오면 오류가 발생하는데 이는 Controller 진입 전에 예외 발생으로 400 예외이다.
     * - 예외가 발생하더라도 숫자 타입이므로 화면을 재로딩하면 문자는 사라져 사용자 본인이 어떤 내용을 입력했는지 모른다.
     * - 결국 고객이 입력한 값도 어딘가에 별도로 관리 되어야 한다.
     */
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {

        // [S] 검증 로직
        Map<String, String> errors = new HashMap<>();

        if (!StringUtils.hasText(item.getItemName()))
            errors.put("itemName", "상품 이름은 필수입니다.");

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000)
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");

        if (item.getQuantity() == null || item.getQuantity() > 9999)
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000)
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
        }

        if (hasError(errors)) {
            log.info("errors = {}", errors);
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";     // addForm.html
        }
        // [E] 검증 로직

        // 비즈니스 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }

    private boolean hasError(Map<String, String> errors) {
        return !errors.isEmpty();
    }
}
