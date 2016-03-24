package BAM.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class BAMAgentClassLoader extends ClassLoader{
	private String name;
	private Jar jar;
	private ClassLoader loader;
	private HashMap<String, byte[]> hm;
	
	public BAMAgentClassLoader(String name, ClassLoader classLoader) {
		super(classLoader);
		try {
			jar = new Jar(name);
		} catch (JarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.name = name;
		loader = classLoader;
		integrateCode(jar);
	}
	
	public BAMAgentClassLoader(ClassLoader classLoader) {
		loader = classLoader;
	}
	
	public void integrateCode(Jar jar){
		for(Entry<String, byte[]> entry : jar.classIterator()){
			hm.put(entry.getKey(), entry.getValue());
		}
	}
	
	public Jar extractCode(){
		File temp = null;
		Jar jar = null;
		try {
		    try {
				temp = File.createTempFile("tempjar",".jar");
				
				Manifest manifest = new Manifest();
				manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
				JarOutputStream jos = new JarOutputStream(new FileOutputStream(temp), manifest);
				
				for(Entry<String, byte[]> entry : hm.entrySet()){
					jos.putNextEntry(new JarEntry(entry.getKey()));
					jos.write(entry.getValue());
				}
				
				jos.close();
				
				jar = new Jar(temp.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
		    temp.delete();
		}
		return jar;
	}
}
