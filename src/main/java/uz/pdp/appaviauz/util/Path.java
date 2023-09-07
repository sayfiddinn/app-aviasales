package uz.pdp.appaviauz.util;

public interface Path {

    String API_VERSION = "/api/v1";
    String API_VERSION_ALL = "/api/v1/**";
    String BASE_PATH_USER = API_VERSION + "/user";
    String BASE_PATH_ADMIN = API_VERSION + "/admin";
    String BASE_PATH_AERODROME = API_VERSION + "/aerodrome";
    String BASE_PATH_AUTH = API_VERSION + "/auth";
    String BASE_PATH_FLIGHT = API_VERSION + "/flight";
    String BASE_PATH_MODER = API_VERSION + "/moder";
    String BASE_PATH_PLANE = API_VERSION + "/plane";
    String BASE_PATH_TICKET = API_VERSION + "/ticket";
    String BASE_PATH_ROLE = API_VERSION + "/role";
    String BASE_PATH_FILE = API_VERSION + "/file";

    String NOT_FOUND = " Id not found";
    String SAVED = "Success saved";
    String UPDATED = "Success edited";
    String DELETED = "Success deleted";
    String NOT_ALLOWED = "Not allowed";
    String FILE_FAILED = "File failed to load";
    String FILE_DESCRIPTION = "Max file size is 5 MB and the file must be in image format";
    String DOMAIN = "https://app-avia-uz-9ee31c051280.herokuapp.com";
    String LINK_CHECK_MODER = DOMAIN + API_VERSION + "/auth/checkModerator?user_id=";
    String LINK_CHECK_EMAIL = DOMAIN + API_VERSION + "/auth/checkEmail?user_id=";


    String[] OPEN_PAGES = {
            BASE_PATH_AUTH + "/**",
            BASE_PATH_TICKET + "/search",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };


}
