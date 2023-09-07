package uz.pdp.appaviauz.util;


import uz.pdp.appaviauz.entity.enums.Authority;

import java.util.Arrays;
import java.util.List;

import static uz.pdp.appaviauz.entity.enums.Authority.*;

public interface Utils {

    String BEARER = "Bearer ";
    String BASIC = "Basic ";


    //Is default role authorities
    List<Authority> superAdminAuthority = Arrays.asList(Authority.values());
    List<Authority> adminAuthority = Arrays.asList(
            ADD_MODERATOR,
            DELETE_MODERATOR,
            SHOW_MODERATOR,
            ADD_PLANE,
            EDIT_PLANE,
            SHOW_PLANE,
            DELETE_PLANE,
            ADD_AERODROME,
            EDIT_AERODROME,
            SHOW_AERODROME,
            DELETE_AERODROME,
            SHOW_SOLD_TICKET_DETAIL,
            ADD_TICKET,
            ADD_HISTORY,
            SHOW_HISTORY,
            EDIT_HISTORY,
            ADD_FLIGHT,
            EDITE_FLIGHT,
            SHOW_FLIGHT,
            CANCEL_FLIGHT,
            SHOW_TICKET,
            BUY_TICKET,
            CANCEL_TICKET,
            SHOW_PROFILE,
            EDITE_PROFILE,
            DELETE_PROFILE,
            SHOW_FLIGHT_HISTORY,
            SHOW_TICKET_HISTORY,
            ADD_ROLE,
            CHANGE_ROLE,
            SHOW_ROLE,
            CRUD_ENTITY,
            SHOW_USER);
    List<Authority> moderAuthority = Arrays.asList(
            ADD_TICKET,
            ADD_HISTORY,
            SHOW_HISTORY,
            EDIT_HISTORY,
            ADD_FLIGHT,
            EDITE_FLIGHT,
            SHOW_FLIGHT,
            CANCEL_FLIGHT,
            SHOW_TICKET,
            BUY_TICKET,
            CANCEL_TICKET,
            SHOW_PROFILE,
            EDITE_PROFILE,
            DELETE_PROFILE,
            SHOW_FLIGHT_HISTORY,
            SHOW_TICKET_HISTORY);
    List<Authority> userAuthority = Arrays.asList(
            SHOW_TICKET,
            BUY_TICKET,
            CANCEL_TICKET,
            SHOW_PROFILE,
            EDITE_PROFILE,
            DELETE_PROFILE,
            SHOW_FLIGHT_HISTORY,
            SHOW_TICKET_HISTORY);
    List<String> ImageFormat = Arrays.asList(
            "image/avif",
            "image/avifs",
            "image/bmp",
            "image/gif",
            "image/heic",
            "image/jpeg",
            "image/jpg",
            "image/jxl",
            "image/pbm",
            "image/pgm",
            "image/png",
            "image/ppm",
            "image/webp",
            "image/xbm",
            "image/xpm");

}
