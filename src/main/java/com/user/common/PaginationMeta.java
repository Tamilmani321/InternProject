package com.user.common;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import lombok.Data;


@Data
@Component
public class PaginationMeta {
	private long totalCount;
	private int pageSize;
	private int totalPage;
	private int pageNumber;
	private boolean isLast;
	private boolean isFirst;
	
	public <T>PaginationMeta createPagination(Page<T> page){
		 PaginationMeta pagination= new PaginationMeta();
		pagination.setFirst(page.isFirst());
		pagination.setLast(page.isLast());
		pagination.setPageNumber(page.getNumber());
		pagination.setPageSize(page.getSize());
		pagination.setTotalCount(page.getTotalElements());
		pagination.setTotalPage(page.getTotalPages());
		return pagination;
	}




}
