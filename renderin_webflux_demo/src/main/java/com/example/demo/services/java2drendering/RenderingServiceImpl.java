package com.example.demo.services.java2drendering;

import com.example.demo.dao.interfaces.MyLineDAO;
import com.example.demo.model.RenderingRequest;
import com.example.demo.services.interfaces.RenderingService;
import org.locationtech.jts.geom.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class RenderingServiceImpl implements RenderingService {

    private static final Logger logger = LoggerFactory.getLogger(RenderingServiceImpl.class);

    private static final String IMAGE_FORMAT = "png";
    private static final String PATCH_IMAGE_ERROR_OCCURRED = "./src/main/resources/images/error_has_occurred.png";

    private final MyLineDAO myLineDAO;

    public RenderingServiceImpl(MyLineDAO myLineDAO){
        this.myLineDAO = myLineDAO;
    }

    @Override
    public Mono<byte[]> getPageByArea(RenderingRequest renderingRequest){
        //сделать запрос на область
        //отрисовать изображение
        //вернуть в виде картинки
        if(renderingRequest.getWidth() == null || renderingRequest.getHeight() == null){
            return Mono.empty();
        }
        final Double maxLat = renderingRequest.getBbox().getGeoLatMax();
        final Double maxLon = renderingRequest.getBbox().getGeoLonMax();
        final Double minLat = renderingRequest.getBbox().getGeoLatMin();
        final Double minLon = renderingRequest.getBbox().getGeoLonMin();
        final Integer height = renderingRequest.getHeight();
        final Integer width = renderingRequest.getWidth();
        final Double stepLat = (maxLat - minLat) / 100;
        final Double stepLon = (maxLon - minLon) / 100;
        final Double stepX = Double.valueOf(width) / 100;
        final Double stepY = Double.valueOf(height) / 100;

        return Mono.create(emitter -> {
            try{
                var buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                Graphics2D g2d = buffImg.createGraphics();
                g2d.setColor(Color.BLUE);

                myLineDAO.getMyLineByCoordinatePlane(maxLat, maxLon, minLat, minLon)
                        .subscribe(
                                value -> {
                                    var coordinates = value.getGeometry().getCoordinates();
                                    var color = getColor(value.getRgbParameter());

                                    g2d.setColor(color);

                                    int x1 = -1;
                                    int y1 = -1;
                                    int x2 = 0;
                                    int y2 = 0;

                                    for(Coordinate coordinate : coordinates){
                                        if(x1 == -1 && y1 == -1){
                                            x1 = convertToPixelWeightPoint(coordinate.getX(), minLon, stepLon, stepX);
                                            y1 = convertToPixelHeightPoint(coordinate.getY(), minLat, stepLat, stepY);
                                        }else{
                                            x2 = convertToPixelWeightPoint(coordinate.getX(), minLon, stepLon, stepX);
                                            y2 = convertToPixelHeightPoint(coordinate.getY(), minLat, stepLat, stepY);

                                            g2d.drawLine(x1, y1, x2, y2);

                                            x1 = x2;
                                            y1 = y2;
                                        }
                                    }
                                },
                                error -> {
                                    logger.error("Exception when getting sequence element: {}", error.getMessage());
                                    error.printStackTrace();
                                },
                                () -> {
                                    try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                                        ImageIO.write(buffImg, IMAGE_FORMAT, baos);
                                        byte[] bytes = baos.toByteArray();
                                        logger.info("Byte array create");
                                        emitter.success(bytes);

                                    }catch (Exception e){
                                        logger.error("Exception in the image to byte array conversion block: {}", e.getMessage());
                                        e.printStackTrace();
                                        emitter.success(getPictureErrorHasOccurred());
                                    }
                                }
                        );


            }catch (Exception e){
                logger.error("Exception by rendering: {}", e.getMessage());
                e.printStackTrace();
                emitter.error(e);
            }
        });
    }

    /**
     * Метод для маштабирования широты на координатную плоскость по Y
     * @param lat координаты по широте которые будут маштабироваться на область определенного разрешения
     * @param minLat минимальные координаты широты
     * @param stepLat максимальные координаты широты минус минимальные координваты широты деленые на 100. Так находим 1% от маштаба всей области по широте
     * @param stepY значение разрешения по Y деленое на 100. Так находим 1% области по Y на которую будем маштабировать выбранную область
     * @return возвращает маштабированные координаты по Y
     */
    private int convertToPixelHeightPoint(Double lat, Double minLat, Double stepLat, Double stepY){
        return (int) Math.round((lat - minLat) / stepLat * stepY);
    }

    /**
     * Метод для маштабирования долготы на координатную плоскость по X
     * @param lon координаты по долготе которые будут маштабироватся на облать определенного размера
     * @param minLon минимальные координаты долготы
     * @param stepLon максимальные координаты долготы минус минимальные координваты долготы деленые на 100. Так находим 1% от маштаба всей области по долготе
     * @param stepX значение разрешения по X деленое на 100. Так находим 1% области по X на которую будем маштабировать выбранную область
     * @return возвращает маштабированные координаты по X
     */
    private int convertToPixelWeightPoint(Double lon, Double minLon, Double stepLon, Double stepX){
        return (int) Math.round((lon - minLon) / stepLon * stepX);
    }

    /**
     * Метод форминует из строки формата r,g,b объект Color
     * @param color срока в формате r,g,b
     * @return возвращает Color преобразованый из строки
     */
    private Color getColor(String color){
        try {
            String[] rgbArray = color.split(",");
            int r = Integer.parseInt(rgbArray[0].trim());
            int g = Integer.parseInt(rgbArray[1].trim());
            int b = Integer.parseInt(rgbArray[2].trim());
            return new Color(r, g, b);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Color.BLUE;
    }

    /**
     * Резервное изображение на случай возникновения ошибки
     * @return байтовый массив из заображения с ошибкой
     */
    private byte[] getPictureErrorHasOccurred(){
        byte[] bytes = new byte[0];
        try {
            BufferedImage bImage = ImageIO.read(new File(PATCH_IMAGE_ERROR_OCCURRED));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            bytes = bos.toByteArray();
        }catch (Exception e){
            logger.error(" {}", e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }
}
