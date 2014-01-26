package com.plj.common.tools.mybatis.page.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import java.util.regex.Matcher;
import java.util.regex.Pattern;*/

/**
 * 公用的sql语句帮助�?
 * @author zhengxing
 * @see
 * @version
 */
public class SQLHelper 
{
	/**
	 * 得到查询总数的sql
	 */
	public static String getCountString(String querySelect) {
		
		querySelect		= getLineSql(querySelect);
		int orderIndex  = getLastOrderInsertPoint(querySelect);
		
		int formIndex   = getAfterFormInsertPoint(querySelect);
		String select   = querySelect.substring(0, formIndex);
		
		//如果SELECT 中包�?DISTINCT 只能在外层包含COUNT
		if (select.toLowerCase().indexOf("select distinct") != -1 
				|| querySelect.toLowerCase().indexOf("group by")!=-1 
				|| querySelect.toLowerCase().indexOf("intersect") != -1
				|| querySelect.toLowerCase().indexOf("union") != -1) 
		{
			return new StringBuffer(querySelect.length()).append(
					"select count(1) count from (").append(
					querySelect.substring(0, orderIndex)).append(" ) t")
					.toString();
		}
		 else 
		 {
			return new StringBuffer(querySelect.length()).append(
					"select count(1) count ").append(
					querySelect.substring(formIndex, orderIndex)).toString();
		}
	}
	
	/**
	 * 将SQL语句变成�?��语句，并且每个单词的间隔都是1个空�?
	 * 
	 * @param sql SQL语句
	 * @return 如果sql是NULL返回空，否则返回转化后的SQL
	 */
	public static String getLineSql(String sql) 
	{
		return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
	}
	
	
	/**
	 * 得到�?���?��Order By的插入点位置
	 * @return 返回�?���?��Order By插入点的位置
	 */
	private static int getLastOrderInsertPoint(String querySelect)
	{
		int orderIndex = querySelect.toLowerCase().lastIndexOf("order by");
		if (orderIndex == -1
				|| !isBracketCanPartnership(querySelect.substring(orderIndex,querySelect.length()))) 
		{
			//throw new RuntimeException("My SQL 分页必须要有Order by 语句!");
			return querySelect.length();
		}
		return orderIndex;
	}
	
	/**
	 * 得到SQL第一个正确的FROM的的插入�?
	 */
	private static int getAfterFormInsertPoint(String querySelect)
	{
		String regex = "\\s+FROM\\s+";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(querySelect);
		while (matcher.find()) 
		{
			int fromStartIndex = matcher.start(0);
			String text = querySelect.substring(0, fromStartIndex);
			if (isBracketCanPartnership(text)) 
			{
				return fromStartIndex;
			}
		}
		return 0;
	}
	
	/**
	 * 判断括号"()"是否匹配,并不会判断排列顺序是否正�?
	 * 
	 * @param text
	 *            要判断的文本
	 * @return 如果匹配返回TRUE,否getIndexOfCount则返回FALSE
	 */
	private static boolean isBracketCanPartnership(String text) 
	{
		if (text == null
				|| (getIndexOfCount(text, '(') != getIndexOfCount(text, ')')))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 得到�?��字符在另�?��字符串中出现的次�?
	 * @param text	文本
	 * @param ch    字符
	 */
	private static int getIndexOfCount(String text, char ch) 
	{
		int count = 0;
		for (int i = 0; i < text.length(); i++)
		{
			count = (text.charAt(i) == ch) ? count + 1 : count;
		}
		return count;
	}
}
