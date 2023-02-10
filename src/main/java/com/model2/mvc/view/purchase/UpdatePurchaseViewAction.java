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

public class UpdatePurchaseViewAction extends Action {
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		User user = new User();
		Purchase purchase = new Purchase();
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println(tranNo);
		
		HttpSession session = request.getSession();
		user = (User)session.getAttribute("user");
		
		PurchaseService purservice = new PurchaseServiceImpl();
		purchase = purservice.getPurchase(tranNo);
		purchase.setBuyer(user);
		
		request.setAttribute("purchase", purchase);
		
		
		return "forward:/purchase/updatePurchase.jsp";
	}
}
