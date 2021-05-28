package com.zycw.distributed.database.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * @program:
 * @description: 基于JdbcTemplate的Orcale分页工具类
 * @author: yu
 * @create: 2018-04-24 16:25
 */
public class PageUtil<T> {

    public static final int NUMBERS_PER_PAGE = 10;
    // 一页显示的记录数
    private int numberPage;
    // 记录总数
    private int totalRows;
    // 总页数
    private int totalPages;
    // 当前页码
    private int currentPage;
    // 起始行数
    private int startIndex;
    // 结束行数
    private int lastIndex;
    // 参数
    private Object[] params;

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    // 结果集存放List
    private List<T> resultList;

    public PageUtil() {

    }

    /**
     * 分页构造函数
     * 
     * @param sql
     *            根据传入的sql语句得到一些基本分页信息
     * @param currentPage
     *            当前页
     * @param numberPage
     *            每页记录数
     * @param jTemplate
     * @param rowMapper
     */
    public PageUtil(String sql, int currentPage, int numberPage, JdbcTemplate jTemplate, RowMapper<T> rowMapper, Object[] params) {
        this.currentPage = currentPage;
        if (jTemplate == null) {
            throw new IllegalArgumentException("com.deity.ranking.util.Pagination.jTemplate is null,please initial it first. ");
        } else if (sql == null || "".equals(sql)) {
            throw new IllegalArgumentException("com.deity.ranking.util.Pagination.sql is empty,please initial it first. ");
        }
        // 设置每页显示记录数
        setNumberPage(numberPage);
        // 设置要显示的页数
        setCurrentPage(currentPage);
        // 计算总记录数
        StringBuffer totalSql = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSql.append(sql);
        totalSql.append(" ) totalTable ");
        // 总记录数
        setTotalRows(jTemplate.queryForObject(totalSql.toString(), params, Integer.class));
        // 计算总页数
        setTotalPages();
        // 计算起始行数
        setStartIndex();
        // 计算结束行数
        setLastIndex();
        // 参数
        setParams(params);
        // 构造oracle数据库的分页语句
        StringBuffer pageQuerySql = new StringBuffer(" SELECT * FROM ( ");
        pageQuerySql.append(" SELECT temp.* ,ROWNUM num FROM ( ");
        pageQuerySql.append(sql);
        pageQuerySql.append("　) temp where ROWNUM <= " + lastIndex);
        pageQuerySql.append(" ) WHERE　num > " + startIndex);
        // 装入结果集
        setResultList(jTemplate.query(pageQuerySql.toString(), params, rowMapper));
    }

    /**
     * 分页构造函数
     * 
     * @param sql
     *            根据传入的sql语句得到一些基本分页信息
     * @param currentPage
     *            当前页
     * @param numberPage
     *            每页记录数
     * @param jTemplate
     * @param rowMapper
     */
    public PageUtil(int totalRows, String sql, int currentPage, int numberPage, JdbcTemplate jTemplate, RowMapper<T> rowMapper, Object[] params) {
        this.currentPage = currentPage;
        if (jTemplate == null) {
            throw new IllegalArgumentException("com.deity.ranking.util.Pagination.jTemplate is null,please initial it first. ");
        } else if (sql == null || "".equals(sql)) {
            throw new IllegalArgumentException("com.deity.ranking.util.Pagination.sql is empty,please initial it first. ");
        }
        // 设置每页显示记录数
        setNumberPage(numberPage);
        // 设置要显示的页数
        setCurrentPage(currentPage);
        // 总记录数
        setTotalRows(totalRows);
        // 计算总页数
        setTotalPages();
        // 计算起始行数
        setStartIndex();
        // 计算结束行数
        setLastIndex();
        // 参数
        setParams(params);
        // 构造oracle数据库的分页语句
        StringBuffer pageQuerySql = new StringBuffer(" SELECT * FROM ( ");
        pageQuerySql.append(" SELECT temp.* ,ROWNUM num FROM ( ");
        pageQuerySql.append(sql);
        pageQuerySql.append("　) temp where ROWNUM <= " + lastIndex);
        pageQuerySql.append(" ) WHERE　num > " + startIndex);
        // 装入结果集
        setResultList(jTemplate.query(pageQuerySql.toString(), params, rowMapper));
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 计算总页数
     */
    public void setTotalPages() {
        if (totalRows % numberPage == 0) {
            this.totalPages = totalRows / numberPage;
        } else {
            this.totalPages = (totalRows / numberPage) + 1;
        }
    }

    // 改变总页数
    public void setTotalPages(int i) {
        this.totalPages = i;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex() {
        this.startIndex = (currentPage - 1) * numberPage;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    /**
     * 计算结束时候的索引
     */
    public void setLastIndex() {
        if (totalRows < numberPage) {
            this.lastIndex = totalRows;
        } else if ((totalRows % numberPage == 0) || (totalRows % numberPage != 0 && currentPage < totalPages)) {
            this.lastIndex = currentPage * numberPage;
        // 最后一页
        } else if (totalRows % numberPage != 0 && currentPage == totalPages) {
            this.lastIndex = totalRows;
        }
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
