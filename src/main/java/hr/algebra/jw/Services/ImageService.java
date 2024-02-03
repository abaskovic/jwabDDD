package hr.algebra.jw.Services;

import Utils.ImageUtils;
import hr.algebra.jw.Model.Image;
import hr.algebra.jw.Repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.zip.DataFormatException;


public interface ImageService {
    public Long uploadImage(MultipartFile imageFile) ;
    public byte[] downloadImage(long id) ;


}