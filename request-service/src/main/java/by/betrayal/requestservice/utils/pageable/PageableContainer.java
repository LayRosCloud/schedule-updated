package by.betrayal.requestservice.utils.pageable;

import java.util.List;

public record PageableContainer<TEntity> (Long totalCount, List<TEntity> items) {

}
