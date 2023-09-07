package uz.pdp.appaviauz.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Util {
    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(page, size, Sort.by("creationTimestamp").descending());
    }

    public static Pageable getPageableDesc(Integer page, Integer size, boolean ascending) {
        return ascending
                ? PageRequest.of(page, size, Sort.by("creationTimestamp").ascending())
                : PageRequest.of(page, size, Sort.by("creationTimestamp").descending());
    }

    public static Pageable getExtraPageable(Integer page, Integer size, String field, boolean ascending) {
        return ascending
                ? PageRequest.of(page, size, Sort.by(field).ascending())
                : PageRequest.of(page, size, Sort.by(field).descending());
    }
}
