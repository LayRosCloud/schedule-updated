package by.betrayal.groupservice.utils.pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class PageableFactory {

    public static Pageable getPageableAsc(PageableOptions options) {
        var sort = SortFactory.getSortIdAsc();
        return PageRequest.of(options.getOneLessPage(), options.getLimit(), sort);
    }

    public static Pageable getPageableDesc(PageableOptions options) {
        var sort = SortFactory.getSortIdDesc();
        return PageRequest.of(options.getOneLessPage(), options.getLimit(), sort);
    }
}
