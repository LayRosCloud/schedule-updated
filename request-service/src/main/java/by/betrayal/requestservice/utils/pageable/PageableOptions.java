package by.betrayal.requestservice.utils.pageable;

import by.betrayal.requestservice.utils.ExceptionUtils;
import lombok.Getter;

@Getter
public class PageableOptions {

    private Integer limit;
    private Integer page;

    public PageableOptions(Integer limit, Integer page) {
        setLimit(limit);
        setPage(page);
    }

    public Integer getOneLessPage() {
        return page - 1;
    }

    public void setLimit(Integer limit) {
        if (limit == null) {
            throw ExceptionUtils.getBadRequestException("Limit has null");
        }

        if (limit < 1) {
            throw ExceptionUtils.getBadRequestException("Limit has min 1 value");
        }

        this.limit = limit;
    }

    public void setPage(Integer page) {
        if (page == null) {
            throw ExceptionUtils.getBadRequestException("Page has null");
        }

        if (page < 1) {
            throw ExceptionUtils.getBadRequestException("Page has min 1 value");
        }
        this.page = page;
    }
}
