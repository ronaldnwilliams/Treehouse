package com.acme.ecommerce.controller;

import com.acme.ecommerce.FlashMessage;
import com.acme.ecommerce.domain.Product;
import com.acme.ecommerce.domain.ProductPurchase;
import com.acme.ecommerce.domain.Purchase;
import com.acme.ecommerce.domain.ShoppingCart;
import com.acme.ecommerce.service.NotEnoughStockException;
import com.acme.ecommerce.service.ProductService;
import com.acme.ecommerce.service.PurchaseService;
import com.sun.net.httpserver.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequestMapping("/cart")
@Scope("request")
public class CartController {
	final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	PurchaseService purchaseService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCart sCart;
	
	@Autowired
	private HttpSession session;
	
    @RequestMapping("")
    public String viewCart(Model model, RedirectAttributes redirectAttributes) {
    	logger.debug("Getting Product List");
    	logger.debug("Session ID = " + session.getId());
    	
    	Purchase purchase = sCart.getPurchase();
    	BigDecimal subTotal = new BigDecimal(0);
    	
    	model.addAttribute("purchase", purchase);
    	if (purchase != null) {
    		for (ProductPurchase pp : purchase.getProductPurchases()) {
    			logger.debug("cart has " + pp.getQuantity() + " of " + pp.getProduct().getName());
    			subTotal = subTotal.add(pp.getProduct().getPrice().multiply(new BigDecimal(pp.getQuantity())));
    		}
    		
    		model.addAttribute("subTotal", subTotal);
    	} else {
    		logger.error("No purchases Found for session ID=" + session.getId());
			redirectAttributes.addFlashAttribute("flash", new FlashMessage("Your cart is currently empty.", FlashMessage.Status.FAILURE));
			return "redirect:/error";
    	}
        return "cart";
    }
    
    @RequestMapping(path="/add", method = RequestMethod.POST)
    public RedirectView addToCart(@ModelAttribute(value="productId") long productId, @ModelAttribute(value="quantity") int quantity, RedirectAttributes redirectAttributes) {
    	boolean productAlreadyInCart = false;
    	boolean notAvailable = false;
    	boolean productDetailNotAvailable = false;
    	RedirectView redirect = new RedirectView("/product/");
		redirect.setExposeModelAttributes(false);

		//identify product
    	Product addProduct = productService.findById(productId);
		if (addProduct != null) {
	    	logger.debug("Adding Product: " + addProduct.getId());
	    	//check to see if any purchases yet
    		Purchase purchase = sCart.getPurchase();
    		if (purchase == null) {
    			purchase = new Purchase();
    			sCart.setPurchase(purchase);
    		} else {
    			for (ProductPurchase pp : purchase.getProductPurchases()) {
    				if (pp.getProduct() != null) {
    					if (pp.getProduct().getId().equals(productId)) {
							productAlreadyInCart = true;
							/*
							<<<< Commented out to handle in Service layer >>>>
							if (pp.getQuantity() + quantity > addProduct.getQuantity()) {
								notAvailable = true;
								break;
							}
							*/
							pp.setQuantity(quantity + pp.getQuantity());
    					}
    				}
    			}
    		}
    		if (!productAlreadyInCart) {
				ProductPurchase newProductPurchase = new ProductPurchase();
				newProductPurchase.setProduct(addProduct);
				/*
				<<<< Commented out to handle in Service layer >>>>
					if (quantity > addProduct.getQuantity()) {
					redirectAttributes.addFlashAttribute("flash", new FlashMessage(String.format("Only %s added to cart. No more available stock.", addProduct.getQuantity()), FlashMessage.Status.FAILURE));
					newProductPurchase.setQuantity(addProduct.getQuantity());
					newProductPurchase.setPurchase(purchase);
					purchase.getProductPurchases().add(newProductPurchase);
					productDetailNotAvailable = true;
					redirectAttributes.addFlashAttribute("addedTooMany", true);
				} else {
				*/
					newProductPurchase.setQuantity(quantity);
					newProductPurchase.setPurchase(purchase);
					purchase.getProductPurchases().add(newProductPurchase);
			}
			if (notAvailable == true) {
				logger.error("Out of stock: " + productId);
				redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Out of stock.", FlashMessage.Status.FAILURE));
				redirectAttributes.addFlashAttribute("addedTooMany", true);
				return redirect;
			}
    		logger.debug("Added " + quantity + " of " + addProduct.getName() + " to cart");
    		sCart.setPurchase(purchaseService.save(purchase));
    		if (!productDetailNotAvailable) redirectAttributes.addFlashAttribute("flash", new FlashMessage("Added to cart!", FlashMessage.Status.SUCCESS));
		} else {
			logger.error("Attempt to add unknown product: " + productId);
			redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Not sure what product you were trying to add", FlashMessage.Status.FAILURE));
			redirect.setUrl("/error");
		}

    	return redirect;
    }
 
    @RequestMapping(path="/update", method = RequestMethod.POST)
    public RedirectView updateCart(@ModelAttribute(value="productId") long productId, @ModelAttribute(value="newQuantity") int newQuantity, RedirectAttributes redirectAttributes) {
    	logger.debug("Updating Product: " + productId + " with Quantity: " + newQuantity);
		RedirectView redirect = new RedirectView("/cart");
		redirect.setExposeModelAttributes(false);
    	
    	Product updateProduct = productService.findById(productId);
    	if (updateProduct != null) {
    		Purchase purchase = sCart.getPurchase();
    		if (purchase == null) {
    			logger.error("Unable to find shopping cart for update");
				redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Not sure what happened when updating your cart. Please try again.", FlashMessage.Status.FAILURE));
				redirect.setUrl("/error");
    		} else {
    			for (ProductPurchase pp : purchase.getProductPurchases()) {
    				if (pp.getProduct() != null) {
    					if (pp.getProduct().getId().equals(productId)) {
    						if (newQuantity > 0) {
								pp.setQuantity(newQuantity);
								logger.debug("Updated " + updateProduct.getName() + " to " + newQuantity);
								redirectAttributes.addFlashAttribute("flash", new FlashMessage("Cart updated!", FlashMessage.Status.SUCCESS));
							/*
							<<<< Commented out to handle in Service layer >>>>
							} else if (newQuantity > 0 && newQuantity > updateProduct.getQuantity()) {
								redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Out of stock.", FlashMessage.Status.FAILURE));
								redirectAttributes.addFlashAttribute("addedTooMany", true);
								pp.setQuantity(updateProduct.getQuantity());
								logger.debug("Max Quantity. Updated " + updateProduct.getName() + " to " + updateProduct.getQuantity());
							*/
    						} else {
    							purchase.getProductPurchases().remove(pp);
    							logger.debug("Removed " + updateProduct.getName() + " because quantity was set to " + newQuantity);
    						}
    						break;
    					}
    				}
    			}
    		}
    		sCart.setPurchase(purchaseService.save(purchase));
    	} else {
    		logger.error("Attempt to update on non-existent product");
			redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Not sure what happened when updating your cart. Please try again.", FlashMessage.Status.FAILURE));
			redirect.setUrl("/error");
    	}
    	
    	return redirect;
    }
    
    @RequestMapping(path="/remove", method = RequestMethod.POST)
    public RedirectView removeFromCart(@ModelAttribute(value="productId") long productId, RedirectAttributes redirectAttributes) {
    	logger.debug("Removing Product: " + productId);
		RedirectView redirect = new RedirectView("/cart");
		redirect.setExposeModelAttributes(false);
    	
    	Product updateProduct = productService.findById(productId);
    	if (updateProduct != null) {
    		Purchase purchase = sCart.getPurchase();
    		if (purchase != null) {
    			for (ProductPurchase pp : purchase.getProductPurchases()) {
    				if (pp.getProduct() != null) {
    					if (pp.getProduct().getId().equals(productId)) {
    						purchase.getProductPurchases().remove(pp);
   							logger.debug("Removed " + updateProduct.getName());
							redirectAttributes.addFlashAttribute("flash", new FlashMessage("Removed. Cart updated!", FlashMessage.Status.SUCCESS));
    						break;
    					}
    				}
    			}
    			purchase = purchaseService.save(purchase);
    			sCart.setPurchase(purchase);
    			if (purchase.getProductPurchases().isEmpty()) {
        	    	//if last item in cart redirect to product else return cart
        			redirect.setUrl("/product/");
        		}
    		} else {
    			logger.error("Unable to find shopping cart for update");
				redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Not sure what happened to your cart. Please try again.", FlashMessage.Status.FAILURE));
				redirect.setUrl("/error");
    		}
    	} else {
    		logger.error("Attempt to update on non-existent product");
			redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Not sure what happened when updating your cart. Please try again.", FlashMessage.Status.FAILURE));
			redirect.setUrl("/error");
    	}

    	return redirect;
    }
    
    @RequestMapping(path="/empty", method = RequestMethod.POST)
    public RedirectView emptyCart(RedirectAttributes redirectAttributes) {
    	RedirectView redirect = new RedirectView("/product/");
		redirect.setExposeModelAttributes(false);
    	
    	logger.debug("Emptying Cart");
    	Purchase purchase = sCart.getPurchase();
		if (purchase != null) {
			purchase.getProductPurchases().clear();
			sCart.setPurchase(purchaseService.save(purchase));
			redirectAttributes.addFlashAttribute("flash", new FlashMessage("Cart Emptied!", FlashMessage.Status.SUCCESS));
		} else {
			logger.error("Unable to find shopping cart for update");
			redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Not sure what happened to your cart. Please try again.", FlashMessage.Status.FAILURE));
			redirect.setUrl("/error");
		}
		sCart.setPurchase(null);
    	return redirect;
    }

    @ExceptionHandler(NotEnoughStockException.class)
	public String notEnoughStock(HttpServletRequest request, Exception e) {
		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("flash", new FlashMessage(e.getMessage(), FlashMessage.Status.FAILURE));
		return "redirect:" + request.getHeader("referer");
	}
}
