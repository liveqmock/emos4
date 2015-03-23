package  com.ultrapower.eoms.common.demo.service;

import java.util.List;

import com.ultrapower.eoms.common.demo.model.Book;



public interface BookService {
	
      public abstract Book findById(String id);
     
      public abstract void save(Book book);
     
      public abstract List<Book> booksList();
     
      public abstract List<Book> queryList();
      
      public abstract void delete(String ids);
	
}
