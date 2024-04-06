package by.betrayal.audienceservice.utils.pagination;

public record PageableOptions(Integer limit, Integer page) {
    public Integer getOffset() {
        return limit * page;
    }

}
