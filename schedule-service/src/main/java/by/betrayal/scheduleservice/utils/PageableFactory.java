package by.betrayal.scheduleservice.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class PageableFactory {

    public static Pageable createPageable(PageableOptions options) {
        var sort = SortFactory.getSortIdAsc();
        return PageRequest.of(options.getPage(), options.getLimit(), sort);
    }
}
