package com.example.springrestapi.controllers;


import com.example.springrestapi.entity.Product;
import com.example.springrestapi.exeception.RecordNotFoundException;
import com.example.springrestapi.repository.ProductRepository;
import com.example.springrestapi.response.ResponseHandler;
import com.example.springrestapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService service;


    @GetMapping("/all")
    public ResponseEntity<Object> findAll(){
        Iterable<Product> all = service.findAll();
        List<Product> data = new ArrayList<>();
        all.forEach(data::add);
        if(data.size() == 0){
            return ResponseHandler.generateResponse(
                    "Data tidak ditemukan" , HttpStatus.NO_CONTENT , null
            );
        }else{
            return ResponseHandler.generateResponse("Data ditemukan" , HttpStatus.OK , data);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Object> save(@RequestBody Product product){
        try {
            Product product1 = service.save(product);
            return ResponseHandler.generateResponse("Saved" , HttpStatus.CREATED , product1);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage() , HttpStatus.BAD_REQUEST , null);
        }
    }
    @PostMapping("/edit")
    public ResponseEntity<Object> edit(@RequestBody Product product){
        try {
            Product update = service.update(product);
            if(update==null){
                return ResponseHandler.generateResponse(
                        "Data not found" , HttpStatus.NOT_FOUND , null
                );
            }else{
                return ResponseHandler.generateResponse(
                        "Updated" , HttpStatus.OK , update
                );
            }
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage() , HttpStatus.BAD_REQUEST , null);
        }
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<Map<String , String>> deleteById(@PathVariable(value = "id") Long id){
        Boolean aBoolean = service.deleteById(id);
        HashMap<String , String> response = new HashMap<>();
        if(aBoolean){
            response.put("message" , "berhasil delete");
            response.put("status" , HttpStatus.OK.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("message" , "gagal delete");
            response.put("status" , HttpStatus.NOT_FOUND.toString());
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable(value = "id") Long id){
        boolean present = service.findById(id).isPresent();
        if(present){
            Product product = service.findById(id).get();
            return ResponseHandler.generateResponse("Data ditemukan" , HttpStatus.OK , product);
        }else{
           throw  new RecordNotFoundException("Data Product dengan id : "+id+" tidak ditemukan" );
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteAll(){
        Boolean aBoolean = service.deleteAll();
        if(aBoolean){
            return ResponseHandler.generateResponse(
                    "Deleted" , HttpStatus.OK , null
            );
        }else{
            return ResponseHandler.generateResponse(
                    "Data Already Empty" , HttpStatus.BAD_REQUEST , null
            );
        }
    }
}
