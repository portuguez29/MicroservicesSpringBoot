package academy.digitallab.store.product;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.service.ProductService;
import academy.digitallab.store.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

//Test con Mockito de SpringBootTest

@SpringBootTest
/*@Sql("/data.sql")*/
public class ProductServiceMockTest {
    //Para trabajar con datos mockeados
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    //Para indicarle que esto se tiene que indicar antes de iniciar el test
    @BeforeEach
    @Deprecated
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product computer = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        //Cuando se busque un producto con el ID 1 retorna mock COMPUTER (up)
        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));

        //Mockear la actualización, cuando un producto se actualizó, debemos actualizar el mock
        Mockito.when(productRepository.save(computer)).thenReturn(computer);

    }

    //Metodo que valida la busqueda de nuestro producto computer
    @Test
    public void whenValidGetID_thenReturnProduct(){
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    //Prueba que cuando agregamos un  nuevo stock este debe actualizarse
    @Test
    public void whenValidUpdatedStock_theReturnNewStock(){
        Product newStock = productService.updateStock(1L,Double.parseDouble("10"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(15);
    }


}
