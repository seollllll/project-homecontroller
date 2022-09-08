package com.bootreact.hmct.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootreact.hmct.dto.OrderDTO;
import com.bootreact.hmct.dto.ResponseDTO;

import com.bootreact.hmct.entity.Cart;
import com.bootreact.hmct.entity.Delivery;
import com.bootreact.hmct.entity.Order;
import com.bootreact.hmct.service.order.OrderService;
import com.bootreact.hmct.service.user.UserService;

//주문, 주문취소, 반품, 교환, 환불 처리
@RestController
@RequestMapping("/api/order")
public class OrderController {
	
    @Autowired
	OrderService orderService;
    
    @Autowired
    UserService userService;
	
    //주문 목록 리스트
    @GetMapping("/getOrderList")
    public ResponseEntity<?> getOrderList(){
    	try {
    		List<Order> orderList = orderService.getOrderList();
    	
    		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
    		
    		//내가 가져오고싶은 정보들 
    		for(Order t: orderList) {
    			OrderDTO orderDTO = new OrderDTO();
    			
    			orderDTO.setOrderNo(t.getOrderNo());
    			orderDTO.setUser(t.getUser());  
    			orderDTO.setOrderStatus(t.getOrderStatus());
    			orderDTO.setOrderDate(t.getOrderDate());
    			orderDTO.setOrderMemo(t.getOrderMemo());
    			orderDTO.setOrderAmount(t.getOrderAmount());
    			orderDTO.setOrderDiscount(t.getOrderDiscount());
    			orderDTO.setOrderFee(t.getOrderFee());
    			
    			orderDTOList.add(orderDTO);	
    		}
    		ResponseDTO<OrderDTO> response = new ResponseDTO<>();
    		
    		response.setData(orderDTOList);
    		
    		return ResponseEntity.ok().body(response);
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    		ResponseDTO<OrderDTO> response = new ResponseDTO<>();
    		response.setError(e.getMessage());
    		return ResponseEntity.badRequest().body(response);		
    	}
    }
    
    //주문 조회
    @GetMapping("/viewOrder")
    public Map<String, Object> viewOrder(Order order){
    	try {
    		
    		Map<String, Object> orderDetail = orderService.viewOrder(order.getOrderNo());
    		
    		return orderDetail;
    	}catch(Exception e){
    		Map<String, Object> errorMap = new HashMap<String, Object>();
    		errorMap.put("error", e.getMessage());
    		return errorMap;
    	}
    }
    

	//주문 생성
	@PostMapping("/createOrder")
	public void createOrder(@RequestBody Map<String, String> paramMap) {
		try {
			System.out.println(paramMap.get("userId"));
			System.out.println(paramMap.get("productNo"));
			System.out.println(paramMap.get("commonCode"));
			
//			//Order 테이블에 주문 정보 인서트
//			//orderNo, orderDate는 자동생성
//			orderService.addOrder(paramMap.get("userId"),
//								  paramMap.get("orderAmount"), 
//								  paramMap.get("orderDiscount"),
//								  paramMap.get("orderFee"));
//			//OrderItem 테이블에 주문아이템 정보 인서트
//			orderService.addOrderItem();
//			//Dlvy 테이블에 배송정보 인서트
//			orderService.addDelibery();
//			//Pmt 테이블에 결제정보 인서트
//			orderService.addPayment();
		

    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
	}


}
