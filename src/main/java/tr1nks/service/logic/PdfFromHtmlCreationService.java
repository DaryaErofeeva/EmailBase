package tr1nks.service.logic;

import com.itextpdf.tool.xml.css.CssFile;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface PdfFromHtmlCreationService {
    HtmlCssForPdfData loadHtmlCssData(String filename);

    byte[] create(HtmlCssForPdfData forPdfData, Map<Pattern, String> replaceMap);

    class HtmlCssForPdfData {
        private String html;
        private List<CssFile> css;

        public HtmlCssForPdfData(String html, List<CssFile> css) {
            this.html = html;
            this.css = css;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public List<CssFile> getCss() {
            return css;
        }

        public void setCss(List<CssFile> css) {
            this.css = css;
        }
    }
}
