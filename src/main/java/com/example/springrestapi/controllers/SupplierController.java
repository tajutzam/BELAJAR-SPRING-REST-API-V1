package com.example.springrestapi.controllers;

import com.example.springrestapi.dto.SearchDTO;
import com.example.springrestapi.dto.SearchKeyDTO;
import com.example.springrestapi.dto.SupplierDTO;
import com.example.springrestapi.dto.SupplierUpdateDTO;
import com.example.springrestapi.entity.Product;
import com.example.springrestapi.entity.Supplier;
import com.example.springrestapi.response.ErrorMapper;
import com.example.springrestapi.response.ResponseHandler;
import com.example.springrestapi.service.SupplierServiceImpl;
import com.example.springrestapi.service.SupplierServiceInterface;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierServiceInterface supplierService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<Object> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO , BindingResult result){
        if(result.hasErrors()){
            List<String> messageErrr = new ArrayList<>();
            for (ObjectError error: result.getAllErrors()
                 ) {
                messageErrr.add(error.getDefaultMessage());
            }
            ErrorMapper errorMapper = new ErrorMapper(messageErrr);
            return ResponseHandler.validationResponse(errorMapper , HttpStatus.BAD_REQUEST);
        }
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);
        Supplier save = supplierService.save(supplier);
        return ResponseHandler.response("saved" , HttpStatus.CREATED , save);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAll(){
        List<Supplier> all = supplierService.findAll();
        if(all.size() == 0){
            return ResponseHandler.response("data tidak supplier ada" , HttpStatus.NO_CONTENT , all);
        }
        return ResponseHandler.response("data supplier ditemukan" , HttpStatus.OK , all);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@Valid @RequestBody SupplierUpdateDTO supplierUpdateDTO , BindingResult result){
        if(result.hasErrors()){
            List<String> messageError = new ArrayList<>();
            for (ObjectError error: result.getAllErrors()
                 ) {
                messageError.add(error.getDefaultMessage());
            }
            ErrorMapper errorMapper = new ErrorMapper(messageError);
            return ResponseHandler.validationResponse(errorMapper , HttpStatus.BAD_REQUEST);
        }
        Supplier supplier = modelMapper.map(supplierUpdateDTO, Supplier.class);
        return supplierService.update(supplier).
                map(value -> ResponseHandler.response("Updated" , HttpStatus.OK , value))
                        .orElse(ResponseHandler.response("Data supplier tidak ditemukan harap masukan data yang valid " , HttpStatus.BAD_REQUEST , null));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id){
         return supplierService.findOne(id)
                 .map(supplier -> ResponseHandler.response("Data ditemukan", HttpStatus.OK , supplier))
                 .orElse(ResponseHandler.response("Data tidak ditemukan" , HttpStatus.NOT_FOUND , null));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> remove(@PathVariable(value = "id") Long id){
        Boolean remove = supplierService.remove(id);
        if(remove){
            return ResponseHandler.response("Data supplier berhasil di hapus" , HttpStatus.OK , null);
        }else{
            return ResponseHandler.response("Data supplier gagal di hapus" , HttpStatus.NOT_FOUND , null);
        }
    }

    @GetMapping("/noProduct")
    public ResponseEntity<Object> findBySupplierIsNull(){
        return supplierService.findSupplierProductNull()
                .map(suppliers ->
                        ResponseHandler.response("Terdapat supplier yang tidak memiliki products", HttpStatus.OK, suppliers))
                .orElse(ResponseHandler.response("Semua supplier memiliki data product", HttpStatus.NOT_FOUND , null));
    }

    @PostMapping("/email")
    public ResponseEntity<Object> findByEmail(@RequestBody SearchDTO searchDTO){
        return supplierService.findByEmail(searchDTO.getEmail()).map(supplier -> ResponseHandler.response("Data ditemukan", HttpStatus.OK, supplier))
                .orElse(ResponseHandler.response("Data tidak ditemukan", HttpStatus.NOT_FOUND, null));

    }
    @PostMapping("/nameOrEmail")
    public ResponseEntity<Object> findByNameOremail(@RequestBody SearchKeyDTO searchKeyDTO){
        return supplierService.findByNameOrEmail(searchKeyDTO.getKey() , searchKeyDTO.getOtherKey())
                .map(supplier -> ResponseHandler.response("Data ditemukan" , HttpStatus.OK , supplier))
                .orElse(ResponseHandler.response("Data tidak ditemukan" , HttpStatus.NOT_FOUND , null));
    }
}
