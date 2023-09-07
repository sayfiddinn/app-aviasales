package uz.pdp.appaviauz.util;

import uz.pdp.appaviauz.exception.BadRequestException;
import uz.pdp.appaviauz.exception.ContentTypeException;

public class Validation {

    public static void checkPage(Integer page, Integer size, Long count) {
        if (page == null || page < 0) {
            throw new BadRequestException("Invalid page number");
        }
        if (size == null || size < 1) {
            throw new BadRequestException("Invalid page size");
        }
        int totalPages = (int) Math.ceil((double) count / size);
        if (totalPages == 0) {
            throw new BadRequestException("Objects not found");
        }
        if (page >= totalPages) {
            throw new BadRequestException("Invalid page number, max number of pages using this " +
                    "size(" + size + ") is equals to " + totalPages);
        }
    }

    public static void checkContentType(String contentType) {
        if (!Utils.ImageFormat.contains(contentType))
            throw new ContentTypeException("can't upload a file of this format");
    }
}
