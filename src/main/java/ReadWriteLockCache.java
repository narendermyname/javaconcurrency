import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 */

/**
 * Cache using read write Lock
 * @author ntanwa
 *
 */
public class ReadWriteLockCache {

	private static final Map<String,String> cache = new HashMap<>();
	
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	private Lock rLock = rwLock.readLock();
	
	private Lock wLock = rwLock.writeLock();
	
	public static void main(String...a){
		
		
	}
	
	/**
	 * Read from cache
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		
		rLock.lock();
		try {
			return cache.get(key);
		} catch(Exception e){
			System.out.println(e);
		} finally {
			rLock.unlock();
		}
		return null;
	}
	
	/**
	 * Put value in cache
	 * @param key
	 * @param value
	 */
	public void putValue(String key, String value){
		wLock.lock();
		try {
			cache.put(key, value);
		} catch (Exception e) {
			System.out.println("Error : "+e.getMessage());
		} finally {
			wLock.unlock();
		}
	}
	
}
