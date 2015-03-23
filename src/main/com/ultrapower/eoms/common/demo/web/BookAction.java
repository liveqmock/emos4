package com.ultrapower.eoms.common.demo.web;

import java.util.List;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.demo.model.Book;
import com.ultrapower.eoms.common.demo.service.BookService;

public class BookAction extends BaseAction {

	private String isbn;
	private Book book;
	private List<Book> books;
	private BookService bookService;
	private String isfresh ;
	
	public String getIsfresh() {
		return isfresh;
	}

	public void setIsfresh(String isfresh) {
		this.isfresh = isfresh;
	}

	public String load() {
		book = bookService.findById(isbn);
		return findForward("edit");
	}

	public String list() {
		books = bookService.booksList();
		return SUCCESS;
	}   
	public String queryList()
	{ 
		//books = bookService.booksList();
		//books = bookService.queryList();
		return SUCCESS;
	}
	
	public String addTest(){
		return SUCCESS;
	}
	
	public String queryListValidation(){
		books = bookService.booksList();
		return SUCCESS;
	}

	public String store() {
		bookService.save(book);
		return findRedirect("list");
	}

	public String remove() {
		bookService.delete(isbn);
		return findRedirect("list");
	}

	public String edit() {
		return SUCCESS;
	}
	
	public String editByValidation(){
		return SUCCESS;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public List<Book> getBooks() {
		return books;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}