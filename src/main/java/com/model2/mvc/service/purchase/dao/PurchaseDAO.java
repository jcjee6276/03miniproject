package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {
	public PurchaseDAO() {
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("넘어온 code확인"+purchase.getTranCode());
		
		Connection con = DBUtil.getConnection();
		String sql = "update (select * from transaction) set TRAN_STATUS_CODE=? WHERE TRAN_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getTranNo());
		stmt.executeUpdate();
		
		System.out.println("update에서 확인"+purchase.getTranNo());
		System.out.println("update에서 확인2"+purchase);
		stmt.close();
		con.close();
		
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		Connection con = DBUtil.getConnection();
		String sql = "insert into transaction (TRAN_NO,PROD_NO,buyer_id,payment_option,receiver_name,\r\n"
				+ "			receiver_phone, demailaddr,dlvy_request,ORDER_DATA,DLVY_DATE) values(seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,SYSDATE,?)";
		//System.out.println("prodNO가져오니"+purchase.getPurchaseProd().getProdNo());
		System.out.println("userid가져오니"+purchase.getBuyer().getUserId());
		
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
		
		
		purchase.setTranCode("1");
		this.updateTranCode(purchase);
		
		
		con.close();
		
	}
	public Purchase findPurchase(int tranNo) throws Exception{
		Connection con = DBUtil.getConnection();
		
		String sql ="select * from transaction where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		ResultSet rs = stmt.executeQuery();
		Product product = new Product();
		Purchase purchase = null;
		
		while(rs.next()) {
			purchase = new Purchase();
			product = new Product();
			product.setProdNo(rs.getInt("Prod_no"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setPurchaseProd(product);
		}
		System.out.println("purchase prodNo"+purchase.getPurchaseProd().getProdNo());
		System.out.println("DAO Purchase는?"+purchase);
		con.close();
		stmt.close();
		rs.close();
		
		return purchase;
	}
	
	public Purchase updatePurchase(Purchase purchase) throws Exception {
		Connection con = DBUtil.getConnection();
		System.out.println("가져온 purchase"+purchase.getTranNo());
		
		//System.out.println(purchase.getTranNo());
		String sql = "update transaction set RECEIVER_Phone=?, DEMAILADDR=?, DLVY_REQUEST=?, DLVY_DATE=? where tran_no=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getReceiverPhone());
		stmt.setString(2, purchase.getDivyAddr());
		stmt.setString(3, purchase.getDivyRequest());
		stmt.setString(4, purchase.getDivyDate());
		stmt.setInt(5, purchase.getTranNo());
		stmt.executeUpdate();
		
		con.close();
		stmt.close();
		System.out.println("DB update"+purchase);
		
		return purchase;
	}
	
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		System.out.println("======db연결은해?");
		
		String sql = "select * from transaction ";
			sql += "where buyer_id='"+userId+"'";
		
		System.out.println("PurchaseDAO:: SQL :: "+sql);
		
		int totalcount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO:: totalcount :: "+totalcount);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		//System.out.println("eeeeeeee2");
		
		//System.out.println("searchVO.getPage():" + search.getPage());
		//System.out.println("searchVO.getPageUnit():" + search.getPageUnit());
		List<Purchase> list = new ArrayList<Purchase>();
		User user = null;
		
		UserService uservice = new UserServiceImpl();
		user = uservice.getUser(userId);
		System.out.println("user는 들어갔나?"+user);
		while(rs.next()) {
			Purchase purchase = new Purchase();
			purchase.setBuyer(user);
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			
			list.add(purchase);
		}
		
		map.put("totalCount", totalcount);
		//System.out.println("DB에서 꺼내면 나오냐?"+map.get("totalCount"));
		map.put("list", list);
		System.out.println("listsize"+list.size());
		
		
		
		rs.close();
		pStmt.close();
		con.close();
		
		return map;
	}
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		//System.out.println("메서드에서 꺼내기"+totalCount);
		return totalCount;
	}
	
}


