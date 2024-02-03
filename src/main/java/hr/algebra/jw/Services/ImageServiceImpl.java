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

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
      public Long uploadImage(MultipartFile imageFile) {
        Image imageToSave = null;

        try {
            var time =new Date();
            imageToSave = Image.builder()
                    .name(time.getTime()+imageFile.getOriginalFilename())
                    .type(imageFile.getContentType())
                    .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image savedImage = imageRepository.save(imageToSave);

        return savedImage.getId();
    }

    @Override
    public byte[] downloadImage(long id) {
        Optional<Image> dbImage = imageRepository.findById(id);
        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new RuntimeException(exception);
            }
        }).orElse(null);
    }


}