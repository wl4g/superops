package com.wl4g.devops.tool.common.collection;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Collector.Characteristics;

/**
 * Enhanced collectors utility.
 * 
 * {@link Collectors}
 * 
 * @author wanglsir@gmail.com, 983708408@qq.com
 * @version 2019年12月26日 v1.0.0
 * @see
 */
public abstract class Collectors2 {

	/**
	 * Returns a {@code Collector} that accumulates the input elements into a new
	 * {@code Set}. There are no guarantees on the type, mutability,
	 * serializability, or thread-safety of the {@code Set} returned; if more
	 * control over the returned {@code Set} is required, use
	 * {@link Collectors#toCollection(Supplier)}.
	 *
	 * <p>
	 * This is an {@link Collector.Characteristics#UNORDERED unordered} Collector.
	 *
	 * @param <T> the type of the input elements
	 * @return a {@code Collector} which collects all the input elements into a
	 *         {@code Set}
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collector<T, ?, Set<T>> toLinkedHashSet() {
		return Collector.of(LinkedHashSet::new, Set::add, (s, rs) -> {
			s.add((T) rs);
			return s;
		}, Characteristics.IDENTITY_FINISH);
	}
}
