package com.shop.controller;

import com.shop.config.auth.PrincipalDetails;
import com.shop.model.Product;
import com.shop.model.User;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 등록 페이지 (GET)
    @GetMapping("/seller/item/new")
    public  String itemSaveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            model.addAttribute("user", principalDetails.getUser());

            return "상품등록 페이지입니다.";
            //return "/seller/itemForm";
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }


    // PostMapping은 front없이는 주석

    // 상품 등록 (POST 판매자만 가능)
    @PostMapping("/seller/item/new/pro")
    public String itemSave(Product product, @AuthenticationPrincipal PrincipalDetails principalDetails, MultipartFile imgFile) throws Exception {
        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN") || principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            product.setSeller(principalDetails.getUser());
            productService.saveItem(product, imgFile);

            return "redirect:/main";
        } else {
            return "redirect:/main";
        }
    }

    // 상품 수정 페이지 - 판매자만 가능
    @GetMapping("/item/modify/{id}")
    public String itemModifyForm(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            User user = productService.itemView(id).getSeller();
            // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 수정 가능
            if(user.getId() == principalDetails.getUser().getId()) {

                model.addAttribute("item", productService.itemView(id));
                model.addAttribute("user", principalDetails.getUser());

                return "/seller/itemModify";
            } else {
                return "redirect:/main";
            }
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }

    // 상품 수정 (POST) - 판매자만 가능
    @PostMapping("/item/modify/pro/{id}")
    public String itemModify(Product product, @PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails, MultipartFile imgFile) throws Exception{
        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            User user = productService.itemView(id).getSeller();

            if(user.getId() == principalDetails.getUser().getId()) {
                // 상품을 올린 판매자 id와 현재 로그인 중인 판매자의 id가 같아야 수정 가능
                productService.itemModify(product, id, imgFile);

                return "redirect:/main";
            } else {
                return "redirect:/main";
            }
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }

//     상품 상세 페이지
    @GetMapping("/seller/item/view/{id}")
    public  String itemView(Model model, @PathVariable("id") Integer id) {

        model.addAttribute("item", productService.itemView(id));
        return "상품 상세 페이지입니다.";
       // return "/seller/itemView";
    }


//     상품 삭제
    @GetMapping("/seller/item/delete/{id}")
    public String itemDelete(@PathVariable("id") Integer id) {

        productService.itemDelete(id);
        return "상품 삭제 성공";
        //return "/main";
    }
}
