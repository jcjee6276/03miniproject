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



public class AddPurchaseViewAction extends Action{
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {

		//System.out.println( s=request.getParameter("prodNo"));
		HttpSession session = request.getSession();
		User vo = (User)session.getAttribute("user");
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));

		
		Purchase purVO = new Purchase();
		Product proVO = new Product();
		
		ProductService service = new ProductServiceImpl();
		proVO = service.getProduct(prodNo);
		System.out.println(proVO);
		
		UserService service2=new UserServiceImpl();
		vo = service2.getUser(vo.getUserId());
		
		//PurchaseService service3 = new PurchaseServiceImpl();
		//PurchaseVO purvo = service3.getPurchase(prodNo);
		
		
		
		purVO.setBuyer(service2.getUser(vo.getUserId()));
		purVO.setReceiverName(vo.getUserName());
		purVO.setDivyAddr(vo.getAddr());
		purVO.setReceiverPhone(vo.getPhone());
		purVO.setTranNo(proVO.getProdNo());
		
		request.setAttribute("proVO", proVO);
		request.setAttribute("purvo", purVO);

		//PurchaseService service = new PurchaseServiceImpl();
		//PurchaseVO purchaseVO = service.getPurchase(prodNo); 
		
		//request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}
