package com.shop.controller;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.shop.config.auth.PrincipalDetails;
import com.shop.model.Cart;
import com.shop.model.Product;
import com.shop.model.User;
import com.shop.repository.CartRepository;
import com.shop.repository.UserRepository;
import com.shop.service.CartService;
import com.shop.service.ProductService;
import com.shop.service.UserPageService;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserPageService userPageService;

    @Autowired
    private ProductService productService;

    @GetMapping({ "", "/" })
    public String index(Model model) {
        // 로그인을 안 한 경우
        List<Product> items = productService.allItemView();
        model.addAttribute("items", items);

        return "main";
    }

    @GetMapping("/main")
    public String mainPage(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
            // 판매자
            int sellerId = principalDetails.getUser().getId();
            List<Product> items = productService.allItemView();
            model.addAttribute("items", items);
            model.addAttribute("user", userPageService.findUser(sellerId));

            return "/main";
        } else {
            // 구매자
            int userId = principalDetails.getUser().getId();
            List<Product> items = productService.allItemView();
            model.addAttribute("items", items);
            model.addAttribute("user", userPageService.findUser(userId));

            return "main";
        }
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

//    @GetMapping("/user")
//    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
//        System.out.println("Principal : " + principal);
//        System.out.println("OAuth2 : "+principal.getUser().getProvider());
//        // iterator 순차 출력 해보기
//        Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
//        while (iter.hasNext()) {
//            GrantedAuthority auth = iter.next();
//            System.out.println(auth.getAuthority());
//        }
//        System.out.println(principal.getUsername());
//
//
//        return "유저 페이지입니다.";
//    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "어드민 페이지입니다.";
    }

    //@PostAuthorize("hasRole('ROLE_MANAGER')")
    //@PreAuthorize("hasRole('ROLE_MANAGER')")
    @Secured("ROLE_SELLER")
    @GetMapping("/seller")
    public @ResponseBody String seller() {
        return "seller 페이지입니다.";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest req) {
        String username = req.getParameter("username");

        HttpSession session = req.getSession();
        session.setAttribute("username", username);

        return "login";
    }

    @GetMapping("/signUp")
    public String signUp(User user) {
        return "signUp";
    }

    @PostMapping("/joinProc")
    public String joinProc(User user) {
        System.out.println("회원가입 진행 : " + user);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        userRepository.save(user);
        createCart(user);
        return "redirect:/";
    }

    public void createCart(User user){

        Cart cart = Cart.createCart(user);

        cartRepository.save(cart);
    }
}