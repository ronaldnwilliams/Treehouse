package com.acme.ecommerce.controller;

import com.acme.ecommerce.FlashMessage;
import com.acme.ecommerce.domain.Product;
import com.acme.ecommerce.domain.ProductPurchase;
import com.acme.ecommerce.domain.Purchase;
import com.acme.ecommerce.domain.ShoppingCart;
import com.acme.ecommerce.service.NotEnoughStockException;
import com.acme.ecommerce.service.ProductNotFoundException;
import com.acme.ecommerce.service.ProductService;
import com.acme.ecommerce.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

@Controller
@RequestMapping("/product")
@Scope("request")
public class ProductController {
	
	final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	private static final int INITIAL_PAGE = 0;
	private static final int PAGE_SIZE = 5;

	@Autowired
	PurchaseService purchaseService;
	
	@Autowired
	ProductService productService;

	@Autowired
	private ShoppingCart sCart;
	
	@Autowired
	HttpSession session;
	
	@Value("${imagePath:/images/}")
	String imagePath;
	
    @RequestMapping("/")
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page) {
    	logger.debug("Getting Product List");
    	logger.debug("Session ID = " + session.getId());
		// Evaluate page. If requested parameter is null or less than 0 (to
		// prevent exception), return initial size. Otherwise, return value of
		// param. decreased by 1.
		int evalPage = (page == null || page < 1) ? INITIAL_PAGE : page - 1;
    	
    	Page<Product> products = productService.findAll(new PageRequest(evalPage, PAGE_SIZE));
		Purchase purchase = sCart.getPurchase();
		BigDecimal subTotal = new BigDecimal(0);
		model.addAttribute("purchase", purchase);
		if (purchase != null) {
			for (ProductPurchase pp : purchase.getProductPurchases()) {
				logger.debug("cart has " + pp.getQuantity() + " of " + pp.getProduct().getName());
				subTotal = subTotal.add(pp.getProduct().getPrice().multiply(new BigDecimal(pp.getQuantity())));
			}
			model.addAttribute("subTotal", subTotal);
		}
		model.addAttribute("products", products);

        return "index";
    }
    
    @RequestMapping(path = "/detail/{id}", method = RequestMethod.GET)
    public String productDetail(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
    	logger.debug("Details for Product " + id);
    	
    	Product returnProduct = productService.findById(id);
    	if (returnProduct != null) {
    		model.addAttribute("product", returnProduct);
    		ProductPurchase productPurchase = new ProductPurchase();
    		productPurchase.setProduct(returnProduct);
    		productPurchase.setQuantity(1);
    		model.addAttribute("productPurchase", productPurchase);
    	} else {
    		logger.error("Product " + id + " Not Found!");
			redirectAttributes.addFlashAttribute("flash", new FlashMessage("Sorry! Could not find this product.", FlashMessage.Status.FAILURE));
			return("redirect:/error");
    	}

        return "product_detail";
    }
    
    @RequestMapping(path="/{id}/image", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> productImage(@PathVariable long id) throws FileNotFoundException {
    	MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
    	
    	logger.debug("Product Image Request for " + id);
    	logger.info("Using imagePath [" + imagePath + "]");
    	
    	Product returnProduct = productService.findById(id);
    	String imageFilePath = null;
    	if (returnProduct != null) {
    		if (!imagePath.endsWith("/")) {
    			imagePath = imagePath + "/";
    		}
    		imageFilePath = imagePath + returnProduct.getFullImageName();
    	} 
    	File imageFile = new File(imageFilePath);
    	
    	return ResponseEntity.ok()
                .contentLength(imageFile.length())
                .contentType(MediaType.parseMediaType(mimeTypesMap.getContentType(imageFile)))
                .body(new InputStreamResource(new FileInputStream(imageFile)));
    }
    
    @RequestMapping(path = "/about")
    public String aboutCartShop() {
    	logger.warn("Happy Easter! Someone actually clicked on About.");
    	return("about");
    }

	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String productNotFound(Model model, Exception e) {
		model.addAttribute("e",e);
		return "error";
	}
}
