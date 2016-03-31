package jus.aor.mobilagent.hostel;

import jus.aor.mobilagent.kernel._Service;

public class Duration implements _Service<Long>{
	public Duration(Object... args){
	}
	
	@Override
	public Long call(Object... params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return System.currentTimeMillis();
	}
}
