package dunzo.store;

import dunzo.enums.ExceptionEnum;
import dunzo.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralStore<K,V> {
    private Map<K,V> map;

    protected GeneralStore(){
        map = new HashMap<>();
    }

    public V getByKey(K key){
        V element = map.get(key);
        if(null == element)
            throw new NotFoundException(ExceptionEnum.NOT_FOUND);
        return element;

    }
    public List<V> getAllElements(){
        return new ArrayList<>(map.values());

    }
    public void addElement(K key,V value){
        map.put(key,value);
    }
    public void removeElementByKey(K key){
        map.remove(key);
    }

}
