package tar;

public class Book {
	
	private String title;
	private String author;
	private String description;
	
	  public static final String AUTHOR = "author";
	  public static final String CREATED_BY = "createdBy";
	  public static final String CREATED_BY_ID = "createdById";
	  public static final String DESCRIPTION = "description";
	  public static final String ID = "id";
	  public static final String PUBLISHED_DATE = "publishedDate";
	  public static final String TITLE = "title";
	  public static final String IMAGE_URL = "imageUrl";
	
	
	public Book(){
		
	}
		
	public Book(String title, String author, String description) {
		super();
		this.title = title;
		this.author = author;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
