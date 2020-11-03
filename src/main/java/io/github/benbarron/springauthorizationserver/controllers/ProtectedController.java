package io.github.benbarron.springauthorizationserver.controllers;

import io.github.benbarron.springauthorizationserver.models.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProtectedController {

    @GetMapping("/")
    public ResponseEntity<JsonResponse> index() {
        JsonResponse response = new JsonResponse(HttpStatus.OK, "Protected Resource");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
