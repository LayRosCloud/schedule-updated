package by.betrayal.groupservice.utils.pageable;

import org.springframework.data.domain.Sort;

public abstract class SortFactory {

    public static Sort getSortAsc(String name) {
        return Sort.by(name).ascending();
    }

    public static Sort getSortIdAsc() {
        return getSortAsc("id");
    }

    public static Sort getSortIdDesc() {
        return getSortDesc("id");
    }

    public static Sort getSortDesc(String name) {
        return Sort.by(name).descending();
    }
}
