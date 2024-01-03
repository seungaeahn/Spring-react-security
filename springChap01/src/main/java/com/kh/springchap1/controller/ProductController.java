package com.kh.springchap1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.springchap1.model.Product;
import com.kh.springchap1.repository.ProductRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:3000", 
						allowCredentials="true",
						allowedHeaders="*")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	//  /api//     /api/add
	@GetMapping("/item")
	public ResponseEntity<List<Product>> getAllProduct(){
		//return productRepository.findAll();
		List<Product>products = productRepository.findAll();
		return ResponseEntity.ok(products);
	}
	//     /api/add
	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		Product saveProduct = productRepository.save(product);
		return ResponseEntity.ok(saveProduct);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productRepository.deleteById(id);
		return ResponseEntity.ok("성공적으로 삭제했습니다.");
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id,
					@RequestBody Product updatedProduct){
		Product existProduct = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("아이디를 찾을 수 없음" + id));
		existProduct.setName(updatedProduct.getName());
		existProduct.setPrice(updatedProduct.getPrice());
		
		Product updateProduct = productRepository.save(existProduct);
		return ResponseEntity.ok(updateProduct);
	}
}




