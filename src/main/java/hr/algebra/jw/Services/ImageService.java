package hr.algebra.jw.Services;

import org.springframework.web.multipart.MultipartFile;



public interface ImageService {
    public Long uploadImage(MultipartFile imageFile) ;
    public byte[] downloadImage(long id) ;


}