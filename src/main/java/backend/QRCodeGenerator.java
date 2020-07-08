package backend;

import net.glxn.qrgen.QRCode;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QRCodeGenerator {

    public static BufferedImage generateQRCodeImage(String text) throws IOException {
        ByteArrayOutputStream stream = QRCode
                .from(text)
                .withSize(300,300)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
        return ImageIO.read(bis);
    }
}
