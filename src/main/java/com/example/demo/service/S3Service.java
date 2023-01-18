package com.example.demo.service;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;

    @Transactional
    public String uploadS3Pdf(MultipartFile multipartFile) throws IOException {

        if("application/pdf".equals(multipartFile.getContentType())) {
            String s3FileName = UUID.randomUUID() + "-" + "HJ_03507_RO1.pdf";

            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(multipartFile.getInputStream().available());

            amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

            String pdfPath = amazonS3.getUrl(bucket, s3FileName).toString();

            return pdfPath;
        }else{
            return "pdf 파일을 업로드해주세요";
        }
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipart.getBytes());
        fos.close();
        return convFile;
    }


    @Transactional
    public List<String> split(MultipartFile multipartFile) throws Exception{

        MultipartFile multipartFile_ = multipartFile;

        List<String> imageList = new ArrayList<>();

        if("application/pdf".equals(multipartFile_.getContentType())) {

            File file = multipartToFile(multipartFile);
            PDDocument document = PDDocument.load(file); // document 생성
            PDFRenderer renderer = new PDFRenderer(document); // PDF 렌더러 불러오기
            List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>(); // 변환될 이미지 객체를 담을 List 선언
            int width = 0, height = 0; // 병합될 이미지 파일의 너비와 높이 값을 담을 변수

            BufferedImage bim = renderer.renderImageWithDPI (0, 300); // 이미지로 변환
            bufferedImages.add(bim); // 그리고 이미지 리스트에 담음

            if (bim.getWidth() > width) width = bim.getWidth(); // 병합될 이미지의 최대 너비
            height += bim.getHeight(); // 병합될 이미지의 최대 높이

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // 병합될 이미지 객체 생성
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics(); // 그래픽 객체 생성

            graphics.setBackground(Color.WHITE); // 배경을 흰색으로 지정

            // 이미지 병합 과정
            int idx = 0;
            for (BufferedImage obj : bufferedImages) { // 이미지 List에서 이미지를 하나씩 꺼내와서
                if (idx == 0) height = 0; // 첫번째 이미지의 경우 높이를 0으로 설정
                graphics.drawImage(obj, 0, height, null); // 그래픽 객체에 꺼내온 이미지를 그려줌

                height += obj.getHeight(); // 높이값을 이미지의 높이만큼 더해줌
                idx++;
            }
            //BufferedImage new_buff = buff.getSubimage(minX, minY, (maxX-minX), (maxY-minY));

            BufferedImage num = bufferedImage.getSubimage(327, 316, 206, 55);
            String fileName1 = UUID.randomUUID() + "-1.jpg";
            ImageIO.write(num, "jpg", new File(fileName1));
            imageList.add(fileName1);

            BufferedImage left = bufferedImage.getSubimage(218, 631, 598, 2143);
            String fileName2 = UUID.randomUUID() + "-2.jpg";
            ImageIO.write(left, "jpg", new File(fileName2)); // 마지막으로 병합된 이미지 생성
            imageList.add(fileName2);

            BufferedImage right = bufferedImage.getSubimage(1285, 495, 593, 2281);
            String fileName3 = UUID.randomUUID() + "-3.jpg";
            ImageIO.write(right, "jpg", new File(fileName3)); // 마지막으로 병합된 이미지 생성
            imageList.add(fileName3);

            graphics.dispose(); // 그래픽 리소스 해제

            if (document != null) {
                document.close();
            }

        }
        return imageList;
    }

    public void merge() throws Exception{
        try {
            BufferedImage image1 = ImageIO.read(new File("./img.jpg"));
            BufferedImage image2 = ImageIO.read(new File("./img1.jpg"));

            int width = Math.max(image1.getWidth(), image2.getWidth());
            int height = image1.getHeight() + image2.getHeight();

            BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = (Graphics2D) mergedImage.getGraphics();

            graphics.setBackground(Color.WHITE);
            graphics.drawImage(image1, 0, 0, null);
            graphics.drawImage(image2, 0, image1.getHeight(), null);

            ImageIO.write(mergedImage, "gif", new File("./merge.jpg"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}