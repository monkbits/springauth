package com.example.dev.Controller;

import com.example.dev.dto.Resp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/addproduct")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(file.getInputStream(), uploadPath.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Could not upload the file: " + e.getMessage());
        }
    }
//
//    @PostMapping("/addproduct")
//    public Resp<MultipartFile> addproduct(@RequestParam("img")MultipartFile files){
//        return new Resp<>(files);
//    }

    @PostMapping("/getproduct")
    public Resp<String> getproduct(){
        return new Resp<>("works");
    }
}
