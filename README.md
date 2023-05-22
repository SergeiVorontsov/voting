Restaurants voting application
===============================

This is a voting system for deciding where to have lunch.
2 types of users: admin and regular users. 

Admin can input a restaurant and it's lunch menu of the day (just a dish name and price).
Admin can create new menus, new dishes, update dishes. 

Users can vote for a restaurant they want to have lunch at today.
Only one vote counted per user.
If user votes again the same day:
- if it is before 11:00 we assume that he changed his mind;
- if it is after 11:00 then it is too late, vote can't be changed.

Supposed each restaurant provides a new menu each day. So restaurants that have not been
provided a today lunch menu, don't take part in the voting today.

### Test credentials:
- user@yandex.ru / password
- admin@gmail.com / admin
- guest@gmail.com / guest


[REST API documentation](http://localhost:8090/swagger-ui/index.html)