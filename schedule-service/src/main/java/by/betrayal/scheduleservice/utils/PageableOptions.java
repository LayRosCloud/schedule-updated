package by.betrayal.scheduleservice.utils;

import lombok.Getter;

@Getter
public class PageableOptions {
    private Integer limit;
    private Integer page;

    public Integer getOneLessPage() {
        return page - 1;
    }

    public void setPage(Integer page) {
        if (page < 1) {
            throw ThrowableUtils.getBadRequestException("Error! Page is min 1");
        }
        this.page = page;
    }

    public void setLimit(Integer limit) {
        if (limit < 1) {
            throw ThrowableUtils.getBadRequestException("Error! Limit is min 1");
        }
        this.limit = limit;
    }
}
