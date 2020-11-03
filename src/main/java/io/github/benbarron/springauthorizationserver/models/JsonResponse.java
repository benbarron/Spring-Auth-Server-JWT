package io.github.benbarron.springauthorizationserver.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.Date;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse {
    private String message;
    private HttpStatus status;
    private Date timestamp;
    private User user;
    private String token;
    private HashMap<String, Object> data;

    public JsonResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
        this.user = null;
        this.token = null;
        this.data = new HashMap<>();
    }

    public void addDataField(String key, Object value) {
        this.data.put(key, value);
    }
}
