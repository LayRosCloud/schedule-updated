package by.betrayal.scheduleservice.utils.pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class PageableFactory {

    public static Pageable createPageable(PageableOptions options) {
        var sort = SortFactory.getSortIdAsc();
        return PageRequest.of(options.getOneLessPage(), options.getLimit(), sort);
    }
}
