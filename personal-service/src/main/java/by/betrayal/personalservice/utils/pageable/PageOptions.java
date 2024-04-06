package by.betrayal.personalservice.utils.pageable;

public record PageOptions(Integer limit, Integer page) {

    public Integer offset() {
        return limit() * page();
    }
}
