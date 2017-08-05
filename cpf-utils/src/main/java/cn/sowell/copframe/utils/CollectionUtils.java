package cn.sowell.copframe.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class CollectionUtils {
	/**
	 * 将集合转换为map
	 * @param items 集合对象，不能为null
	 * @param keyGetter 每个元素对象获取其key的方法
	 * @return {@link LinkedHashMap}对象
	 */
	public static <V, T> Map<T, V> toMap(Collection<V> items, Function<V, T> keyGetter){
		Assert.notNull(items);
		Assert.notNull(keyGetter);
		Map<T, V> map = new LinkedHashMap<T, V>();
		items.forEach(item->{
			T key = keyGetter.apply(item);
			if(key != null){
				map.put(key, item);
			}
		});
		return map;
	}
	/**
	 * 将集合中key相同的组合放到list中，以这个key为返回map的key，list为map的value
	 * @param items
	 * @param keyGetter
	 * @return
	 */
	public static <T, V> Map<T, List<V>> toListMap(Collection<V> items, Function<V, T> keyGetter){
		Assert.notNull(items);
		Assert.notNull(keyGetter);
		Map<T, List<V>> map = new LinkedHashMap<T, List<V>>();
		items.forEach(item->{
			T key = keyGetter.apply(item);
			if(key != null){
				List<V> list = map.get(key);
				if(list == null){
					list = new ArrayList<V>();
					map.put(key, list);
				}
				list.add(item);
			}
		});
		return map;
	}
	
	public static String toChain(Collection<?> source, String spliter) {
		if(source == null){
			return null;
		}else{
			StringBuffer buffer = new StringBuffer();
			String _spliter = spliter == null? "": spliter;
			source.forEach(item -> {
				buffer.append(FormatUtils.toString(item) + _spliter);
			});
			if(buffer.length() > 0){
				buffer.delete(buffer.length() - spliter.length() - 1, buffer.length());
			}
			return buffer.toString();
		}
	}

	public static String toChain(Collection<?> source) {
		return toChain(source, ",");
	}
	
	/**
	 * 从集合source的元素中抽取属性，添加到另一个集合中作为元素
	 * @param source
	 * @param target
	 * @param itemGetter
	 */
	public static <T, R> void appendTo(Collection<T> source, Collection<R> target, Function<T, R> itemGetter) {
		if(source != null && target != null && itemGetter != null){
			source.forEach(item -> {
				R r = itemGetter.apply(item);
				target.add(r);
			});
		}
	}
	/**
	 * 从集合source中抽取属性，放到一个新的list中作为元素
	 * @param source 集合
	 * @param itemGetter 获得属性的方法
	 * @return
	 */
	public static <T, R> ArrayList<R> toList(Collection<T> source, Function<T, R> itemGetter){
		ArrayList<R> result = new ArrayList<R>();
		appendTo(source, result, itemGetter);
		return result;
	}
}
