package BAM.client;

import java.net.URL;
import java.net.URLClassLoader;

public class BAMServerClassLoader extends URLClassLoader {
	public BAMServerClassLoader(URL[] urls, ClassLoader classLoader) {
		super(urls, classLoader);
	}
	
	public void addURL(URL url) {
		super.addURL(url);
	}
	
	public String toString() {
		return super.toString();
	}
}
