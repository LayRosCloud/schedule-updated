package by.betrayal.audienceservice.utils.pagination;

import java.util.List;

public record TotalPageable<TEntity>(List<TEntity> items, Long totalCount) {
}
