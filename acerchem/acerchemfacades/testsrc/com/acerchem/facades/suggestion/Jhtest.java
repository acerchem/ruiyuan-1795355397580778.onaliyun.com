package com.acerchem.facades.suggestion;

import de.hybris.platform.util.Config;

public class Jhtest {

	public static void main(String[] args){
		String test = "sdfhalkjl{BAK}";
		
		test = test.replace("{BAK}", "ABC");
		String s = Config.getString("as", "sss");
		System.out.println(test);
		System.out.println(s);
	}
}
