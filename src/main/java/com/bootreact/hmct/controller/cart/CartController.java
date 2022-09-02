package com.bootreact.hmct.controller.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootreact.hmct.dto.ResponseDTO;
import com.bootreact.hmct.entity.Cart;
import com.bootreact.hmct.entity.User;
import com.bootreact.hmct.jwt.JwtTokenProvider;
import com.bootreact.hmct.service.cart.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

//	//장바구니 제품추가 (POST)
//	void addCart(Product product) {}
//
//	//장바구니 제품삭제 (DELETE)
//	void deleteCart(Product product) {}
//
//	//장바구니 제품수정(수량 등) (GET, POST)
//	void updateCart(Product product) {}
//
	//장바구니 제품리스트 조회 (POST)
	@PostMapping("/getCartList")
    public ResponseEntity<?> getCartList(@RequestBody User user){
		//'조회'라고 해서 무조건 'get'요청을 보내는 것은 아님.
		//data: { userId: "gogo" }를 보내주기 위해서는 'post'요청을 보내야 함.
		//매개변수에는 userId가 아니라 User엔티티를 넣어주어야 했음.
		//이 때 @authenticationprincipal 어노테이션을 붙일 경우 로그인한 사용자일 때만 getCartList()를 사용 가능함
		//추후에 로그인한 경우에만 장바구니 조회할 수 있게 수정할 때 어노테이션도 바꿔줘야 함
		try {
			//직접 쿼리작성 없이 JPA로 조인&셀렉트하고 싶은 경우 아래와 같이 함
    		List<Cart> cartList = cartService.getCartList(user.getUserId());
    		ResponseDTO<Cart> response = new ResponseDTO<>();
    		response.setData(cartList);
    		return ResponseEntity.ok().body(response);
    		
    		//직접 작성한 쿼리로 매퍼 사용하고 싶은 경우 아래와 같이 함
//    		List<Map<String, Object>> cartList = cartService.getCartMapList(user.getUserId());    		
//    		ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
//    		response.setData(cartList);
//    		return ResponseEntity.ok().body(response);
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    		ResponseDTO<Cart> response = new ResponseDTO<>();
    		response.setError(e.getMessage());
    		return ResponseEntity.badRequest().body(response);		
    	}
	}
	
}
