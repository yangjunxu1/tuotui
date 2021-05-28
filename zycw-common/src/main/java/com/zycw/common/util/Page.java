package com.zycw.common.util;

import java.util.List;

/**
 * 
 * @ClassName: Page
 * @date 2018年5月30日 下午4:47:32
 * 
 */

public class Page<T> {
    /**
     * 当前页数
     */
    private int page = 1;
    /**
     * 每页记录数
     */
    private int size = 5;
    /**
     * 总页数
     */
    private long totalPages;
    /**
     * 总记录数
     */
    private long totalElements;
    /**
     * 当前页数据列表
     */
     private List<T> content;

     
     
    public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Page(int page, int size, long totalElements, List<T> content) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.content = content;
        long hasMode = totalElements % size;
        long totalPages = (long) Math.ceil(totalElements / size);
        this.totalPages = hasMode == 0 ? totalPages : totalPages + 1;
    }

    public Page(long totalElements, List<T> content) {
        this.totalElements = totalElements;
        this.content = content;
        this.totalPages = (int) Math.ceil(totalElements / this.size);
    }

    /**
     * 
     */
    public Page() {
        super();
    }

}
