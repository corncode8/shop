package com.shop.controller;


import com.shop.config.oauth.PrincipalDetails;
import com.shop.model.*;
import com.shop.service.CartService;
import com.shop.service.ProductService;
import com.shop.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.List;

//@RequiredArgsConstructor
@Controller
public class UserPageController {

    private UserPageService userPageService;
    private ProductService productService;
    private CartService cartService;
   @Transactional
   @PostMapping("/user/cart/checkout/{id}")
    public String cartCheckout(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
       // 로그인이 되어있는 유저의 id와 주문하는 id가 같아야 함
       if(principalDetails.getUser().getId() == id) {
           User user = userPageService.findUser(id);

           // 유저 카트 찾기
           Cart userCart = cartService.findUserCart(user.getId());

           // 유저 카트 안에 있는 상품들
           List<CartItem> userCartItems = cartService.allUserCartView(userCart);

           // 최종 결제 금액
           int totalPrice = 0;
           for (CartItem cartItem : userCartItems) {
               // 장바구니 안에 있는 상품의 재고가 없거나 재고보다 많이 주문할 경우
               if (cartItem.getProduct().getStock() == 0 || cartItem.getProduct().getStock() < cartItem.getCartCount()) {
                   return "redirect:/main";
               }
               totalPrice += cartItem.getCartCount() * cartItem.getProduct().getPrice();
           }

//           int userCoin = user.getCoin();
//           // 유저의 현재 잔액이 부족하다면
//           if (userCoin < totalPrice) {
//               return "redirect:/main";
//           } else {
//               // 유저 돈에서 최종 결제금액 빼야함
//               user.setCoin(user.getCoin() - totalPrice);
//
//               List<OrderItem> orderItemList = new ArrayList<>();
//
//               for (CartItem cartItem : userCartItems) {
//                   // 각 상품에 대한 판매자
//                   User seller = cartItem.getItem().getSeller();
//
//                   // 판매자 수익 증가
//                   seller.setCoin(seller.getCoin() + (cartItem.getCount() * cartItem.getItem().getPrice()));
//
//                   // 재고 감소
//                   cartItem.getItem().setStock(cartItem.getItem().getStock() - cartItem.getCount());
//
//                   // 상품 개별로 판매 개수 증가
//                   cartItem.getItem().setCount(cartItem.getItem().getCount() + cartItem.getCount());
//
//                   // sale, saleItem 에 담기
//                   SaleItem saleItem = saleService.addSale(cartItem.getItem().getId(), seller.getId(), cartItem);
//
//                   // order, orderItem 에 담기
//                   OrderItem orderItem = orderService.addCartOrder(cartItem.getItem().getId(), user.getId(), cartItem, saleItem);
//
//                   orderItemList.add(orderItem);
//               }
//
//               orderService.addOrder(user, orderItemList);
//
//               // 장바구니 상품 모두 삭제
//               cartService.allCartItemDelete(id);
//           }

           model.addAttribute("totalPrice", totalPrice);
           model.addAttribute("cartItems", userCartItems);
           model.addAttribute("user", userPageService.findUser(id));

           return "redirect:/user/cart/{id}";
       } else {
           return "redirect:/main";
       }
    }

}
