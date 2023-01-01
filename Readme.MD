# API SPEC

## CREATE PRODUCT

Request :

- Endpoint : `/api/v1/product/add`
- Method : POST
- Body : 

``` json
    { 
        "productName" : String , 
        "productDescription" : String , 
        "productPrice" : double , 
        "stock" : int
    }
```
Response : 

``` json
  {
    "data": {
        "id": 6,
        "productName": "keyboard Mekanikal",
        "productDescription": "Description keyboard Mekanikal",
        "productPrice": 20000.0,
        "stock": 10
    },
    "message": "Saved",
    "status": 201
  }
```


## FIND ALL PRODUCT

Request :

- Endpoint : `/api/v1/product/all`
- Method : GET
- Body :
Response :
``` json
{
    "data": [
        {
            "id": 6,
            "productName": "keyboard GAMEN ELITE",
            "productDescription": "Description keyboard baru",
            "productPrice": 20000.0,
            "stock": 10
        }
    ],
    "message": "Data ditemukan",
    "status": 200
}
```

## UPDATE PRODUCT

Request :

- Endpoint : `/api/v1/product/edit`
- Method : POST
- Body :

``` json
    { 
        "id" : int
        "productName" : String , 
        "productDescription" : String , 
        "productPrice" : double , 
        "stock" : int
    }
```
Response :

``` json
{
    "data": {
        "id": 6,
        "productName": "keyboard GAMEN ELITE",
        "productDescription": "Description keyboard baru",
        "productPrice": 20000.0,
        "stock": 10
    },
    "message": "Updated",
    "status": 200
}
```

## Find ONE by ID PRODUCT

Request :

- Endpoint : `/api/v1/product/id`
- Method : POST
- Body :

Response :

``` json
{
    "data": {
        "id": 6,
        "productName": "keyboard GAMEN ELITE",
        "productDescription": "Description keyboard baru",
        "productPrice": 20000.0,
        "stock": 10
    },
    "message": "Data ditemukan",
    "status": 200
}
```

## Delete by id PRODUCT

Request :

- Endpoint : `/api/v1/product/delete/id`
- Method : POST
- Body :
Response :

``` json
{
    "message": "berhasil delete",
    "status": "200 OK"
}
```



# CODE REFERENCE

Untuk membuat response entity gunakan :

``` java
public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        return new ResponseEntity<Object>(map,status);
    }
}

```

Custom Exception : 


``` java
   
public class ErrorResponse {

    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }
    private String message;
    private List<String> details;
}

// custom class exeption handler

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("Server error" , details);
        return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("DATA TIDAK DITEMUKAN", details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

}
// custom recordnotfound semua yang teradapat runtime akan masuk kek recordnotfoudn exeption
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException  extends  RuntimeException{

    public RecordNotFoundException(String message) {
        super(message);
    }
}

```

``` properties
# demo api adalah nama database
spring.datasource.url=jdbc:mysql://localhost/demo_api
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform = org.hibernate.dialect.MySQL5Dialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto = update
```


