package by.betrayal.requestservice.utils.pageable;

import org.springframework.data.domain.Sort;

public abstract class SortFactory {

    public static Sort getSortAsc(String name) {
        return Sort.by(name).ascending();
    }

    public static Sort getSortDesc(String name) {
        return Sort.by(name).descending();
    }

    public static Sort getSortIdAsc() {
        return getSortAsc("id");
    }

    public static Sort getSortIdDesc() {
        return getSortDesc("id");
    }
}
