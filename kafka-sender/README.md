# Kafka Sender

Тестовая отправка событий распознавания лиц в топик `face-recognition-events` через консольный продюсер внутри контейнера `kafka` (требуется запущенный `docker compose up -d`).

## Отправка события

```bash
./send.sh <studentName> <studentLastName> <classroomName> [confidence] [recognizedAt]
```

Примеры:

```bash
./send.sh Ivan Ivanov A-101
./send.sh Ivan Ivanov A-101 0.87
./send.sh Ivan Ivanov A-101 0.87 2026-06-07T19:00:00
```

По умолчанию `confidence` = 0.95, `recognizedAt` = текущее время в Asia/Almaty.

## Отправка произвольного JSON

Для тестирования невалидных payload (попадание в FAILED и ретраи):

```bash
./send-raw.sh '{"studentName":"Ivan","classroomName":"NoSuchRoom"}'
```

## Формат события

```json
{
  "studentName": "Ivan",
  "studentLastName": "Ivanov",
  "classroomName": "A-101",
  "recognizedAt": "2026-06-07T19:00:00",
  "confidence": 0.95
}
```
