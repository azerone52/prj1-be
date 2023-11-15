package com.example.prj1be.controller;

import com.example.prj1be.domain.Comment;
import com.example.prj1be.domain.Member;
import com.example.prj1be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService service;
    @PostMapping("add")
    public ResponseEntity add(@RequestBody Comment comment,
                              @SessionAttribute(value = "login", required = false)Member login){
        if(login==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(service.validate(comment)){
            if(service.add(comment, login)){
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.internalServerError().build();
            }
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("list")
    public List<Comment> list(@RequestParam("id") Integer boardId){
        return service.list(boardId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity remove(@PathVariable Integer id,
                                         @SessionAttribute(value = "login", required = false)Member login){
        if(login==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(service.hasAccess(id, login)) {
            if(service.remove(id)){
                return ResponseEntity.ok().build();
            }else {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity fix(@PathVariable Integer id,
                    @RequestBody Map<String, Object> fixedComment){
        //RequestBody dto 없이 받기
//        System.out.println("fixedComment = " + fixedComment.get("fixedComment"));
        if(service.updateById(id, fixedComment.get("fixedComment").toString())){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
