package com.shop.controller;

import com.shop.model.Product;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {
    private ProductService productService;

    // 상품 등록 페이지 (GET)
    @GetMapping("/manager/item/new")
    public @ResponseBody String itemSaveForm() {
        return "상품등록 페이지입니다.";
        //return "/seller/itemForm";
    }

    // 상품 등록 (POST)
//    @PostMapping("/manager//item/new/pro")
//    public String itemSave(Product item) {
//
//        productService.saveItem(item); return "/main";
//    }

//    상품 수정 페이지 (GET)
    @GetMapping("/manager/item/modify/{id}")
    public @ResponseBody String itemModifyForm(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("item", productService.itemView(id));
        return "상품수정 페이지입니다.";
        //return "/seller/itemModify";
    }

    // 상품 수정 (POST)
//    @PostMapping("/manager/item/modify/pro/{id}")
//    public String itemModify(Product item, @PathVariable("id") Integer id) {
//
//        productService.itemModify(item, id);
//
//        return "redirect:/main";
//    }

//     상품 상세 페이지
    @GetMapping("/manager/item/view/{id}")
    public @ResponseBody String itemView(Model model, @PathVariable("id") Integer id) {

        model.addAttribute("item", productService.itemView(id));
        return "상품 상세 페이지입니다.";
       // return "/seller/itemView";
    }


//     상품 삭제
    @GetMapping("/manager/item/delete/{id}")
    public @ResponseBody String itemDelete(@PathVariable("id") Integer id) {

        productService.itemDelete(id);
        return "상품 삭제 성공";
        //return "/main";
    }
}
