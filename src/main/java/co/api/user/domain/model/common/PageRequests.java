package co.api.user.domain.model.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PageRequests {
    private int page;
    private int size;
    private String sort;
}