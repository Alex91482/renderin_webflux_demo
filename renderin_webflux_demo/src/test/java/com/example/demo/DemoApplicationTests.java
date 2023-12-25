package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

//@SpringBootTest
class DemoApplicationTests {

	//@Test
	void contextLoads() {
	}

	//@Test
	public void testLostToArray(){
		var arrayList = new ArrayList<String>();
		arrayList.add("one");
		arrayList.add("two");
		arrayList.add("three");
		var array = arrayList.toArray(String[]::new);

		assert (array.length == 3);
		assert (array[0].equals("one"));
		assert (array[1].equals("two"));
		assert (array[2].equals("three"));
	}

}
