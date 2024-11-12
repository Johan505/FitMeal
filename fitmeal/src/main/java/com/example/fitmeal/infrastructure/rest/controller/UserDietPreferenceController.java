package com.example.fitmeal.infrastructure.rest.controller;


import com.example.fitmeal.domain.service.UserDietPreferenceService;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-diet-preference")
@RequiredArgsConstructor
public class UserDietPreferenceController {

    private static final Logger log = LoggerFactory.getLogger(UserDietPreferenceController.class);
    private final UserDietPreferenceService userDietPreferenceService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> registerDietPreference(
            @PathVariable String userId,
            @RequestBody DietPreferenceRequest request) {
        if (request.getDietTypeId() == null) {
            return ResponseEntity.badRequest().body("DietType ID cannot be null");
        }

        log.debug("Received dietTypeId: {}", request.getDietTypeId());
        String response = userDietPreferenceService.registerDietPreference(userId, request.getDietTypeId());
        return ResponseEntity.ok(response);
    }
}

@Getter
@Setter
class DietPreferenceRequest {
    private Long dietTypeId;
}
