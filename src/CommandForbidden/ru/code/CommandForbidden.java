package ru.code;


import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class CommandForbidden extends JavaPlugin implements Listener {


    /**
     * Вкл
     */
    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
    }


    /**
     * Здесь вроде как, всё по стандарту
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args)
    {
        if (sender.hasPermission("cforbidden.bypass") || sender.equals(Bukkit.getConsoleSender()))
        {
           if (args.length == 0 || ! args[0].equalsIgnoreCase("reload"))
           {
              sender.sendMessage(
                  Utilits.getMsg(
                      this.getConfig().getString("cmdHelp")
                  )
              );
           }
           else
           {
              this.reloadConfig();
              sender.sendMessage(
                  Utilits.getMsg(
                      "&6[&d" + this.getName() + "&6] &r" + this.getConfig().getString("cmdRelod")
                  )
              );
           }

           return true;
        }

        sender.sendMessage(
            Utilits.getMsg(this.getConfig().getString("defaultMsg"))
        );

        return false;
    }


    /**
     * {@link}https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/player/PlayerCommandPreprocessEvent.html#isCancelled()
     * 
     * This event is called whenever a player runs a command (by placing a slash at the start of their message).
     * It is called early in the command handling process, and modifications in this event (via setMessage(String)) will be shown in the behavior.
     * Many plugins will have no use for this event, and you should attempt to avoid using it if it is not necessary.
     * 
     * Не советуют избавлятся от ведущего слэша в команде...priority = EventPriority.LOWEST, 
     */
    @EventHandler(ignoreCancelled = false)
    public void onCommand(final PlayerCommandPreprocessEvent event)
    {
        /**
         * Приводим к нижнему регистру
         * Никто за нас этого не сделает!
         * А такие команды, как op или Op для PlayerCommandPreprocessEvent одно и тоже.
         * А для нас нет, так как запрещая /op - /Op отработает всё равно
         */
        final String command = event.getMessage().toLowerCase();

        final ConfigurationSection config = this.getConfig().getConfigurationSection("forbidden");
        /**
         * Не знаю как в java, но в других языках, которые знаю
         * Объект или ещё чего, прописанный в контексте цикла, будет отрабатывать при каждой итерации
         * Поэтому вынес во вне...
         */
        final Set<String> keys = config.getKeys(false);

        for (String k : keys)
        {
          /**
           * Чтобы когда запрещаем, например: "/op admin" - не запрещался и "/op admindada" и т. д.
           * А "/op admin args" уже не прокатит!
           * Поэтому, проверка по началу выражения .startsWith() нам не подходит
           * .substring() тоже отказалась работать - не знаю почему
           * И по этому regex рулит балом
           */
            if (command.matches("^" + k + "( .*)?$"))
            {
               if (event.isCancelled())
               {
                  /**
                   * Для совместимости с другими запрещалками команд
                   * Логироваем
                   */
                  Logger.getLogger("CommandForbidden").warning("[!] This " + command + " previously announced!");
               }

               /**
                * Как по другому вычислить количество аргуметов, не допёр
                * Пришлось создавать новый объект
                */
               else if (Utilits.split(command, ' ').length == 1 || ! config.getBoolean(k + ".args", false))
               {
                   event.getPlayer().sendMessage(
                       Utilits.getMsg(
                           (
                               config.contains(k + ".message") 
                                   ? config.getString(k + ".message")
                                   : this.getConfig().getString("defaultMsg")
                           )
                       )
                   );

                   event.setCancelled(true);
               }

               /**
                * Собственно: если наш огурчик, то прекращаем цикл за ненадобностью
                */
               break;
            }

            /**
             * Псевдооптимизация )))))))))))
             */
            continue;
        }
    }
}
