package com.shop.service;

import com.shop.model.Cart;
import com.shop.model.CartItem;
import com.shop.model.Product;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;


    /** create - 상품 등록 */
    public void saveItem(Product product, MultipartFile imgFile) throws Exception {
        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        // UUID 를 이용하여 파일명 새로 생성
        // UUID - 서로 다른 객체들을 구별하기 위한 클래스
        UUID uuid = UUID.randomUUID();

        String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName);

        imgFile.transferTo(saveFile);

        product.setImgName(imgName);
        product.setImgPath("/files/" + imgName);

        productRepository.save(product);

//        메인페이지에서 상품 사진이 나와야 하기 때문에 main.html에서 thymeleaf를 이용하여 렌더링 합니다.
//        th:src="@{${item.getImgPath()}}"
    }

    /** read */
    public Product itemView(Integer id) {   // 개별 상품 읽기
        return productRepository.findById(id).get();
    }

    public List<Product> allItemView() {    // 전체 상품 리스트 읽기
        return productRepository.findAll();
    }

    /** update */
    // 상품 수정
    @Transactional
    public void itemModify(Product product, Integer id, MultipartFile imgFile) throws Exception {

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + imgFile.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        imgFile.transferTo(saveFile);

        Product update = productRepository.findItemById(id);
        update.setName(product.getName());
        update.setContent(product.getContent());
        update.setPrice(product.getPrice());
        update.setStock(product.getStock());
        update.setSoldout(product.isSoldout());
        update.setImgName(fileName);
        update.setImgPath("/files/"+fileName);
        productRepository.save(update);
    }


    /** delete
     상품 삭제 */
    @Transactional
    public void itemDelete(Integer id) {
        // cartItem 중에 해당 id 를 가진 item 찾기
        List<CartItem> items = cartService.findCartItemByItemId(id);

        for(CartItem item : items) {
            cartService.cartItemDelete(item.getId());
        }

        productRepository.deleteById(id);
    }

}
