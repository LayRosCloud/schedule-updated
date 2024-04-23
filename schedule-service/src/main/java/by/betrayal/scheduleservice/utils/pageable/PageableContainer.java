package by.betrayal.scheduleservice.utils.pageable;

import java.util.List;

public record PageableContainer<TEntity>(Long totalCount, List<TEntity> items) {
    public static final String HEADER_TOTAL_COUNT = "x-total-count";
    public static final String PARAM_PAGE = "_page";
    public static final String PARAM_LIMIT = "_limit";
}
