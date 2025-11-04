import java.awt.Desktop;
import java.io.File;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
            // ✅ 메인 홈페이지 파일 (resources 아래)
            File html = new File("src/resources/index.html");

            if (!html.exists()) {
                System.err.println("❌ index.html을 찾을 수 없습니다:");
                System.err.println(html.getAbsolutePath());
                return;
            }

            URI uri = html.getAbsoluteFile().toURI();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri); // 기본 브라우저(대개 Chrome)로 열림
                System.out.println("✅ 메인 홈페이지 오픈: " + uri);
            } else {
                // 데스크톱 브라우저 미지원 시 OS별 폴백(거의 올 일 없음)
                String os = System.getProperty("os.name").toLowerCase();
                ProcessBuilder pb;
                if (os.contains("win")) {
                    pb = new ProcessBuilder("cmd", "/c", "start", "\"\"", uri.toString());
                } else if (os.contains("mac")) {
                    pb = new ProcessBuilder("open", uri.toString());
                } else {
                    pb = new ProcessBuilder("xdg-open", uri.toString());
                }
                pb.inheritIO().start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
