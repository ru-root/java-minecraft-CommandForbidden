## Minecraft Plugin CommandForbidden

[![https://camo.githubusercontent.com/3c49db13b34a1bcc7c23fc60b00003a32be73d06a1933b492f8b77fd9fd4e3f0/68747470733a2f2f7472617669732d63692e6f72672f6d6f76657068702f636c6173736c6f616465722e7376673f6272616e63683d6d6173746572](https://camo.githubusercontent.com/3c49db13b34a1bcc7c23fc60b00003a32be73d06a1933b492f8b77fd9fd4e3f0/68747470733a2f2f7472617669732d63692e6f72672f6d6f76657068702f636c6173736c6f616465722e7376673f6272616e63683d6d6173746572)](https://github.com/ru-root/java-minecraft-CommandForbidden/blob/master/build/CommandForbidden.jar?raw=true)
[![https://img.shields.io/badge/java-16.0.1-00BFFF](https://img.shields.io/badge/java-16.0.1-00BFFF)](https://www.php.net/releases/8.0/en.php)





- Навигатор по командам, скрытие команд.

___


## Usage

- Скрыть любую команду даже от игроков с `op`:
  - Будет показано сообщение по умолчанию, как для этой команды, так и для её альянсов.
```
forbidden:
    /pl: []
```

- Будет показано сообщение из строки <strong>message:</strong>, для этой команды если отсутствуют параметры команды.
  - <i>Соответственно команда</i> `/promote vasya` <i>отработает</i>!
```
forbidden:
    /pl:
        message: "&Разрешение у админа спросил на просмотр списка?"
```

- Если указанно:
    - То параметры команды уже не сработают и будет показано сообщение.
    - Соответственно и если параметр <strong>args:</strong> вообще отсутствует будет аналогичное поведение.
```
    args: false
```


Для чего так делать!?
Всё просто, пускай некоторые подумают, что это паранойя, но лишним не будет по причине того, что; например команда: <strong>/promote</strong> без параметров показывает версию плагина, что упрощает для агрессивно настроенных псевдохакеров, найти эксплоит для того или иного плагина.
Во многих плагинах выводиться название и версия плагина, если ввести команду без параметров.
Не знаю для чего так делают плагинисты, псевдопиар такой, или ещё что!?
Этим самым мы в корне усложняем жизнь таким гостям сервера.
И одновременно не ограничиваем игроков в функционале команд или минимум ограничиваем.
  
  
Теперь о гибкости. Тот же <strong>NoCheatPlus</strong> не позволяет такого делать, только одно сообщение для всех запрещённых команд.
Что лишает возможности сделать например:
[INDENT][I]У нас работала команда [B]/donate[/B] и по каким-то причинам мы решили данную команду переименовать в [B]/donat[/B].[/I]
- Прописываем в файле:[/INDENT]
```
forbidden:
    /donate:
        message: "&6Используй команду: &a/donat &6/donate больше не используется на данном сервере."
```

#### Собственно это основное, в остальном в конфиге разобраться не сложно.
  
  

