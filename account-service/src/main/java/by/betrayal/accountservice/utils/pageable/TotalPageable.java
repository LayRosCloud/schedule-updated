package by.betrayal.accountservice.utils.pageable;

import java.util.List;

public record TotalPageable<TEntity>(List<TEntity> items, long totalCount) {
}
