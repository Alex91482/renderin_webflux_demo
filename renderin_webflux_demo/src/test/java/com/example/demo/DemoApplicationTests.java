package com.example.demo;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

	//@Test
	public void lineStringTest(){
		var coordinateArray = Arrays.asList(
				new Coordinate(1.5, 1.5),
				new Coordinate(2.0, 2.0),
				new Coordinate(2.5, 2.5)
		).toArray(Coordinate[]::new);

		var geometryFactory = new GeometryFactory();
		var lineString = geometryFactory.createLineString(coordinateArray);

	}

	//@Test
	public void testMonoSuccessByFlux(){
		Optional<Object> i = Mono.create(emitter -> {
			AtomicInteger x = new AtomicInteger(0);

			Flux.just(1,2,3,4,5,6,7,8,9).subscribe(
					x::addAndGet,
					throwable -> {
						System.err.println(throwable.getMessage());
						throwable.printStackTrace();
					},
					() -> {
						emitter.success(x.get());
					}
			);
		}).blockOptional();

		boolean b = i.isPresent();

		assert (b);
		assert ((int)i.get() == 45);
	}

	//@Test
	public void testMonoSuccessByFluxAndErrorInBlockComplete(){
		Optional<Object> i = Mono.create(emitter -> {
			AtomicInteger x = new AtomicInteger(0);

			Flux.just(1,2,3,4,5,6,7,8,9).subscribe(
					x::addAndGet,
					throwable -> {
						System.err.println(throwable.getMessage());
						throwable.printStackTrace();
					},
					() -> {
						try{
							throw new RuntimeException("ErrorInBlockComplete");
						}catch (Exception e){
							System.out.println("Exception: " + e.getMessage());
							emitter.success(-1);
						}
					}
			);
		}).blockOptional();

		boolean b = i.isPresent();

		assert (b);
		assert ((int)i.get() == -1);
	}

}
