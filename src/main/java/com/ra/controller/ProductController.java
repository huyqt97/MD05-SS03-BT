package com.ra.controller;

import com.ra.entity.Category;
import com.ra.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public String index(Model model) {
        List<Category> listCategory = categoryService.findAll();
        List<Product> listProduct = productService.findAll();
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listProduct", listProduct);
        return "product/index";
    }

    @GetMapping("/add-product")
    public String add(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> listCategory = categoryService.findAll();
        model.addAttribute("listCategory", listCategory);
        return "product/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.saveOrUpdate(product);
        return "redirect:/product";
    }

    @GetMapping("/delete-product/{id}")
    public String delete(@PathVariable("id") Integer id) {
        productService.delete(id);
        return "redirect:/product";
    }
    @GetMapping("/edit-product/{id}")
    public String edit(@PathVariable("id")int id ,Model model){
        Category categories = categoryService.findById(id);
        model.addAttribute("categories",categories);
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "product/edit-product";
    }
    @PostMapping("/update-product")
    public String update(@ModelAttribute("product") Product product){
        if(productService.saveOrUpdate(product) != null){
            return "redirect:/product";
        }
        return "product/edit-product";
    }
}

