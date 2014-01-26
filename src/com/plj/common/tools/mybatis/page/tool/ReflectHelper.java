package com.plj.common.tools.mybatis.page.tool;

/*import java.lang.reflect.Field;
import java.util.Map;*/

/** 
 * 反射工具 
 * @author zhengxing 
 *  
 */  
public class ReflectHelper {  
    /** 
     * 获取obj对象fieldName的Field 
     * @param obj 
     * @param fieldName 
     * @return 
     */  
/*    public static Field getFieldByFieldName(Object obj, String fieldName)
    {

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; 
				superClass = superClass.getSuperclass()) 
		{  
            try 
            {  
                return superClass.getDeclaredField(fieldName);  
            }
            catch (NoSuchFieldException e) 
            {  
            }  
        }  
		return null;
    } */
    
    /**
     * �?��是否含有分页或本来就是分页类
     * @param obj
     * @param fieldName
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
	public static Object isPage(Object obj, String fieldName) 
    {
		if (obj instanceof java.util.Map) 
		{
			java.util.Map map = (java.util.Map)obj;
			return map.get(fieldName);

		} 
		else 
		{
    		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) 
    		{  
                try 
                {  
                    return superClass.getDeclaredField(fieldName);  
                }
                catch (NoSuchFieldException e) 
                {  
                }  
            }  
    		  return null;
    	}
    } 
  
    /** 
     * 获取obj对象fieldName的属性�? 
     * @param obj 
     * @param fieldName 
     * @return 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
/*    public static Object getValueByFieldName(Object obj, String fieldName)  
            throws SecurityException, NoSuchFieldException,  
            IllegalArgumentException, IllegalAccessException 
    {  
        Field field = getFieldByFieldName(obj, fieldName);  
        Object value = null;  
        if(field!=null)
        {  
            if (field.isAccessible()) 
            {  
                value = field.get(obj);  
            } 
            else 
            {  
                field.setAccessible(true);  
                value = field.get(obj);  
                field.setAccessible(false);  
            }  
        }  
        return value;  
    }  */
  
    /** 
     * 设置obj对象fieldName的属性�? 
     * @param obj 
     * @param fieldName 
     * @param value 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
/*    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException,IllegalArgumentException, IllegalAccessException {  
        
    	if(obj instanceof java.util.Map)
    	{
			java.util.Map map = (java.util.Map)obj;
			map.put(fieldName,value);
    	}
    	else
    	{
        	Field field = obj.getClass().getDeclaredField(fieldName);  
            if (field.isAccessible()) 
            {  
                field.set(obj, value);  
            } 
            else
            {  
                field.setAccessible(true);  
                field.set(obj, value);  
                field.setAccessible(false);  
            }  
    	}
    }  */
}  
