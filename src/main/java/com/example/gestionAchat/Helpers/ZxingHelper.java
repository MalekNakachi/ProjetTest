package com.example.gestionAchat.Helpers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ZxingHelper {

    public static byte[] generateQRCode(String data, Integer width, Integer height, String[] text) throws IOException {

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();

            //        If text is needed to display
            if (text.length > 0) {
                int totalTextLineToadd = text.length;
                InputStream in = new ByteArrayInputStream(pngData);
                BufferedImage image = ImageIO.read(in);
                BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight() + 25 * totalTextLineToadd, BufferedImage.TYPE_INT_ARGB);
                Graphics g = outputImage.getGraphics();
                        g.setColor(Color.WHITE);
                        g.fillRect(0, 0, outputImage.getWidth(), outputImage.getHeight());
                        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
                        g.setFont(new Font("Arial Black", Font.BOLD, 20));
                Color textColor = Color.BLACK;
                        g.setColor(textColor);
                FontMetrics fm = g.getFontMetrics();
                int startingYposition = height + 5;
                        for(String displayText : text) {
                    g.drawString(displayText, (outputImage.getWidth() / 2)   - (fm.stringWidth(displayText) / 2), startingYposition);
                    startingYposition += 20;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(outputImage, "PNG", baos);
                        baos.flush();
                pngData = baos.toByteArray();
                        baos.close();
            }

            return pngData;
        } catch (WriterException | IOException ex) {
            ex.printStackTrace();
            return  null;
        }
    }

}
