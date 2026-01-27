# Wiarygodnik
Projekt wiarygodnik

### Uruchamianie pełnej infrastruktury
W przypadku uruchamiania infrastruktury w środowisku Docker'owym pojawił się problem z Keycloak'iem. 
Otóż zwracając się po token z frontend'u wysyłamy żądanie z `localhost`, natomiast żeby serwisy mogły zweryfikować token muszą wysląć żądanie w sieci kontenerów (bridge) na domenę `keycloak`.
Ze względu na wymóg tej samej domeny w `iss` zawartym w JWT zwracanym z Keycloak'a zostało zastosowane obejście, gdzie musimy oszukać Keycloak'a odwołując się do niego po tej samej domenie - `keycloak`.
Należy więc dodać do `/etc/hosts` poniższy wiersz:

```
127.0.0.1 keycloak
```

Reszta konfiguracji jest przygotowana pod to rozwiązanie.
Wystarczy teraz uruchomić:

```shell
docker compose up -d
```