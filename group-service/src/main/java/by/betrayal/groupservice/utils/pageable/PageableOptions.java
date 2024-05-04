package by.betrayal.groupservice.utils.pageable;

import by.betrayal.groupservice.utils.ExceptionUtils;
import lombok.Getter;

@Getter
public class PageableOptions {

    private Integer limit;
    private Integer page;

    public PageableOptions(Integer limit, Integer page) {
        this.limit = limit;
        this.page = page;
    }

    public Integer getOneLessPage() {
        return page - 1;
    }

    public void setLimit(Integer limit) {
        if (limit < 1) {
            throw ExceptionUtils.getBadRequestException("Limit has min 1");
        }
        this.limit = limit;
    }

    public void setPage(Integer page) {
        if (page < 1) {
            throw ExceptionUtils.getBadRequestException("Page has min 1");
        }
        this.page = page;
    }
}
