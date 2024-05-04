package by.betrayal.requestservice.utils.pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class PageableFactory {
    public static Pageable createPageableAsc(PageableOptions options) {
        var sort = SortFactory.getSortIdAsc();
        return createPageable(options, sort);
    }

    public static Pageable createPageableDesc(PageableOptions options) {
        var sort = SortFactory.getSortIdDesc();
        return createPageable(options, sort);
    }

    public static Pageable createPageable(PageableOptions options, Sort sort) {
        return PageRequest.of(options.getOneLessPage(), options.getLimit(), sort);
    }

}
