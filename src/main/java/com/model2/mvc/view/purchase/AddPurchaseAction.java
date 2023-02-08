package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.service.purchase.*;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.framework.Action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddPurchaseAction extends Action{
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		User user = new User();
		Purchase pchase = new Purchase();
		//Product product = new Product();
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		Product product = (Product)request.getAttribute("pvo");
		ProductService proservice = new ProductServiceImpl();
		proservice.getProduct(prodNo);
		pchase.setPurchaseProd(proservice.getProduct(prodNo));
		System.out.println("+++++"+pchase.getPurchaseProd().getProdNo());
		HttpSession session = request.getSession();
		user = (User)session.getAttribute("user");
		
		
		//System.out.println("======ttt"+pchaseVO.getPurchaseProd().getProdNo());
		pchase.setBuyer(user);
		pchase.setPaymentOption(request.getParameter("paymentOption"));
		pchase.setReceiverName(user.getUserName());
		pchase.setReceiverPhone(user.getPhone());
		pchase.setDivyAddr(user.getAddr());
		pchase.setDivyRequest(request.getParameter("receiverRequest"));
		pchase.setDivyDate(request.getParameter("receiverDate"));
		

		PurchaseService pservice = new PurchaseServiceImpl();
		pservice.addPurchase(pchase);
		
		request.setAttribute("purcVO", pchase);
		
		
		return "forward:/purchase/addPurchase.jsp";
	}
}
