package academy.digitallab.store.product.controller;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorMessage {
    private String code;
    private List<Map<String,String>> message;
}
