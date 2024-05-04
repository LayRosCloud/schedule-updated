package by.betrayal.scheduleservice.utils.pageable;

import org.springframework.data.domain.Sort;

public abstract class SortFactory {

    public static Sort getSortIdAsc() {
        return getSortAsc("id");
    }

    public static Sort getSortAsc(String name) {
        return Sort.by(name).ascending();
    }
}
