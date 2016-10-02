package com.taulukko.cassandra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

public class FunctionsTest {

	@Test
	public void tesLambida() throws Exception {
		String name = getName();
		System.out.println("r:" + name);
		Assert.assertNotNull(name);
	}

	public String getName() throws Exception {
		List<String> names = Arrays.asList("Eu","Paçoca", "Edson", "Carlos",
				"Felipe", "Wanita", "Laurinda", "Shawnda", "Vicki", "Coralee",
				"George", "Matilda", "Ashly", "Season", "Doria", "Phuong",
				"Alejandra", "Melody", "Margarette", "Sherrill", "Elsa",
				"Shawanna", "Dusty", "Edwardo", "Yukiko");

		final List<Exception> errors = new ArrayList<Exception>();

		System.out.println("1:" + Thread.currentThread().getId());

		Function<List<String>, String> function = l -> l
				.stream()
				.filter((s) -> {
					System.out
							.println(Thread.currentThread().getId() + ":" + s);
					if (s.equals("Paçoca")) {
						System.out.println("2:"
								+ Thread.currentThread().getId());
						errors.add(new Exception("Paçoca encontrada"));
						return false;
					}
					return true;
				}).findAny().get();

		System.out.println("3:" + Thread.currentThread().getId());

		// only here happens exception, se alcancar a exception
		String ret = function.apply(names);
		if (errors.size() > 0) {
			throw errors.get(0);
		}
		return ret;
	}

}
