package com.example.springrestapi.controllers;


import com.example.springrestapi.entity.Product;
import com.example.springrestapi.exeception.RecordNotFoundException;
import com.example.springrestapi.response.ErrorMapper;
import com.example.springrestapi.response.ResponseHandler;
import com.example.springrestapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
            return ResponseHandler.response(
                    "Data tidak ditemukan" , HttpStatus.NO_CONTENT , null
            );
        }else{
            return ResponseHandler.response("Data ditemukan" , HttpStatus.OK , data);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Object> save(@Valid  @RequestBody Product product , BindingResult errors){
        try {
            if(errors.hasErrors()){
                List<String> er = new ArrayList<>();
                for (ObjectError error: errors.getAllErrors()
                     ) {
                    System.out.println(error.getDefaultMessage());
                    er.add(error.getDefaultMessage());
                }
                ErrorMapper errorMapper = new ErrorMapper(er);
                return ResponseHandler.validationResponse(errorMapper , HttpStatus.BAD_REQUEST);
            }
            Product product1 = service.save(product);
            return ResponseHandler.response("Saved" , HttpStatus.CREATED , product1);
        }catch (Exception e){
            return ResponseHandler.response(e.getMessage() , HttpStatus.BAD_REQUEST , null);
        }
    }
    @PostMapping("/edit")
    public ResponseEntity<Object> edit(@Valid  @RequestBody Product product , BindingResult errors){
        try {
            List<String> messages = new ArrayList<>();
           if(errors.hasErrors()){
               for (ObjectError error: errors.getAllErrors()
                    ) {
                   messages.add(error.getDefaultMessage());
               }
               ErrorMapper errorMapper = new ErrorMapper(messages);
               return ResponseHandler.validationResponse(errorMapper , HttpStatus.BAD_REQUEST);
           }
            Optional<Product> update = service.update(product);
            return update.map(product1 -> ResponseHandler.response("Updated" , HttpStatus.OK , product1))
                    .orElse(ResponseHandler.response("Gagal Update data tidak ditemukan" ,
                            HttpStatus.BAD_REQUEST , null));
        }catch (Exception e){
            return ResponseHandler.response(e.getMessage() , HttpStatus.BAD_REQUEST , null);
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
            return ResponseHandler.response("Data ditemukan" , HttpStatus.OK , product);
        }else{
           throw  new RecordNotFoundException("Data Product dengan id : "+id+" tidak ditemukan" );
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteAll(){
        Boolean aBoolean = service.deleteAll();
        if(aBoolean){
            return ResponseHandler.response(
                    "Deleted" , HttpStatus.OK , null
            );
        }else{
            return ResponseHandler.response(
                    "Data Already Empty" , HttpStatus.BAD_REQUEST , null
            );
        }
    }
}
