package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;
    private int newWidth;
    private int newHeight;

    public Converter() {
        schema = new ColorSchema();
    }

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        // take image from URL
        BufferedImage img = ImageIO.read(new URL(url));
        maximumRatio(img);
        resizeImage(img);
        char[][] graph = new char[newHeight][newWidth];
        // scaling image
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        // convert to black-white image
        BufferedImage blackWhiteImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        // instrument for drawing on my bw image
        Graphics2D graphics = blackWhiteImg.createGraphics();
        // instrument copy from my image
        graphics.drawImage(scaledImage, 0, 0, null);
        var bwRaster = blackWhiteImg.getRaster();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                graph[h][w] = c;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        printText(graph, stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema colorSchema) {
        this.schema = colorSchema;
    }

    private void maximumRatio(BufferedImage img) throws BadImageSizeException {
        double ratio;
        if (img.getWidth() / img.getHeight() > img.getHeight() / img.getWidth()) {
            ratio = (double) img.getWidth() / (double) img.getHeight();
        } else {
            ratio = (double) img.getHeight() / (double) img.getWidth();
        } if (ratio > maxRatio && maxRatio != 0) throw new BadImageSizeException(ratio, maxRatio);
    }

    private void printText(char[][] graph, StringBuilder stringBuilder) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                stringBuilder.append(graph[i][j]);
                stringBuilder.append(graph[i][j]);
            }
            stringBuilder.append("\n");
        }
    }

    private void resizeImage(BufferedImage image) {
        double cWidth = 0;
        double cHeight = 0;
        if (image.getWidth() > width || image.getHeight() > height) {
            if (width != 0) {
                cWidth = image.getWidth() / width;
            } else cWidth = 1;
            if (height != 0) {
                cHeight = image.getHeight() / height;
            } else cHeight = 1;
            double maxCoeff = Math.max(cWidth, cHeight);
            newWidth = (int) (image.getWidth() / maxCoeff);
            newHeight = (int) (image.getHeight() / maxCoeff);
        } else {
            newWidth = image.getWidth();
            newHeight = image.getHeight();
        }
    }
}