package com.example.common.base;

import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @ClassName PageResponse
 * @Description TODO描述
 * @Author mingj
 * @Date 2019/12/22 17:54
 **/
@Data
public class PageResponse<T> {

    private int pageNo;

    private int pageSize;

    private int total;

    private List<T> list;
}
