package com.umitsen.onlinebookstore.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
private List<BookRequest> bookRequestList;
    public List<BookRequest> getBooks() {
        return bookRequestList;
    }



}
