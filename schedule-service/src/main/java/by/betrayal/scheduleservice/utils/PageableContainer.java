package by.betrayal.scheduleservice.utils;

import java.util.List;

public record PageableContainer<TEntity>(Long totalCount, List<TEntity> items) {
}
