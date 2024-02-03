package hr.algebra.jw.Controller;

import hr.algebra.jw.Model.Image;
import hr.algebra.jw.Repositories.ImageRepository;
import hr.algebra.jw.Services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Controller
@RequiredArgsConstructor
public class ImageController {
    @Autowired
    private final ImageService imageService;
    @PostMapping("/image")
    public String uploadImage(@RequestParam("image") MultipartFile file, Model model) throws IOException {
        Long fileName = imageService.uploadImage(file);
        model.addAttribute("fileName", fileName);
        return "image";
    }

    @GetMapping("/showImage/{id}")
    public ResponseEntity<byte[]> showImageById(@PathVariable Long id) {
        byte[] imageData = imageService.downloadImage(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }


}