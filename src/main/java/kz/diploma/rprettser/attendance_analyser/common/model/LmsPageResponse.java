package kz.diploma.rprettser.attendance_analyser.common.model;

import lombok.Data;

import java.util.List;

@Data
public class LmsPageResponse<T> {
    private List<T> content;
    private int totalElements;
    private int numberOfElements;
    private int totalPages;
    private boolean last;
}