SET GLOBAL event_scheduler = ON;

delimiter $$
create event myEvent
    on schedule every 15 minute
    do
        begin
            CALL updateSpecial();
        end $$
delimiter ;



/*
SQL> DROP EVENT myEvent;
SQL> SET GLOBAL event_scheduler = ON;
SQL> show processlist;
*/