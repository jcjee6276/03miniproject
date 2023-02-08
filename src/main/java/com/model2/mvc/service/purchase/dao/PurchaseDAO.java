package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {
	public PurchaseDAO() {
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql = "insert into transaction (TRAN_NO,PROD_NO,buyer_id,payment_option,receiver_name,\r\n"
				+ "			receiver_phone, demailaddr,dlvy_request,ORDER_DATA,DLVY_DATE) values(seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,SYSDATE,?)";
		//System.out.println("prodNO가져오니"+purchase.getPurchaseProd().getProdNo());
		System.out.println("userid가져오니"+purchase.getBuyer().getUserId());
		
		
		System.out.println("=====db=========");
		
		//ProductVO productVO = new ProductVO();
		
		//UserVO userVO = null;
		//UserService uservice = new UserServiceImpl();
		//userVO = uservice.getUser("userId");
		//ProductService proservice = new ProductServiceImpl();
		//productVO = proservice.getProduct(productVO.getProdNo());
		//PurchaseVO pchaseVO = new PurchaseVO(); 
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		
		
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getDivyDate());
		stmt.executeUpdate();
		
		con.close();
		
	}
	public Purchase findPurchase(int tranNo) throws Exception{
		/*Connection con = DBUtil.getConnection();
		String sql = "select * from product where prod_No=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO = null;
		PurchaseVO purchaseVO = null;
		UserVO userVO = new UserVO();
		UserService service = new UserServiceImpl();
		//userVO = service.getUser();
		System.out.println(userVO);
		
		while(rs.next()) {
			productVO = new ProductVO();
			purchaseVO = new PurchaseVO();
			//userVO = new UserVO();
			UserService service2 = new UserServiceImpl();
			userVO = service2.getUser(rs.getString("USER_ID"));
			purchaseVO.setBuyer(userVO);
			purchaseVO.setReceiverName(rs.getString("USER_NAME"));
			
			purchaseVO.setReceiverPhone(rs.getString("USER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("ADDR"));
			
		}
		con.close();
		//System.out.println(productVO.toString());
		//purchaseVO.setPurchaseProd(productVO);
		System.out.println(userVO);
		System.out.println(purchaseVO);
		
		
		//System.out.println("반반무마니:"+purchaseVO.getPurchaseProd());*/
		return null;
	}
	
	
}
