package com.frc868.filters.groups;

import com.frc868.filters.Canny;
import com.frc868.filters.YellowTote;


public class Filter2015 extends FilterGroup {
	
	public Filter2015() {
		add(new YellowTote());
		add(new Canny(100, 200));
	}

}
