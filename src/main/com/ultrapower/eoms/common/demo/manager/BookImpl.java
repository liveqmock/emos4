package com.ultrapower.eoms.common.demo.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.demo.model.Book;
import com.ultrapower.eoms.common.demo.service.BookService;

@Transactional
public class BookImpl implements BookService {

	IDao<Book> theBookDao;

	public IDao<Book> getTheBookDao() {
		return theBookDao;
	}

	public void setTheBookDao(IDao<Book> theBookDao) {
		this.theBookDao = theBookDao;
	}

	public Book findById(String id) {
		return theBookDao.get(id);
	}

	public void save(Book book) {
		if (book.getIsbn() != null && !("").equals(book.getIsbn()))
			theBookDao.saveOrUpdate(book);
		else
			theBookDao.save(book);
	}

	public void delete(String isbn) {
		if (isbn != null && !("").equals(isbn)) {
			String[] isbnids = isbn.split(",");
			for (String isbnid : isbnids) {
				Book book = theBookDao.get(isbnid);
				theBookDao.remove(book);
			}
		}
	}

	public List<Book> queryList() {
		List<Book> users = new ArrayList<Book>();
		PageLimit pageLimit = PageLimit.getInstance();
		//String hsql=" from Book where title like ?";
		//Object values[]={"%哈%"};
		//users = theBookDao.pagedQuery(hsql, pageLimit,values);
		String hsql=" from Book ";
		
		users = theBookDao.pagedQuery(hsql, pageLimit);		
		return users;
	}
	public List<Book> booksList() {
		PageLimit pageLimit = PageLimit.getInstance();
		return theBookDao.getAll();
	}

	public void edit() {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args){
		System.out.println("aaaaaaaa");
		
	}

}
