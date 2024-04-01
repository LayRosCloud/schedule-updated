package by.betrayal.personalservice.utils.pageable;

import java.util.List;

public record PageContainer<TEntity> (List<TEntity> items, Long totalCount) {
}
