package Exception;

public class PageNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public PageNotFoundException(String url) {
		System.out.println(url+":: does not exist!");
	}

	public PageNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	
}
