package com.example.prj1be.controller;

import com.example.prj1be.domain.Like;
import com.example.prj1be.domain.Member;
import com.example.prj1be.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Like")
public class LikeController {
    private final LikeService service;

    @PostMapping
    public ResponseEntity like(@RequestBody Like like,
                               @SessionAttribute(value = "login", required = false) Member login) {
        if (login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            like.setMemberId(login.getId());
        }
        if (service.update(like)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }
}
