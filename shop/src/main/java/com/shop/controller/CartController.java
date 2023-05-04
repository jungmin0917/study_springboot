package com.shop.controller;

import com.shop.dto.CartDetailDTO;
import com.shop.dto.CartItemDTO;
import com.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니에 장바구니 상품 추가하는 메소드. 이것도 REST 방식으로 한다
    @PostMapping(value = "/cart")
    @ResponseBody
    public ResponseEntity order(@RequestBody @Valid CartItemDTO cartItemDTO, BindingResult bindingResult, Principal principal){

        // CartItemDTO 검증에 실패했을 경우
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            // 필드마다 에러가 난 경우 String에 추가함
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long cartItemId; // 일단 먼저 선언만 해 놓음

        try {
            // 장바구니에 장바구니 상품 DTO 및 email을 이용해 추가하고 cartItemId를 반환받는다.
            cartItemId = cartService.addCart(cartItemDTO, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // 여기까지 정상적으로 왔다면, 장바구니에 상품 추가 성공적으로 완료한 것
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    // 장바구니 페이지
    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model){
        List<CartDetailDTO> cartDetailList = cartService.getCartList(principal.getName());

        model.addAttribute("cartItems", cartDetailList);

        return "cart/cartList";
    }

    // 장바구니 상품의 수량을 업데이트하는 메소드
    // HTTP 메소드에서 요청된 자원의 일부를 업데이트할 때 PATCH 방식을 사용한다. 장바구니 상품의 수량만 업데이트하면 되기 때문에 @PatchMapping을 사용한다.
    @PatchMapping(value = "/cartItem/{cartItemId}")
    @ResponseBody
    public ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal){

        if(count <= 0){ // 요청한 개수가 0개 이하일 때
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        }else if(!cartService.validateCartItem(cartItemId, principal.getName())){ // 요청자와 상품 주인이 일치하지 않을 때
            return new ResponseEntity<String>("수정 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    // 장바구니 상품을 장바구니에서 삭제하는 메소드
    // HTTP 메소드에서 요청된 자원을 삭제할 때는 DELETE 방식을 사용한다. 장바구니 상품을 삭제하기 때문에 @DeleteMapping을 사용한다.
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    @ResponseBody
    public ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal){

        // 당연히 여기서도 해당 요청을 한 사용자와 장바구니 상품 주인이 같은지 확인한다.
        if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId); // 해당 장바구니 상품을 삭제함
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}







