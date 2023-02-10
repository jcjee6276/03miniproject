package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.service.purchase.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.framework.Action;

public class GetPurchaseAction extends Action{
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		User user = new User();
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		//System.out.println(tranNo);
		
		
		PurchaseService purservice = new PurchaseServiceImpl();
		Purchase purchase = purservice.getPurchase(tranNo);
		HttpSession session = request.getSession();
		user = (User)session.getAttribute("user");
		purchase.setBuyer(user);
		System.out.println(purchase.getBuyer().getUserId());
		request.setAttribute("pur", purchase);
		
		
		return "forward:/purchase/getPurchase.jsp";
	}
}
