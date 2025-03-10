package co.api.user.domain.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ListResponse <T>{
    private int totalPage;
    private int page;
    private List<T> items;
}
