import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.List;

/*
* 우선 지금 정해진거 얘기해주면,
1. KOICA 사업의 2010-2018년간 라오스/캄보디아 현지 언론보도 건수(미디어노출 건수) 집계
2. 타 국가 기관(JICA, GIZ 등 추후선정) 동일하게 집계
3. 검색어 keywords는 교육, 보건의료,기술환경에너지,농림수산,공공행정 등의 분야에 맞는 keywords선정하여 제공 예정
4. 현지 언론 미디어 수는  3-4개로 제한할 예정
* */


public class SeleniumExample {

    //WebDriver
    private WebDriver driver;
    private WebElement element;
    private String url;

    //Properties
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "D:/chromedriver.exe";

    public static void main(String[] args) {

        SeleniumExample test = new SeleniumExample();
        test.find();
    }

    public SeleniumExample() {
        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        //Driver SetUp
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        driver = new ChromeDriver(options);

        url = "https://www.khmertimeskh.com/?s=";
    }

    public void find() {
        try {
            //get방식으로 url 요청
            String keyword = "KOICA";
            driver.get(url+keyword);
            Thread.sleep(1000);
            crawl();
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    public void crawl() {
        int index = 0;
        List<WebElement> titles = driver.findElements(By.className("item-title"));
        List<WebElement> contents = driver.findElements(By.className("item-excerpt"));

        File file = new File("test.txt");
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;

        try {
            writer = new FileWriter(file, false);
            bufferedWriter = new BufferedWriter(writer);
            for (int i = 0; i < titles.size(); i++) {
                index++;
                System.out.println("기사 제목 : " + titles.get(i).getText());
                System.out.println("기사 본문 : " + contents.get(i).getText());
                bufferedWriter.write("기사 제목 : " + titles.get(i).getText() + "\n");
                bufferedWriter.write("기사 본문 : " + contents.get(i).getText() + "\n");
            }
            System.out.println("총 기사 건수 : " + index + " 건");
            bufferedWriter.write("총 기사 건수 : " + index + " 건");
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
