package tr1nks.component;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import tr1nks.service.logic.FileGenerationService;

import java.io.IOException;
import java.io.InputStream;

@Component
public class Base64ImageProviderForPDF extends AbstractImageProvider {
    private static final Logger logger = Logger.getLogger(Base64ImageProviderForPDF.class);

    @Override
    public Image retrieve(String src) {
        int pos = src.indexOf("base64,");
        try {
            if (src.startsWith("data") && pos > 0) {
                byte[] img = Base64.decode(src.substring(pos + 7));
                return Image.getInstance(img);
            } else {
                InputStream is = this.getClass().getResourceAsStream(FileGenerationService.PDF_RESOURCE_LOCATION + src);
                if (null != is) {
                    byte[] arr = new byte[is.available()];
                    is.read(arr);
                    return Image.getInstance(arr);
                }
            }
        } catch (BadElementException | IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getImageRootPath() {
        return null;
    }
}