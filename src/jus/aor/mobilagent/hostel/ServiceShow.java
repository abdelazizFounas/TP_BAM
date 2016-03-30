package jus.aor.mobilagent.hostel;

import jus.aor.mobilagent.kernel._Service;

public class ServiceShow implements _Service<Integer>{
	private int i = -1;
	
	public ServiceShow(Object... args){
		i = Integer.parseInt((String) args[0]);
	}
	
	@Override
	public Integer call(Object... params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		System.out.println("Le service est fonctionnel " + params[0] + " " + params[1] + " " + i);
		return 1;
	}
}
