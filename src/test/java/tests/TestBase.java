package tests;

import api.ApiClient;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

//    public static String browser = System.getProperty("browser", "chrome");
//    public static String remoteBaseUsername = System.getProperty("remoteBaseUsername");
//    public static String remoteBasePass = System.getProperty("remoteBasePass");
//    public static String remoteBaseUrl = System.getProperty("remoteBaseUrl");


    protected static final ApiClient api = new ApiClient();



    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://book-club.qa.guru";
        RestAssured.basePath = "/api/v1";

//        if (remoteBaseUrl != null) {
//            Configuration.remote = "https://" + remoteBaseUsername + ":" + remoteBasePass + "@" + remoteBaseUrl;
//        }

        Configuration.baseUrl = "https://book-club.qa.guru";
    }
}