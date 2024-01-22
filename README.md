# Prosty Serwer Czatu w Java

Zaawansowany projekt w języku Java - prosty serwer czatu obsługujący kilku klientów jednocześnie.

## Funkcje

1. **Czat Grupowy:** Klienci mogą dołączać do czatu, wysyłać wiadomości, i opuszczać czat.
2. **Wielu Klientów:** Serwer obsługuje kilku klientów jednocześnie.

## Instrukcje

1. Skompiluj program za pomocą komendy:
    ```bash
    javac ChatServer.java
    ```

2. Uruchom serwer, podając numer portu jako argument (opcjonalnie):
    ```bash
    java ChatServer
    ```

    Domyślnie serwer działa na porcie 8888.

3. Uruchom klienta czatu, podając adres IP serwera i numer portu (jeśli inny niż domyślny):
    ```bash
    java ChatClient <adres_IP> <numer_portu>
    ```

4. Podaj imię i zacznij rozmawiać z innymi klientami w czacie.

5. Aby wyjść, wpisz "exit".

## Ostrzeżenie

Ten program jest przeznaczony do celów edukacyjnych i demonstracyjnych. W rzeczywistych projektach należy rozważyć zastosowanie zabezpieczeń, takich jak protokół szyfrowania (SSL/TLS), autentykacja, i zarządzanie wątkami.

## Licencja

Ten program jest dostępny na licencji GNU. Szczegóły można znaleźć w pliku [LICENSE](LICENSE).
