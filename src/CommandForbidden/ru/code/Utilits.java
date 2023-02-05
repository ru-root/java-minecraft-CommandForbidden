package ru.code;

import org.bukkit.ChatColor;

/**
 * Запрещаем создание экземпляра этого класса.
 */
abstract public class Utilits {


    /**
     * Форматирует сообщение
     * 
     * @param  String msg
     * @return String
     */
    final public static String getMsg(String msg)
    {
        return " " + ChatColor.translateAlternateColorCodes('&', msg.trim());
    }


    /**
     * Чтобы не словить ArrayIndexOutOfBoundsException из-за несовместимости Java 7/8
     * По скорости, в большинстве случаев, опережает апачевский аналог
     * 
     * @param  String string
     * @param  char   delimiter   символ-разделитель
     * @return String[]
     */
    final public static String[] split(String string, char delimiter)
    {
        int n = 1;
        int i = 0;
        while(true)
        {
            i = string.indexOf(delimiter, i);
            if (i == -1)
            {
               break;
            }
            n++;
            i++;
        }

        if (n == 1)
        {
           return new String[] {string}; 
        }

        String[] result = new String[n];
        n = 0;
        i = 0;

        int start = 0;
        while(true)
        {
            i = string.indexOf(delimiter, start);
            
            if (i == -1)
            {
               break;
            }
            result[n++] = string.substring(start, i);
            start = i+1;
        }

        result[n] = string.substring(start);
        return result;
    }
}
