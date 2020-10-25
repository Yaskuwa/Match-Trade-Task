# Zadanie rekrutacyjne

Aplikacja po uruchomieniu automatycznie łączy się do websocketu giełdy coinbase pro i subskrybuje kanał ticker. Otrzymane z api wiadomości na bierząco mapowane są według wymagań opisanych w zadaniu, a otrzymany JSON wyświetlany jest w konsoli.

Aplikacja tworzy również dwa endpointy, które pozwalają na pobranie ostatnich wyników dla każdego z instrumentów.

Pierwszy endpoint, który jest pod adresem /ticker, wysyła ostatnie wyniki po otrzymaniu żądania HTTP GET. 

Drugi endpoint, który jest pod adresem /topic/ticker, pozwala na subskrypcję z użyciem WebSocketu. Endpoint ten co 3 sekundy wysyła ostatnie wyniki z kanału ticker giełdy.
