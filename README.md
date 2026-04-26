## Запуск

```bash
# 1. Поднять инфраструктуру (postgres, kafka, kafka-ui)
docker compose up -d

# 2. Поднять приложения (simplelms, attendance-analyser, frontend)
docker compose -f docker-compose.apps.yml up -d --build
```

Пересборка одного сервиса после изменения кода:
```bash
docker compose -f docker-compose.apps.yml up -d --build simplelms
docker compose -f docker-compose.apps.yml up -d --build attendance-analyser
docker compose -f docker-compose.apps.yml up -d --build frontend
```
