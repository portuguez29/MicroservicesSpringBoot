package academy.digitallab.store.product.controller;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    //injección de dependencia con @Autowired
    @Autowired
    private ProductService productService;

    @GetMapping("/getListProducts")
    public ResponseEntity<List<Product>> getListProduct(){
       List<Product> products = productService.listAllProduct();
        if(products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
      //  return new ResponseEntity(productService.listAllProduct(), HttpStatus.OK);
    }

    @GetMapping("/getProductsbyCategory/{idCategory}")
    public ResponseEntity<Product> getProductsbyCategory(@PathVariable Long idCategory){
        return new ResponseEntity(productService.findByCategory(Category.builder().id(idCategory).build()),HttpStatus.OK);
    }


    @GetMapping("/getProduct/{id}")
    public ResponseEntity getProduct(@PathVariable Long id){
        Product product = productService.getProduct(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity(productService.getProduct(id),HttpStatus.OK);
    }

    @PostMapping("/createProduct")
    public ResponseEntity createProduct(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        return new ResponseEntity(productService.createProduct(product),HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        return new ResponseEntity(productService.deleteProduct(id),HttpStatus.OK);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity updateProduct(@RequestBody Product product){
        return new ResponseEntity(productService.updateProduct(product),HttpStatus.OK);
    }

    @PostMapping("/updateStock/{id}")
    public ResponseEntity updateStock(@PathVariable Long id,@RequestBody Product product){
        return new ResponseEntity(productService.updateStock(id,product.getStock()),HttpStatus.OK);
    }

    private String formatMessage(BindingResult result){
        //Creamos un método para enviar errores en String y ponerlo en una lista
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        //Instanciamos a la Clase ErrorMessage
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .message(errors)
                .build();
        //Lo convertimos a formato Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try{
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }

}
