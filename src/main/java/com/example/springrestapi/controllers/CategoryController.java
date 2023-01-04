package com.example.springrestapi.controllers;


import com.example.springrestapi.dto.CategoryDTO;
import com.example.springrestapi.dto.CategoryUpdateDTO;
import com.example.springrestapi.entity.Category;
import com.example.springrestapi.response.ErrorMapper;
import com.example.springrestapi.response.ResponseHandler;
import com.example.springrestapi.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/all")
    public ResponseEntity<Object> findAll(){
        List<Category> all = categoryService.findAll();
        if(all.size() == 0){
            return ResponseHandler.response("Data category kosong" , HttpStatus.NO_CONTENT , all);
        }else{
            return ResponseHandler.response("Data Category Ditemukan" , HttpStatus.OK , all);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid CategoryDTO categoryDTO , BindingResult result){
        if(result.hasErrors()){
            List<String> messageErr = new ArrayList<>();
            for (ObjectError error: result.getAllErrors()
                 ) {
                messageErr.add(error.getDefaultMessage());
            }
            ErrorMapper errorMapper = new ErrorMapper(messageErr);
            return ResponseHandler.validationResponse(errorMapper , HttpStatus.BAD_REQUEST);
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category saved = categoryService.save(category);
        return ResponseHandler.response("Saved" , HttpStatus.CREATED , saved);
    }
    @PostMapping("/update")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO , BindingResult result){
        if(result.hasErrors()){
            List<String> messageErr = new ArrayList<>();
            for (ObjectError error: result.getAllErrors()
                 ) {
                messageErr.add(error.getDefaultMessage());
            }
            ErrorMapper errorMapper = new ErrorMapper(messageErr);
            return ResponseHandler.validationResponse(errorMapper , HttpStatus.BAD_REQUEST);
        }
        Category category = modelMapper.map(categoryUpdateDTO, Category.class);
        Category updated = categoryService.update(category);
        return ResponseHandler.response("Category berhasil diperbarui" , HttpStatus.OK , updated);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") Long id){
        Optional<Category> optionalCategory = categoryService.findById(id);
        return optionalCategory.map(category -> ResponseHandler.response("Data category ditemukan" , HttpStatus.OK , category)).
                orElse(ResponseHandler.response("Data category tidak ditemukan" , HttpStatus.NOT_FOUND , null));
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> remove(@PathVariable("id") Long id){
        Optional<Category> optionalCategory = categoryService.findById(id);
        if(optionalCategory.isPresent()){
            categoryService.remove(id);
            return ResponseHandler.response("Berhasil delete" , HttpStatus.OK , null);
        }else{
            return ResponseHandler.response("Gagal delete , data category tidak ditemukan" , HttpStatus.NOT_FOUND , null);
        }
    }
}
