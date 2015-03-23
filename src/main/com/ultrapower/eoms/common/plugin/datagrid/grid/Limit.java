package com.ultrapower.eoms.common.plugin.datagrid.grid;


public interface Limit {

	 //  public FilterSet getFilterSet();

	    public int getRowEnd();

	    public int getRowStart();

	 //   public Sort getSort();

	    public int getPage();
	    
	    public int getPageSize();
	    public int getTotalPage();
	   // public int getCurrentRowsDisplayed();

	    public int getTotalRows();

	//    public boolean isFiltered();

	//    public boolean isCleared();

//	    public boolean isSorted();
	    

	//    public boolean isExported();

	    public String getSortField();//排序字段
	    public int getSortType();//排序类型 1正序 2逆序
	    public void setRowAttributes(int totalRows, int defaultRowsDisplayed);	
}
